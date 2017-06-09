package com.example.adriaalvarez.android_boilerplate.activities;

import android.Manifest;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;

import com.example.adriaalvarez.android_boilerplate.R;

/**
 * Created by adria.alvarez on 08/06/2017.
 */

public class SplashActivity extends AppCompatActivity {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        ImageView logo = (ImageView)findViewById(R.id.logo);

        ObjectAnimator fadeAnimation = ObjectAnimator.ofFloat(logo, "alpha", 0, 1);
        fadeAnimation.setDuration(650);
        fadeAnimation.setRepeatCount(ObjectAnimator.INFINITE);
        fadeAnimation.setRepeatMode(ObjectAnimator.REVERSE);

        AnimatorSet set = new AnimatorSet();
        set.play(fadeAnimation);
        set.setInterpolator(new AccelerateDecelerateInterpolator());
        set.start();

        verifyStoragePermissions(this);
    }

    @Override
    public void onResume(){
        super.onResume();

        final Context context = this;

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent(context, MainActivity.class);
                startActivity(intent);
            }
        }, 5000);

    }

    private void verifyStoragePermissions(Activity activity) {
        // Check if we have write permission
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            // We don't have permission so prompt the user
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSIONS_STORAGE,
                    REQUEST_EXTERNAL_STORAGE
            );
        }
    }

}
