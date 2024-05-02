package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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

public class memLoginPage extends AppCompatActivity {

    EditText phoneEt;
    EditText passEt;


    String memPhone, memPass, url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        SharedPreferences myPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        //if(myPref.contains(“highscore”));

        phoneEt =  findViewById(R.id.phoneNbr);
        passEt = findViewById(R.id.pass);

    }

    public void login(View v){

        SharedPreferences myPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        memPhone = phoneEt.getText().toString().trim();
        memPass = passEt.getText().toString().trim();

        if(!memPhone.equals("") && !memPass.equals("")){

            RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
            url= "https://yourportablehouse.000webhostapp.com/memLogin.php?phone="+memPhone+"&pass="+memPass;

            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    // Log.d("res", response);
                    if (response.equals("success")) {

                        //save info to shared pref
                        e.putString("memPhoneNbr", memPhone) ;
                        e.apply();
                        e.putString("memPassword", memPass);
                        e.apply();

                        //open mem login
                        Intent intent= new Intent(memLoginPage.this, mainPage.class);
                        startActivity(intent);
                        finish();

                    } else if (response.equals("failure")) {
                        Toast.makeText(memLoginPage.this, "Invalid number or password", Toast.LENGTH_LONG).show();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(memLoginPage.this, "try again later", Toast.LENGTH_SHORT).show();
                }
            });

            requestQueue.add(stringRequest);

        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }


    }

    public void add(View v){
        Intent intent= new Intent(memLoginPage.this, addMember.class);
        startActivity(intent);
        finish();

    }

}