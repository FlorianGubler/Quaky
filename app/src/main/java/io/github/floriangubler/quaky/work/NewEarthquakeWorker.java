package io.github.floriangubler.quaky.work;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Objects;

import io.github.floriangubler.quaky.R;
import io.github.floriangubler.quaky.service.EveryEarthquakeAPIService;
import io.github.floriangubler.quaky.service.LastEarthquakeService;
import io.github.floriangubler.quaky.entity.Earthquake;
import io.github.floriangubler.quaky.widget.LastEarthquakeWidget;

public class NewEarthquakeWorker extends Worker {

    private static final String CHANNEL_ID = "defaultChannel";
    private static final String CHANNEL_NAME = "Default Channel";

    EveryEarthquakeAPIService apiService;

    boolean apiServiceBound;

    LastEarthquakeService fileService;

    boolean fileServiceBound;

    private NotificationManager notificationManager;

    private Context context;

    public NewEarthquakeWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.context = context;
        this.notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }

        // Bind to API Service
        Intent connIntent = new Intent(context, EveryEarthquakeAPIService.class);
        context.bindService(connIntent, APIconnection, Context.BIND_AUTO_CREATE);

        //Bind to File Service
        Intent fileIntent = new Intent(context, LastEarthquakeService.class);
        context.bindService(fileIntent, FileConnection, Context.BIND_AUTO_CREATE);
    }
    @NonNull
    @Override
    public Result doWork() {
        if(apiServiceBound && fileServiceBound){
            try{
                Earthquake newestEarthquakeFile = fileService.getLastEarthquake();
                Earthquake newestEarthquakeHTTP = apiService.getLastEarthquake(true);
                if(newestEarthquakeFile != null && !Objects.equals(newestEarthquakeHTTP.getId(), newestEarthquakeFile.getId())){
                    sendNotification("New Earthquake registered", newestEarthquakeHTTP.getTitle());
                }
                //Widget must always be updated, because worker is also called on Widget creation
                ComponentName name = new ComponentName(context, LastEarthquakeWidget.class);
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
                RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.last_earthquake_widget);
                remoteViews.setTextViewText(R.id.lastEarthquakeWidgetText, newestEarthquakeHTTP.getTitle());
                for(int id : appWidgetManager.getAppWidgetIds(name)){
                    appWidgetManager.updateAppWidget(id, remoteViews);
                }
                return Result.success();
            } catch (Exception e){
                e.printStackTrace();
                return Result.failure();
            }
        } else{
            System.err.println("Services not bound");
            return Result.failure();
        }
    }

    private void sendNotification(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle(title)
                .setContentText(content)
                .setSmallIcon(R.drawable.notification_icon)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(0, builder.build());
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
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            fileServiceBound = false;
        }
    };
}
