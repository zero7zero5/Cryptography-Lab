package com.example.graphicspremitives;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.view.ViewCompat;
import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Animatable2;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private View circularView;
    private GestureDetector gestureDetector;
    private Handler handler = new Handler();
    private Button btn;
    private ImageView imageView;
    private AnimatedVectorDrawable animatedVectorDrawable1,animatedVectorDrawable2,animatedVectorDrawable3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        circularView = findViewById(R.id.circle);
        imageView=findViewById(R.id.imageAni);
        btn=findViewById(R.id.btn);

        animatedVectorDrawable1=(AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_demo);
        animatedVectorDrawable2=(AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_square);
        animatedVectorDrawable3=(AnimatedVectorDrawable) getResources().getDrawable(R.drawable.animated_circle);

        imageView.setImageDrawable(animatedVectorDrawable1);
        // Create a GestureDetector to detect double-taps
        gestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDoubleTap(MotionEvent e) {
                int tintColor = generateRandomColor();
                ViewCompat.setBackgroundTintList(circularView, ColorStateList.valueOf(tintColor));
                return true;
            }
        });

        circularView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // Pass touch events to the GestureDetector to handle double-tap
                gestureDetector.onTouchEvent(event);
                return true;
            }
        });
    }
    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private int generateRandomColor() {
        Random random = new Random();
        int red = random.nextInt(256);
        int green = random.nextInt(256);
        int blue = random.nextInt(256);
        return Color.rgb(red, green, blue);
    }

    public void startAnimation(View view) {
        imageView.setVisibility(View.VISIBLE);
        circularView.setVisibility(View.INVISIBLE);

        // Start the animation
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (animatedVectorDrawable1 != null) {
                    animatedVectorDrawable1.start();
                }
            }
        }, 0);

        // Start the second animation after a delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(animatedVectorDrawable2);
                if (animatedVectorDrawable2 != null) {
                    animatedVectorDrawable2.start();
                }
            }
        }, 6000); // Delay of 3000 milliseconds (3 seconds)

        // Start the third animation after a delay
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setImageDrawable(animatedVectorDrawable3);
                if (animatedVectorDrawable3 != null) {
                    animatedVectorDrawable3.start();
                }
            }
        }, 12000);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                imageView.setVisibility(View.INVISIBLE);
                circularView.setVisibility(View.VISIBLE);
            }
        }, 19000);
    }
}
