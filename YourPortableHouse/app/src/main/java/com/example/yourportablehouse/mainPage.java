package com.example.yourportablehouse;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.core.view.WindowCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.yourportablehouse.databinding.ActivityMainPageBinding;

public class mainPage extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainPageBinding binding;


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.info)
        {
            Intent intent = new Intent(mainPage.this, loginInfo.class);
            startActivity(intent);
        }
        if(item.getItemId()==R.id.logout){
            SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor e = myPref.edit();

            e.clear();
            e.apply();

            Intent intent = new Intent(mainPage.this, loginPage.class);
            startActivity(intent);
            finish();
        }

        return super.onOptionsItemSelected(item);

    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences myPref = getSharedPreferences("prefs", Context.MODE_PRIVATE);
        if(!myPref.contains("homeEmail")){
            Intent intent = new Intent(mainPage.this, loginPage.class);
            startActivity(intent);
            finish();
        }

        binding = ActivityMainPageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);



        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Contact us on yourportablehouse@gmail.com", Snackbar.LENGTH_LONG)
                        .setAnchorView(R.id.fab)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main_page);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void eye(View v){
        Intent intent = new Intent(mainPage.this, security.class);
        startActivity(intent);
        finish();
    }

    public void hand(View v){
        Intent intent = new Intent(mainPage.this, components.class);
        startActivity(intent);
        finish();
    }
}