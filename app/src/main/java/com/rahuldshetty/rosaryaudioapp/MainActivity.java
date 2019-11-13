package com.rahuldshetty.rosaryaudioapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.rahuldshetty.rosaryaudioapp.activities.HomeActivity;


public class MainActivity extends AppCompatActivity {

    public static Activity activity;
    public static Context mainContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activity = MainActivity.this;
        mainContext = getApplicationContext();



        Thread background = new Thread() {
            public void run() {
                try {
                    // Thread will sleep for 5 seconds
                    sleep(3000);

                    // After 5 seconds redirect to another intent
                    Intent i=new Intent(getBaseContext(), HomeActivity.class);
                    startActivity(i);

                    //Remove activity
                    finish();
                } catch (Exception e) {
                }
            }
        };
        // start thread
        background.start();
    }
}
