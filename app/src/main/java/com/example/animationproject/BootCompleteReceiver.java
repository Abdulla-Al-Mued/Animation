package com.example.animationproject;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.util.Log;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            // Re-register the alarm here
            Log.d("bootComplete", Intent.ACTION_BOOT_COMPLETED);
            Log.d("bootComplete 2", intent.getAction());
            scheduleAlarm(context);
        }
    }

    private void scheduleAlarm(Context context) {

        // Get the AlarmManager instance
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        // Create the intent for the broadcast receiver

        Intent alarmIntent = new Intent(context, NotificationBroadcastReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);



        // Schedule the repeating alarm
        long interval = 30 * 1000; // 30 seconds
        long triggerTime = SystemClock.elapsedRealtime() + interval;
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, interval, pendingIntent);
    }

}
