package io.github.floriangubler.quaky.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import io.github.floriangubler.quaky.entity.Earthquake;

/* Class for persistent storage of last earthquake with internal storage API */
public class LastEarthquakeService extends Service {
    /* Binder given to clients */
    private final IBinder binder = new LocalBinder();
    /* Object Mapper for JSON en-/decoding */
    ObjectMapper objectMapper;
    /* Filename for json file */
    private final String LAST_EARTHQUAKE_FILENAME = "lastearthquake.json";

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        public LastEarthquakeService getService() {
            // Return this instance of LocalService so clients can call public methods
            return LastEarthquakeService.this;
        }
    }

    public LastEarthquakeService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    /* Method for storing last earthquake */
    public void setLastEarthquake(Earthquake earthquake) throws IOException {
        try (FileOutputStream fos = this.openFileOutput(LAST_EARTHQUAKE_FILENAME, Context.MODE_PRIVATE)) {
            fos.write(objectMapper.writeValueAsString(earthquake).getBytes());
        }
    }

    /* Method for loading last earthquake (on error is null returned) */
    public Earthquake getLastEarthquake() {
        try{
            return objectMapper.readValue(Files.newInputStream(new File(this.getFilesDir(), LAST_EARTHQUAKE_FILENAME).toPath()), Earthquake.class);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}