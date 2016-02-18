package com.unbelievable.uetsupport;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by huylv on 22/11/2015.
 */
public class SplashActivity extends Activity {

    private static int SPLASH_TIME_OUT = 3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            public void run() {

                Intent intent = new Intent();
                intent.setClass(SplashActivity.this, MainActivity.class);

                SplashActivity.this.startActivity(intent);
                SplashActivity.this.finish();

                // transition from splash to main menu
                overridePendingTransition(R.anim.activityfadein,
                        R.anim.splashfadeout);

            }
        }, SPLASH_TIME_OUT);

    }
}
