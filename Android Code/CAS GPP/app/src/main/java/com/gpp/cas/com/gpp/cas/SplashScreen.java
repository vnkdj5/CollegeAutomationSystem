package com.gpp.cas.com.gpp.cas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.gpp.cas.MainActivity;
import com.gpp.cas.R;

/**
 * Created by Vaibhav on 02/04/2016.
 */
public class SplashScreen extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        setContentView(R.layout.splash_screen);
        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(3*1000);

                    // After 3 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(),MainActivity.class);
                    startActivity(i);

                    finish();

                } catch (Exception e) {

                }
            }
        };
        background.start();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
