package com.example.first_second.gui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.example.first_second.R;
import static com.example.first_second.bluetooth.BluetoothHelperImpl.SHARE_PERMISSIONS;
import static com.example.first_second.bluetooth.BluetoothHelperImpl.RECEIVE_PERMISSIONS;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.first_second.bluetooth.BluetoothHelper;
import com.example.first_second.bluetooth.BluetoothHelperImpl;
import com.example.first_second.databinding.ActivityMainBinding;
import com.example.first_second.local_memory.LocalMemoryImpl;
import com.example.first_second.local_memory.LocalMemory;

import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMainBinding binding;
    private MenuInflater menuInflater;
    private Drawable nothingHereYetBackground;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        menuInflater = getMenuInflater();

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        //ddOnDestinationChangedListener fuer navController
        DestinationChangedListener destinationChangedListener =
                new DestinationChangedListener();
        navController.addOnDestinationChangedListener(destinationChangedListener);

        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        nothingHereYetBackground =
                ResourcesCompat.getDrawable(getResources(), R.drawable.nothinghereyet, null);

        //Permission Request (Share)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, SHARE_PERMISSIONS, 0);
        }
        //Permission Request (Receive)
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_ADVERTISE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, RECEIVE_PERMISSIONS, 0);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Logic for not displaying 3 dot menu outside of RecipeListScreen
        getSupportActionBar().setTitle("Cookbook Pro");
        NavController navController =
                Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavDestination currentDestination = navController.getCurrentDestination();

        if (currentDestination != null){
            int currentFragmentId = currentDestination.getId();
            if(currentFragmentId != R.id.RecipeListScreen){
                return false;
            }
        }
        // Inflate the menu; adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        // noinspection SimplifiableIfStatement

        if (item.getItemId() == R.id.action_receiveViaBluetooth) {
            BluetoothHelper bluetoothHelper = new BluetoothHelperImpl(this, this);
            bluetoothHelper.startDiscoverable();
        }
        if (item.getItemId() == R.id.action_deleteAll) {
            LocalMemory lm = LocalMemoryImpl.getDatabaseHelper(this);
            lm.deleteAllRecipes();
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

    private class DestinationChangedListener implements NavController.OnDestinationChangedListener {
        @Override
        public void onDestinationChanged(@NonNull NavController navController,
                                         @NonNull NavDestination currentDestination,
                                         @Nullable Bundle bundle) {
            binding.toolbar.getMenu().clear();
            int currentFragmentId = currentDestination.getId();
            if (currentFragmentId == R.id.RecipeListScreen){
                menuInflater.inflate(R.menu.menu_main, binding.toolbar.getMenu());
            }
        }
    }

    public void showToast(String message){
        MainActivity.this.runOnUiThread(() -> Toast.makeText(this, message,
                Toast.LENGTH_SHORT).show());
    }
}