package com.example.animationproject;

import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

public class ExpandCollapseAnimation {

    private final View view;
    private final int targetHeight;

    public ExpandCollapseAnimation(View view, int targetHeight) {
        this.view = view;
        this.targetHeight = targetHeight;
    }

    public void expand() {
        int currentHeight = view.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, targetHeight);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

    public void collapse() {
        int currentHeight = view.getHeight();
        ValueAnimator valueAnimator = ValueAnimator.ofInt(currentHeight, 0);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int height = (int) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
                layoutParams.height = height;
                view.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setInterpolator(new AccelerateInterpolator());
        valueAnimator.setDuration(500);
        valueAnimator.start();
    }

}
