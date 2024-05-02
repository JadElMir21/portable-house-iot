package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class loginInfo extends AppCompatActivity {

    private ProgressBar progressBar;
    TextView email, name;
    String em;
    ImageView back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_info);

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginInfo.this, mainPage.class);
                startActivity(intent);
                finish();
            }
        });

        progressBar = findViewById(R.id.progressBar);

        email= findViewById(R.id.email);
        name = findViewById(R.id.name);

       // SharedPreferences myPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        if(!myPref.contains("homeEmail")){
            Intent intent = new Intent(loginInfo.this, loginPage.class);
            startActivity(intent);
            finish();
        }

        em = myPref.getString("homeEmail", "");
        if(em!=""){
            email.setText(em);
        }else{
            Toast.makeText(loginInfo.this, "Unable to load this page correctly", Toast.LENGTH_LONG).show();
        }

        if(myPref.contains("homeEmail")){
            getName();
        }

    }


    public void getName(){

        progressBar.setVisibility(View.VISIBLE);

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        String email= myPref.getString("homeEmail", "");

        String url ="https://yourportablehouse.000webhostapp.com/getHomeName.php?email=" + email;

        RequestQueue queue =
                Volley.newRequestQueue(loginInfo.this);
        StringRequest request = new
                StringRequest(Request.Method.GET, url,

                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {

                        progressBar.setVisibility(View.GONE);
                        if (response.equals("failure")) {

                            Toast.makeText(loginInfo.this, "error loading your home name", Toast.LENGTH_LONG).show();

                        } else {

                            name.setText(response);
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
        //progressBar.setVisibility(View.GONE);
    }



    public void logout(View v){

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        e.clear();
        e.apply();

        Toast.makeText(loginInfo.this, "Logged out", Toast.LENGTH_LONG).show();


        Intent intent = new Intent(loginInfo.this, loginPage.class);
        startActivity(intent);
        finish();
    }

    public void delete(View v){

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        String email= myPref.getString("homeEmail", "");

        String url ="https://yourportablehouse.000webhostapp.com/deleteHome.php?email=" + email;

        RequestQueue queue =
                Volley.newRequestQueue(loginInfo.this);
        StringRequest request = new
                StringRequest(Request.Method.GET, url,

                new Response.Listener<String>(){
                    @Override
                    public void onResponse(String response) {
                        if (response.equals("success")) {

                            e.clear();
                            e.apply();

                            Toast.makeText(loginInfo.this, "Account deleted", Toast.LENGTH_LONG).show();


                            //open main page
                            Intent intent = new Intent(loginInfo.this, loginPage.class);
                            startActivity(intent);
                            finish();

                        } else if (response.equals("failure")) {
                            Toast.makeText(loginInfo.this, "error occurred try again later", Toast.LENGTH_LONG).show();
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