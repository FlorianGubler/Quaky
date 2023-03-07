package io.github.floriangubler.quaky.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.widget.RemoteViews;

import androidx.work.ExistingWorkPolicy;
import androidx.work.OneTimeWorkRequest;
import androidx.work.WorkManager;
import androidx.work.WorkRequest;

import io.github.floriangubler.quaky.R;
import io.github.floriangubler.quaky.service.EveryEarthquakeAPIService;
import io.github.floriangubler.quaky.service.LastEarthquakeService;
import io.github.floriangubler.quaky.work.NewEarthquakeWorker;

/**
 * Implementation of App Widget functionality.
 */
public class LastEarthquakeWidget extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.last_earthquake_widget);
        views.setTextViewText(R.id.lastEarthquakeWidgetText, "-");

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);

        //On start call worker for update data
        OneTimeWorkRequest workRequest = OneTimeWorkRequest.from(NewEarthquakeWorker.class);
        WorkManager.getInstance(context).enqueueUniqueWork("Update last earthquake", ExistingWorkPolicy.KEEP, workRequest);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}