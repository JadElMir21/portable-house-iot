package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class createHome extends AppCompatActivity {

    ImageView back;
    EditText name, pass, conPass, email;
    String homeName, homePass, homeConPass, homeEmail, url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_home);

        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        conPass = findViewById(R.id.conPass);
        email = findViewById(R.id.email);
        url = "";

        back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(createHome.this, loginPage.class);
                startActivity(intent);
                finish();
            }
        });
    }


    public void build(View v) {

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        homeName = name.getText().toString().trim();
        homePass = pass.getText().toString().trim();
        homeConPass = conPass.getText().toString().trim();
        homeEmail = email.getText().toString().trim();


        if (!homeName.equals("") && !homePass.equals("") && !homeConPass.equals("") && !homeEmail.equals("")) {
            if (isValidEmail(homeEmail)) {
          /*  RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

            url = "https://yourportablehouse.000webhostapp.com/emailCheck.php?email="+homeEmail;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //if (response.equals("no")) { */


                if (homePass.equals(homeConPass)) {

                    // Toast.makeText(loginPage.this, "pressed", Toast.LENGTH_LONG).show();
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

                    url = "https://yourportablehouse.000webhostapp.com/createHome.php?email=" + homeEmail + "&name=" + homeName + "&pass=" + homePass;

                    StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            // Log.d("res", response);
                            if (response.equals("success")) {
                                //reset text fields
                                name.setText("");
                                pass.setText("");
                                conPass.setText("");
                                email.setText("");

                                //save info to shared pref
                                e.putString("homeEmail", homeEmail);
                                e.apply();

                                //open mem login
                                Intent intent = new Intent(createHome.this, mainPage.class);
                                startActivity(intent);
                                finish();

                            } else /*if (response.equals("failure"))*/ {
                                Toast.makeText(createHome.this, "Email already in use!", Toast.LENGTH_LONG).show();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(createHome.this, "Check your internet connection", Toast.LENGTH_LONG).show();
                        }
                    });

                    requestQueue.add(stringRequest);

                } else {
                    Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
                }


        }else {
                Toast.makeText(this, "Enter a valid email format", Toast.LENGTH_SHORT).show();
            }

        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }


    }

    private boolean isValidEmail(CharSequence email) {
        return /*email != null &&*/ Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}