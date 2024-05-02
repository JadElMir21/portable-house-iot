package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                SharedPreferences myPref = getPreferences(Context.MODE_PRIVATE);
                SharedPreferences.Editor e = myPref.edit();


                    Intent intent = new Intent(MainActivity.this, loginPage.class);
                    startActivity(intent);
                    finish();

            }
        }, 3500);
        //getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.custom_mainOrange)));

    }
}