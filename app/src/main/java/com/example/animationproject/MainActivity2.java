package com.example.animationproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.util.concurrent.TimeUnit;

public class MainActivity2 extends AppCompatActivity {

    private static final long NOTIFICATION_INTERVAL = 30*1000;
    private Button dd;
    private PendingIntent notificationIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout click, changes;

        click = findViewById(R.id.click);
        changes = findViewById(R.id.change);
        dd = findViewById(R.id.dd);

        Dialog dialog = new Dialog(this, R.style.MyDialogTheme);
        dialog.setContentView(R.layout.sample);
        View popupView = getLayoutInflater().inflate(R.layout.sample, null);
        PopupWindow popupWindow = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setOutsideTouchable(true);

        dd.setOnClickListener(view -> {

            if (dialog.isShowing()==false){

                Toast.makeText(this, "", Toast.LENGTH_SHORT).show();

                popupWindow.showAsDropDown(dd);

            }else {
                 // allow the PopupWindow to be closed when clicked outside

                // Show the PopupWindow just below the anchorView


                dialog.dismiss();
            }

            // Set the dialog to be full width

            // Show the dialog
            //dialog.show();

        });


        click.setOnClickListener(view -> {

            if (changes.getVisibility()== View.VISIBLE)
                collapseView(changes);
            else {
                changes.setVisibility(View.VISIBLE);
                expandView(changes);
            }
        });

        // Get the AlarmManager instance
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        // Create the intent for the broadcast receiver
        Intent intent = new Intent(this, NotificationBroadcastReceiver.class);
        notificationIntent = PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_IMMUTABLE);

        // Schedule the repeating alarm
        long triggerTime = SystemClock.elapsedRealtime() + NOTIFICATION_INTERVAL;
        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerTime, NOTIFICATION_INTERVAL, notificationIntent);



//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            startForegroundService(serviceIntent);
//        } else {
//            startService(serviceIntent);
//        }

//        PeriodicWorkRequest periodicWorkRequest =
//                new PeriodicWorkRequest.Builder(NotificationWorker.class, 3, TimeUnit.MILLISECONDS)
//                        .addTag("notification_work_tag")
//                        .build();
//
//        // Enqueue the periodic work request
//        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
//                "notification_work_tag",
//                ExistingPeriodicWorkPolicy.KEEP,
//                periodicWorkRequest
//        );


    }

    private void expandView(View view) {
        // First, get the measured height of the view
        int measuredHeight = getViewHeight(this,view);

        // Next, set the view's height to 0 and make it visible
        view.getLayoutParams().height = 0;
        view.setVisibility(View.VISIBLE);

        // Create a ValueAnimator to animate the view's height from 0 to the measured height
        ValueAnimator valueAnimator = ValueAnimator.ofInt(0, measuredHeight);

        // Set the ValueAnimator's duration and interpolator
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set a listener to update the view's height as the animation progresses
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (int) animator.getAnimatedValue();
                view.getLayoutParams().height = value;
                view.requestLayout();
            }
        });

        // Start the animation
        valueAnimator.start();
    }

    private void collapseView(View view) {
        // First, get the current height of the view
        int height = view.getHeight();

        // Create a ValueAnimator to animate the view's height from the current height to 0
        ValueAnimator valueAnimator = ValueAnimator.ofInt(height, 0);

        // Set the ValueAnimator's duration and interpolator
        valueAnimator.setDuration(300);
        valueAnimator.setInterpolator(new AccelerateDecelerateInterpolator());

        // Set a listener to update the view's height as the animation progresses
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animator) {
                int value = (int) animator.getAnimatedValue();
                view.getLayoutParams().height = value;
                view.requestLayout();
            }
        });

        // Set a listener to make the view invisible when the animation ends
        valueAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.GONE);
            }
        });

        // Start the animation
        valueAnimator.start();
    }

    public static int getViewHeight(Activity activity, View view) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);

        int screenWidth = metrics.widthPixels;

        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(screenWidth, View.MeasureSpec.EXACTLY);

        view.measure(widthMeasureSpec, heightMeasureSpec);
        int height = view.getMeasuredHeight();
        return height;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        Intent serviceIntent = new Intent(this, ToastService.class);
//        startService(serviceIntent);
    }
}