package io.github.floriangubler.quaky;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.work.PeriodicWorkRequest;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.util.concurrent.TimeUnit;

import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.service.EveryEarthquakeAPIService;
import io.github.floriangubler.quaky.service.LastEarthquakeService;
import io.github.floriangubler.quaky.work.NewEarthquakeWorker;

public class MainActivity extends AppCompatActivity {

    EveryEarthquakeAPIService apiService;

    boolean apiServiceBound;

    LastEarthquakeService fileService;

    boolean fileServiceBound;

    TextView lastEarthquakeDate;
    TextView lastEarthquakeCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lastEarthquakeDate = findViewById(R.id.lastEarthquakeDate);
        lastEarthquakeCountry = findViewById(R.id.lastEarthquakeCountry);

        //Start background scheduler
        new PeriodicWorkRequest.Builder(NewEarthquakeWorker.class, 1, TimeUnit.SECONDS).build();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //Bind services
        // Bind to API Service
        Intent connIntent = new Intent(this, EveryEarthquakeAPIService.class);
        bindService(connIntent, APIconnection, Context.BIND_AUTO_CREATE);

        //Bind to File Service
        Intent fileIntent = new Intent(this, LastEarthquakeService.class);
        bindService(fileIntent, FileConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(APIconnection);
        unbindService(FileConnection);
        apiServiceBound = false;
        fileServiceBound = false;
    }

    /* Loading last earthquake and set to main site GUI */
    private void loadLastEarthquake(){
        try{
            Earthquake lastEarthquake = apiService.getLastEarthquake(true);
            lastEarthquakeDate.setText(lastEarthquake.getDate().format(DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM)));
            lastEarthquakeCountry.setText(lastEarthquake.getContinent());
        } catch (Exception e){
            Toast.makeText(this, "Could not load last earthquake, see logs for detail", Toast.LENGTH_LONG).show();
            e.printStackTrace();
        }
    }

    /* OnClick history link */
    public void goToHistory(View view){
        Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
        startActivity(intent);
    }

    /* OnClick last earthquake link */
    public void showLastEarthquakeDetail(View view){
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try{
            Intent intent = new Intent(MainActivity.this, DetailActivity.class);
            intent.putExtra("data", objectMapper.writeValueAsString(apiService.getLastEarthquake(false)));
            startActivity(intent);
        } catch (Exception e){
            Toast.makeText(this, "Could not load data from last earthquake, see logs for details", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }
    }

    /* API Service connection for binding the service */
    ServiceConnection APIconnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            EveryEarthquakeAPIService.LocalBinder binder = (EveryEarthquakeAPIService.LocalBinder) service;
            apiService = binder.getService();
            apiServiceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            apiServiceBound = false;
        }
    };

    /* FileService connection, given to API Service */
    ServiceConnection FileConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            LastEarthquakeService.LocalBinder binder = (LastEarthquakeService.LocalBinder) service;
            fileService = binder.getService();
            fileServiceBound = true;

            if(apiServiceBound){
                apiService.setLastEarthquakeService(fileService);
                loadLastEarthquake();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            fileServiceBound = false;
        }
    };
}