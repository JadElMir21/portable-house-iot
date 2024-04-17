package com.example.seniorprojecttrial;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    private ProgressBar loadingPB;
    private Button btnLogin, btnUpd;
    private EditText val, val2;
    private Switch swit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadingPB = findViewById(R.id.idLoadingPB);

        val = findViewById(R.id.val);
        val2 = findViewById(R.id.val2);
        swit = findViewById(R.id.switch1);


        btnLogin = findViewById(R.id.btnLogin);
        btnUpd = findViewById(R.id.btnUpd);
        btnLogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
               // postDataUsingVolley(sendval.getText().toString(), sendval2.getText().toString());

                String sendval= val.getText().toString();
                String sendval2= val2.getText().toString();

                String url ="https://seniorprojectdatabase.000webhostapp.com/sallydbwrite.php?sendval=" + sendval +
                        "&sendval2=" + sendval2;

                RequestQueue queue =
                        Volley.newRequestQueue(MainActivity.this);
                StringRequest request = new
                        StringRequest(Request.Method.GET, url,

                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response,
                                        Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error:" +
                                        error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );


                queue.add(request);

            }
        });


        swit.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                // Your code here
                if (isChecked) {


                    String url ="https://yourportablehouse.000webhostapp.com/getLedUpdate.php?stat=" + "1";

                    RequestQueue queue =
                            Volley.newRequestQueue(MainActivity.this);
                    StringRequest request = new
                            StringRequest(Request.Method.GET, url,

                            new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), response,
                                            Toast.LENGTH_SHORT).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Error:" +
                                            error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );


                    queue.add(request);


                } else {
                    String url ="https://yourportablehouse.000webhostapp.com/getLedUpdate.php?stat=" + "2";

                    RequestQueue queue =
                            Volley.newRequestQueue(MainActivity.this);
                    StringRequest request = new
                            StringRequest(Request.Method.GET, url,

                            new Response.Listener<String>(){
                                @Override
                                public void onResponse(String response) {
                                    Toast.makeText(getApplicationContext(), response,
                                            Toast.LENGTH_SHORT).show();

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(getApplicationContext(), "Error:" +
                                            error.toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    );


                    queue.add(request);

                }
            }
        });

        btnUpd.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                // postDataUsingVolley(sendval.getText().toString(), sendval2.getText().toString());

                String sendval= val.getText().toString();


                String url ="https://seniorprojectdatabase.000webhostapp.com/getLedUpdate.php?stat=" + sendval;

                RequestQueue queue =
                        Volley.newRequestQueue(MainActivity.this);
                StringRequest request = new
                        StringRequest(Request.Method.GET, url,

                        new Response.Listener<String>(){
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(getApplicationContext(), response,
                                        Toast.LENGTH_SHORT).show();

                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(getApplicationContext(), "Error:" +
                                        error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                );


                queue.add(request);

            }
        });
    }

    public void ledStat(View v){



    }












    // this is for login
    private void postDataUsingVolley(final String val, final String val2) {

        // url to post our data
       /* String url = "https://seniorprojectdatabase.000webhostapp.com/dbwrite.php";

        // loading pogress bar this is optional
        loadingPB.setVisibility(View.VISIBLE);

        // creating a new variable for our request queue
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);

        // on below line we are calling a string
        // request method to post the data to our API
        // in this we are calling a post method.
        StringRequest request = new StringRequest(Request.Method.POST, url, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // inside on response method we are
                // hiding our progress bar
                loadingPB.setVisibility(View.GONE);

                // on below line we are displaying a success toast message.
                //Toast.makeText(MainActivity.this, "Data added to API", Toast.LENGTH_SHORT).show();
                try {
                    // on below line we are passing our response
                    // to json object to extract data from it.
                    JSONObject respObj = new JSONObject(response);

                    // below are the strings which we
                    // extract from our json object.
                    String result = respObj.getString("result");
                    String username = respObj.getString("uname");

                    // we just toast the value we got from API, 1 for success, 0 otherwise
                    Toast.makeText(MainActivity.this, "result is " + result + ", Hi " + username, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // method to handle errors.
                Toast.makeText(MainActivity.this, "Fail to get response = " + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // below line we are creating a map for
                // storing our values in key and value pair.
                Map<String, String> params = new HashMap<String, String>();

                // on below line we are passing our key
                // and value pair to our parameters.
                params.put("val", val);
                params.put("val2", val2);
                // put some code for verfication that the post came from your mobile app
                //params.put("login", "some-code-here");

                // at last we are
                // returning our params.
                return params;
            }
        };
        // below line is to make
        // a json object request.
        queue.add(request);*/

        /*String url ="https://seniorprojectdatabase.000webhostapp.com/dbwrite.php?sendval=" + sendval +
                "&sendval2=" + sendval2;

        RequestQueue queue =
                Volley.newRequestQueue(MainActivity.this);
        StringRequest request = new
                StringRequest(Request.Method.GET, url,

                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response,
                                Toast.LENGTH_SHORT).show();
                        tx.setText("value sent");

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "Error:" +
                                error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        queue.add(request); */



    }

}