package com.example.willy1087.final_project;

/**
 * Created by williamsalinas on 5/25/15.
 */
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends Activity {

    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        StartAnimations();

        new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

            @Override
            public void run() {
                // This method will be executed once the timer is over
                // Start your app main activity
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                startActivity(i);

                // close this activity
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_screen_fadeout);
        anim.reset();
        ImageView l=(ImageView) findViewById(R.id.imgLogo);
        l.clearAnimation();
        l.startAnimation(anim);

        Animation anim1 = AnimationUtils.loadAnimation(this, R.anim.slideup);

        TextView up = (TextView)findViewById(R.id.by_id);
        up.startAnimation(anim1);

        Animation anim2 = AnimationUtils.loadAnimation(this, R.anim.slidedown);

        TextView down = (TextView)findViewById(R.id.app_name);
        down.startAnimation(anim2);


    }

}