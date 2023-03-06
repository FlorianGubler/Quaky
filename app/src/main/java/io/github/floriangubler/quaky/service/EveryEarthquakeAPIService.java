package io.github.floriangubler.quaky.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.entity.EveryEarthquakeResponse;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Service Class for all functionality, that works with the EveryEarthquake API
 */
public class EveryEarthquakeAPIService extends Service {
    /* Binder given to class for connection */
    private final IBinder binder = new EveryEarthquakeAPIService.LocalBinder();
    /* Every Earthquake API URL */
    private static final String URL = "https://everyearthquake.p.rapidapi.com";
    /* Every Earthquake API KEY Header Name */
    private static final String API_KEY_NAME = "X-RapidAPI-Key";
    /* Every Earthquake API KEY */
    private static final String API_KEY = "067f9bd1aemsh0e4a61e4cfc7512p1aed22jsndbca7644ec32";
    /* Every Earthquake API Host Header Name */
    private static final String API_HOST_NAME = "X-RapidAPI-Host";
    /* Every Earthquake API Host */
    private static final String API_HOST = "everyearthquake.p.rapidapi.com";
    /* Ok Http Client used for connections */
    private OkHttpClient client;
    /* Object Mapper for JSON en-/decode */
    private ObjectMapper objectMapper;
    /* Last Earthquake service for persistent storage */
    private LastEarthquakeService lastEarthquakeService;

    /* Binder class used for binding the service to a caller */
    public class LocalBinder extends Binder {
        public EveryEarthquakeAPIService getService() {
            // Return this instance of LocalService so clients can call public methods
            return EveryEarthquakeAPIService.this;
        }
    }

    public EveryEarthquakeAPIService() {
        client = new OkHttpClient();
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    /* Setter for LastEarthquake Service, given from caller */
    public void setLastEarthquakeService(LastEarthquakeService lastEarthquakeService) {
        this.lastEarthquakeService = lastEarthquakeService;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /* Abstract method, creating a HTTP Call to a specific URL with Auth Headers, returns Future Object for response */
    private Response createCall(String url) {
        ExecutorService executor = Executors.newSingleThreadExecutor();

        Future<Response> res = executor.submit(() -> {
            Headers headers = Headers.of(Map.of(API_KEY_NAME, API_KEY, API_HOST_NAME, API_HOST));
            Request request = new Request.Builder()
                    .url(url)
                    .headers(headers)
                    .build();

            try {
                return client.newCall(request).execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        try{
            //Blocking thread get response
            return res.get();
        } catch (ExecutionException | InterruptedException e){
            throw new RuntimeException(e);
        }
    }

    /* Method for searching earthquakes with start & limit */
    public Earthquake[] search(int start, int limit) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(URL + "/earthquakes")).newBuilder();
        urlBuilder.addQueryParameter("start", String.valueOf(start));
        urlBuilder.addQueryParameter("count", String.valueOf(limit));

        Response res =  createCall(urlBuilder.build().toString());
        assert res.body() != null;
        return objectMapper.readValue(res.body().byteStream(), EveryEarthquakeResponse.class).getData().toArray(new Earthquake[0]);
    }

    /* Method for searching earthquakes by date (also with start & limit) */
    public Earthquake[] searchDate(int start, int limit, LocalDate startDate, LocalDate endDate) throws IOException {
        HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(URL + "/earthquakesByDate")).newBuilder();
        urlBuilder.addQueryParameter("start", String.valueOf(start));
        urlBuilder.addQueryParameter("count", String.valueOf(limit));
        urlBuilder.addQueryParameter("startDate", String.valueOf(startDate));
        urlBuilder.addQueryParameter("endDate", String.valueOf(endDate));

        Response res = createCall(urlBuilder.build().toString());
        assert res.body() != null;
        return objectMapper.readValue(res.body().byteStream(), EveryEarthquakeResponse.class).getData().toArray(new Earthquake[0]);
    }

    //Gets the last earthquake (from local if available, if not or reload = true, newest loaded from API */
    public Earthquake getLastEarthquake(boolean reload) throws Exception {
        if(lastEarthquakeService == null){
            throw new Exception("LastEarthquakeService is null");
        } else{
            Earthquake lastEarthquakeFromFile = lastEarthquakeService.getLastEarthquake();
            //If Earthquake local not found or reload true, make API Call
            if(lastEarthquakeFromFile == null || reload) {
                HttpUrl.Builder urlBuilder = Objects.requireNonNull(HttpUrl.parse(URL + "/earthquakes")).newBuilder();
                urlBuilder.addQueryParameter("start", String.valueOf(1));
                urlBuilder.addQueryParameter("count", String.valueOf(1));

                Response res = createCall(urlBuilder.build().toString());
                //Parse response
                assert res.body() != null;
                Earthquake lastEarthquake = objectMapper.readValue(res.body().byteStream(), EveryEarthquakeResponse.class).getData().get(0);
                //Store local if not same
                if(lastEarthquakeFromFile != null && !Objects.equals(lastEarthquakeFromFile.getId(), lastEarthquake.getId())){
                    lastEarthquakeService.setLastEarthquake(lastEarthquake);
                }
                //Return new loaded earthquake
                return lastEarthquake;
            } else {
                //Return local earthquake
                return lastEarthquakeFromFile;
            }
        }
    }
}