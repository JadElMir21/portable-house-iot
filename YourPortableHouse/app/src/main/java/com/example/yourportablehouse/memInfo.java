package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class memInfo extends AppCompatActivity {
    EditText birthdate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_info);
        birthdate= findViewById(R.id.birthdate);


        birthdate.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean hasFocus) {
                if (hasFocus) {
                    // Set up an input event listener here
                    // This will be triggered when the EditText is clicked and ready for input
                    birthdate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // Your onClick logic here
                            // For example, you can show a toast or perform any action you desire
                            birthdate.setText("YYYY-MM-DD");
                        }
                    });
                } else {
                    // Remove the onClick listener if EditText loses focus
                    birthdate.setOnClickListener(null);
                }
            }
        });
        

    }






}