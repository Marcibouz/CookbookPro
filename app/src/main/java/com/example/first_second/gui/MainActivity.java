package com.example.first_second.gui;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.first_second.R;

import androidx.appcompat.app.AppCompatActivity;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.bluetooth.BluetoothHelper;
import com.example.first_second.databinding.ActivityMainBinding;
import com.example.first_second.local_memory.DatabaseHelper;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private Drawable nothingHereYetBackground;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        nothingHereYetBackground =
                ResourcesCompat.getDrawable(getResources(), R.drawable.nothinghereyet, null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        getSupportActionBar().setTitle("Cookbook Pro");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // noinspection SimplifiableIfStatement
        if (item.getItemId() == R.id.action_receiveViaBluetooth) {
            Toast.makeText(this,"Bluetooth", Toast.LENGTH_SHORT).show();
            BluetoothHelper bluetoothHelper = new BluetoothHelper();
            bluetoothHelper.startDiscoverable();
        }
        if (item.getItemId() == R.id.action_deleteAll) {
            DatabaseHelper db = new DatabaseHelper(this);
            db.deleteAllRecipes();
            RecipeRecyclerViewAdapter recipeRecyclerViewAdapter =
                    new RecipeRecyclerViewAdapter(this, new LinkedList<String>(),
                            new LinkedList<String>(), new LinkedList<String>(),
                            new LinkedList<String>());
            RecyclerView recyclerView = findViewById(R.id.recyclerView);
            recyclerView.setAdapter(recipeRecyclerViewAdapter);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            ConstraintLayout recipeListLayout = findViewById(R.id.recipeListScreen);
            recipeListLayout.setBackground(nothingHereYetBackground);
            Toast.makeText(this,"All Recipes Deleted", Toast.LENGTH_SHORT).show();
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