package com.example.first_second.gui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.first_second.R;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.first_second.databinding.ActivityMainBinding;

import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        //Get Username from Shared Preferences
//        SharedPreferences preferences = getSharedPreferences("UserName", MODE_PRIVATE);
//        String userName = preferences.getString("UserName", null);
//
//        if (userName == null){
//            AlertDialog.Builder builder = new AlertDialog.Builder(this);
//            builder.setTitle("Enter Username");
//
//            // Set up the input
//            final EditText input = new EditText(this);
//            builder.setView(input);
//
//            // Set up the buttons
//            builder.setPositiveButton("OK", (dialog, which) -> {
//                        String newUsername = input.getText().toString();
//                        // Save the new username to shared preferences
//                        SharedPreferences sharedPreferences = getSharedPreferences("UserName", MODE_PRIVATE);
//                        SharedPreferences.Editor editor = sharedPreferences.edit();
//                        editor.putString("UserName", userName);
//                        editor.apply();
//            });
//            builder.setNegativeButton("Cancel", (dialog, which) -> dialog.cancel());
//
//            // Show the dialog
//            builder.show();
//        }

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        //getSupportActionBar().setTitle("Cookbook Pro");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}