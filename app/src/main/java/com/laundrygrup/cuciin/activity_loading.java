package com.laundrygrup.cuciin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;

public class activity_loading extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        },3000);
    }
}