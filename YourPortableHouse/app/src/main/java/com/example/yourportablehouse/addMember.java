package com.example.yourportablehouse;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class addMember extends AppCompatActivity {

    EditText name, pass, conPass, phoneNbr;
    String memName, memPass, memConPass, memPhoneNbr, url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        name = findViewById(R.id.name);
        pass = findViewById(R.id.pass);
        conPass = findViewById(R.id.conPass);
        phoneNbr = findViewById(R.id.phoneNbr);
        url = "";

    }

    public void build(View v) {
        SharedPreferences myPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor e = myPref.edit();

        memName = name.getText().toString().trim();
        memPass = pass.getText().toString().trim();
        memConPass = conPass.getText().toString().trim();
        memPhoneNbr = phoneNbr.getText().toString().trim();

        if (!memName.equals("") && !memPass.equals("") && !memConPass.equals("") && !memPhoneNbr.equals("")){

            if (memPass.equals(memConPass)){


                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                url= "https://yourportablehouse.000webhostapp.com/phoneCheck.php?phone="+memPhoneNbr;

                StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.d("res", response);
                        if (response.equals("no")) {

                            //save info to shared pref
                            e.putString("memPhoneNbr", memPhoneNbr) ;
                            e.apply();
                            e.putString("memPassword", memPass);
                            e.apply();

                            //open mem login
                            Intent intent= new Intent(addMember.this, memInfo.class);
                            intent.putExtra("memName", memName);
                            intent.putExtra("memPass", memPass);
                            intent.putExtra("memPhoneNbr", memPhoneNbr);
                            startActivity(intent);
                            finish();

                        } else if (response.equals("yes")) {
                            Toast.makeText(addMember.this, "Phone number already used", Toast.LENGTH_LONG).show();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(addMember.this, "try again later", Toast.LENGTH_SHORT).show();
                    }
                });

                requestQueue.add(stringRequest);



            }else{
                Toast.makeText(this, "Password did not match", Toast.LENGTH_SHORT).show();
            }


        }else{
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show();
        }


    }

 }