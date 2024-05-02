package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class security extends AppCompatActivity {
    ImageView open, closed, back;

    Button check;
    private Handler mHandler;
    private RequestQueue mRequestQueue;
    private static final String URL = "https://yourportablehouse.000webhostapp.com/getDoorStatus.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        open = findViewById(R.id.open);
        closed = findViewById(R.id.closed);
        back = findViewById(R.id.back);

        check = findViewById(R.id.check);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // camera link http://192.168.1.111:8080
                String url = "https://yourportablehouse.000webhostapp.com/getLedUpdate.php";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);

                }
            }
        });


        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(security.this, mainPage.class);
                startActivity(intent);
                finish();
            }
            });

        // Initialize Volley request queue
        mRequestQueue = Volley.newRequestQueue(this);

        // Initialize Handler
        mHandler = new Handler();

        // Schedule the handler to run every 0.5 seconds
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                // Make a request to your database
                makeDatabaseRequest();

                // Reschedule the handler
                mHandler.postDelayed(this, 500);
            }
        }, 500);


    }

    private void makeDatabaseRequest() {
        RequestQueue queue = Volley.newRequestQueue(security.this);
        StringRequest request = new StringRequest(Request.Method.GET, URL,

                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        if (response.equals("failure")) {

                            Toast.makeText(security.this, "error loading status", Toast.LENGTH_LONG).show();

                        } else if (response.equals("closed")){
                            open.setVisibility(View.GONE);
                            closed.setVisibility(View.VISIBLE);
                        } else {
                            closed.setVisibility(View.GONE);
                            open.setVisibility(View.VISIBLE);
                        }
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
    }
}