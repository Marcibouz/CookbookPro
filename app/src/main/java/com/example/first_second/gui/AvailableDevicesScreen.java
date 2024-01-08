package com.example.first_second.gui;

import static java.lang.Thread.sleep;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.first_second.R;
import com.example.first_second.bluetooth.BluetoothHelper;
import com.example.first_second.databinding.AvailabledevicesscreenBinding;

public class AvailableDevicesScreen extends Fragment {
    private AvailabledevicesscreenBinding binding;
    private Context context;
    private Activity activity;
    private RecyclerView recyclerView;
    private BluetoothObserver bo;
    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = AvailabledevicesscreenBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getContext();
        activity = getActivity();
        recyclerView = view.findViewById(R.id.recyclerViewAvailableDevices);
        bo = new DeviceRecyclerViewAdapter(context, AvailableDevicesScreen.this);
        BluetoothHelper bluetoothHelper = new BluetoothHelper(context, activity);
        bluetoothHelper.addBluetoothObserver(bo);
        recyclerView.setAdapter((DeviceRecyclerViewAdapter)bo);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        bluetoothHelper.findDevices();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}