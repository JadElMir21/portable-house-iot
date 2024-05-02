package com.example.yourportablehouse;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainPageMenu extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.info)
        {
            Intent intent = new Intent(MainPageMenu.this, loginInfo.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page_menu);

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);

       if(!myPref.contains("homeEmail")){
            Intent intent = new Intent(MainPageMenu.this, loginPage.class);
            startActivity(intent);
            finish();
        }
    }
}