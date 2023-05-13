package com.example.animationproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    TextView text;
    Button animateTxtBtn;
    Animator animator;
    LinearLayout myView;
    private Button buttonReveal;
    private Button buttonHide;
    FloatingActionButton view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startActivity(new Intent(this, MainActivity2.class));

//        text = findViewById(R.id.texToAnimate);
//        animateTxtBtn = findViewById(R.id.animateTextBtn);
//
//        animateTxtBtn.setOnClickListener(v -> {
//
//            Animation move = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.first_animation);
//            text.startAnimation(move);
//
//        });
        view = findViewById(R.id.fab);



        buttonReveal = findViewById(R.id.button_reveal);
        buttonHide = findViewById(R.id.button_hide);

        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                revealFABUpToDown();
            }
        });

        buttonReveal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                revealFAB();
            }
        });

        buttonHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hideFAB();
            }
        });
    }

    private void revealFAB() {
        View view = findViewById(R.id.fab);

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float finalRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, 0, finalRadius);
        view.setVisibility(View.VISIBLE);

        anim.setDuration(1000);
        anim.start();
    }

    private void revealFABUpToDown() {
        View view = findViewById(R.id.fab);


        view.setAlpha(0f); // set the initial transparency to 0

// create an alpha animation
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f);
        fadeIn.setDuration(2000); // set the duration of the animation in milliseconds

// set an animation listener to perform actions when the animation starts and ends
        fadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                view.setVisibility(View.VISIBLE); // make the view visible when the animation starts
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                // do nothing when the animation ends
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                // do nothing when the animation repeats
            }
        });

        view.startAnimation(fadeIn);


    }

    private void hideFAB() {
        final View view = findViewById(R.id.fab);

        int cx = view.getWidth() / 2;
        int cy = view.getHeight() / 2;

        float initialRadius = (float) Math.hypot(cx, cy);

        Animator anim = ViewAnimationUtils.createCircularReveal(view, cx, cy, initialRadius, 0);

        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                view.setVisibility(View.INVISIBLE);
            }
        });

        anim.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                revealFABUpToDown();
            }
        });
    }

//    @Override
//    protected void onRestart() {
//        super.onRestart();
//        view.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @Override
//            public void onGlobalLayout() {
//                revealFAB();
//            }
//        });
//    }
}