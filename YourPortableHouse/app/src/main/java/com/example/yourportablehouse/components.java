package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class components extends AppCompatActivity {
    private Switch swit;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_components);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(components.this, mainPage.class);
                startActivity(intent);
                finish();
            }
        });

        swit = findViewById(R.id.swit);

        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Your code here
                if (isChecked) {


                    String url ="https://yourportablehouse.000webhostapp.com/getLedUpdate.php?stat=1";

                    RequestQueue queue = Volley.newRequestQueue(components.this);
                    StringRequest request = new StringRequest(Request.Method.GET, url,

                            new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Led turned ON",
                                            Toast.LENGTH_LONG).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_LONG).show();
                                }
                            }
                    );


                    queue.add(request);


                } else {
                    String url ="https://yourportablehouse.000webhostapp.com/getLedUpdate.php?stat=2";

                    RequestQueue queue =
                            Volley.newRequestQueue(components.this);
                    StringRequest request = new
                            StringRequest(Request.Method.GET, url,

                            new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), "Led turned OFF",
                                            Toast.LENGTH_LONG).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Check your internet connection", Toast.LENGTH_SHORT).show();
                                }
                            }
                    );


                    queue.add(request);

                }
            }
        });
    }
}