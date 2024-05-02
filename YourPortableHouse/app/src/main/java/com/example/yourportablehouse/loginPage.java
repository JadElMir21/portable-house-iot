package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class loginPage extends AppCompatActivity {
EditText emailEt;
EditText passEt;
Button login;

String homeEmail, homePass, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        emailEt = findViewById(R.id.email);
        passEt = findViewById(R.id.pass);
        login = findViewById(R.id.login);

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

        if(myPref.contains("homeEmail")) {
            Intent intent = new Intent(loginPage.this, mainPage.class);
            startActivity(intent);
            finish();
        }

    }


    public void login(View v){

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        homeEmail = emailEt.getText().toString().trim();
        homePass = passEt.getText().toString().trim();


          if(!homeEmail.equals("") && !homePass.equals("")){
           // Toast.makeText(loginPage.this, "pressed", Toast.LENGTH_LONG).show();
              if (isValidEmail(homeEmail)) {
                  RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                  url = "https://yourportablehouse.000webhostapp.com/homeLogin.php?email=" + homeEmail + "&pass=" + homePass;

                  StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {
                          // Log.d("res", response);
                          if (response.equals("success")) {

                              //reset text fields
                              emailEt.setText("");
                              passEt.setText("");

                              //save info to shared pref
                              e.putString("homeEmail", homeEmail);
                              e.apply();


                              //open main page
                              Intent intent = new Intent(loginPage.this, mainPage.class);
                              startActivity(intent);
                              finish();

                          } else if (response.equals("failure")) {
                              Toast.makeText(loginPage.this, "Invalid phone number or password", Toast.LENGTH_LONG).show();
                          }
                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {
                          Toast.makeText(loginPage.this, "Check your internet connection", Toast.LENGTH_SHORT).show();
                      }
                  });

                  requestQueue.add(stringRequest);

              }else{
                  Toast.makeText(this, "Enter a valid email format", Toast.LENGTH_SHORT).show();

              }
        }else{
             Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isValidEmail(CharSequence email) {
        return /*email != null &&*/ Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public void build(View v){
        Intent intent= new Intent(loginPage.this, createHome.class);
        startActivity(intent);
        finish();
    }



}