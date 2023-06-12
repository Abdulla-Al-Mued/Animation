package com.example.animationproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import androidx.core.app.NotificationCompat;

public class ToastService extends Service {
    private Runnable runnable;
    private static final long INTERVAL = 3000; // 3 seconds
    private static final int NOTIFICATION_ID = 1;
    private static final String CHANNEL_ID = "notification_channel";
    private static final CharSequence CHANNEL_NAME = "Notification Channel";
    final Handler handler = new Handler();
    final int delay = 3000;

    @Override
    public void onCreate() {
        super.onCreate();
//        handler = new Handler();
//        runnable = new Runnable() {
//            @Override
//            public void run() {
//                showNotification();
//                handler.postDelayed(this, INTERVAL);
//            }
//        };
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showNotification();
                Log.d("notification", "running");

                handler.postDelayed(this, delay);
            }
        }, delay);

        //handler.postDelayed(runnable, INTERVAL);
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //handler.removeCallbacks(runnable);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {
        // Create a notification channel (required for Android 8.0 and above)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }

        // Create an explicit intent for launching the app
        Intent launchIntent = new Intent(this, MainActivity2.class);
        //PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, launchIntent, PendingIntent.FLAG_IMMUTABLE);


        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addNextIntentWithParentStack(launchIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0,
                        PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);

        // Build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("Notification Title")
                .setContentText("This is a notification message.")
                .setContentIntent(resultPendingIntent);

        // Show the notification
        NotificationManager notificationManager = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            notificationManager = getSystemService(NotificationManager.class);
        }
        notificationManager.notify(NOTIFICATION_ID, builder.build());
    }
}
