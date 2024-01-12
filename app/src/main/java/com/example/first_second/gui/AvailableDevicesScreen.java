package com.example.first_second.gui;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.first_second.R;
import com.example.first_second.bluetooth.BluetoothImpl;
import com.example.first_second.bluetooth.BluetoothObserver;
import com.example.first_second.databinding.AvailabledevicesscreenBinding;

public class AvailableDevicesScreen extends Fragment {
    private AvailabledevicesscreenBinding binding;
    private Context context;
    private MainActivity activity;
    private RecyclerView recyclerView;
    private BluetoothObserver bo;
    private String recipe_name;
    private String ingredients;
    private String directions;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AvailabledevicesscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        activity = (MainActivity)getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewAvailableDevices);
        getAndSetArgData();
        bo = new DeviceRecyclerViewAdapter(context, AvailableDevicesScreen.this,
                recipe_name, ingredients, directions);
        BluetoothImpl bluetooth = new BluetoothImpl(context, activity);
        bluetooth.addBluetoothObserver(bo);
        recyclerView.setAdapter((DeviceRecyclerViewAdapter)bo);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bluetooth.findDevices();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getAndSetArgData(){
        //Getting Data from Args
        recipe_name = AvailableDevicesScreenArgs.fromBundle(getArguments()).getRecipeName();
        ingredients = AvailableDevicesScreenArgs.fromBundle(getArguments()).getIngredients();
        directions = AvailableDevicesScreenArgs.fromBundle(getArguments()).getDirections();
    }
}