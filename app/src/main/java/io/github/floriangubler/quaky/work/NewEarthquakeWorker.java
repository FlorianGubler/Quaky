package io.github.floriangubler.quaky.work;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.Objects;

import io.github.floriangubler.quaky.service.EveryEarthquakeAPIService;
import io.github.floriangubler.quaky.service.LastEarthquakeService;
import io.github.floriangubler.quaky.entity.Earthquake;

public class NewEarthquakeWorker extends Worker {

    private static final String CHANNEL_ID = "defaultChannel";
    private static final String CHANNEL_NAME = "Default Channel";

    private NotificationManager notificationManager;

    public NewEarthquakeWorker(
            @NonNull Context context,
            @NonNull WorkerParameters params) {
        super(context, params);
        this.notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT);
            notificationManager.createNotificationChannel(channel);
        }
    }
    @NonNull
    @Override
    public Result doWork() {
        try{
            EveryEarthquakeAPIService apiService = new EveryEarthquakeAPIService();
            LastEarthquakeService fileService = new LastEarthquakeService();
            apiService.setLastEarthquakeService(fileService);
            Earthquake newestEarthquakeHTTP = apiService.getLastEarthquake(true);
            Earthquake newestEarthquakeFile = fileService.getLastEarthquake();
            if(newestEarthquakeFile == null || !Objects.equals(newestEarthquakeHTTP.getId(), newestEarthquakeHTTP.getId())){
                sendNotification("New Earthquake registered", newestEarthquakeHTTP.getTitle());
            }

            sendNotification("test", "test");
            return Result.success();
        } catch (Exception e){
            return Result.failure();
        }
    }

    private void sendNotification(String title, String content){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID)
                .setSmallIcon(android.R.drawable.ic_media_play)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        notificationManager.notify(0, builder.build());
    }
}
