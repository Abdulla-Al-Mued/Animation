package com.example.animationproject;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.LinearLayout;

public class MainActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        LinearLayout click, changes;

        click = findViewById(R.id.click);
        changes = findViewById(R.id.change);


        click.setOnClickListener(view -> {

            if (changes.getVisibility()== View.VISIBLE)
                collapseView(changes);
            else {
                changes.setVisibility(View.VISIBLE);
                expandView(changes);
            }
        });

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
}