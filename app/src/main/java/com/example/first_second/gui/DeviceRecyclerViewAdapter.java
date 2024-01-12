package com.example.first_second.gui;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.first_second.R;
import com.example.first_second.bluetooth.BluetoothHelper;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DeviceRecyclerViewAdapter extends
        RecyclerView.Adapter<DeviceRecyclerViewAdapter.DeviceViewHolder> implements
        BluetoothObserver{
    private Context context;
    private Fragment fragment;
    private List<BluetoothDevice> device = new LinkedList<>();
    private List<String> deviceName = new LinkedList<>();
    private String recipe_name;
    private String ingredients;
    private String directions;
    public DeviceRecyclerViewAdapter(Context context, Fragment fragment, String recipe_name,
                                     String ingredients, String directions){
        this.context = context;
        this.fragment = fragment;
        this.recipe_name = recipe_name;
        this.ingredients = ingredients;
        this.directions = directions;
    }
    @Override
    public void bondedDeviceAdded(BluetoothHelper bluetoothHelper){
        Map<BluetoothDevice, String> availableBondedDevices = bluetoothHelper.
                getAvailableBondedDevices();
        for (Map.Entry<BluetoothDevice, String> e: availableBondedDevices.entrySet()){
            if(!device.contains(e.getKey())){
                device.add(e.getKey());
                deviceName.add((e.getValue()));
            }
        }
        notifyDataSetChanged();
    }
    @Override
    public void availableDeviceAdded(BluetoothHelper bluetoothHelper){
        Map<BluetoothDevice, String> availableDevices = bluetoothHelper.getAvailableDevices();
        for (Map.Entry<BluetoothDevice, String> e: availableDevices.entrySet()){
            if(!device.contains(e.getKey())){
                device.add(e.getKey());
                deviceName.add((e.getValue()));
            }
        }
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public DeviceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.device_row, parent, false);
        return new DeviceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DeviceViewHolder holder, int position) {
        holder.device_name_txt.setText(deviceName.get(position));
        DeviceRowElementListener deviceRowElementListener = new DeviceRowElementListener(position);
        holder.deviceRowLayout.setOnClickListener(deviceRowElementListener);
    }

    @Override
    public int getItemCount() {
        return device.size();
    }

    public class DeviceViewHolder extends RecyclerView.ViewHolder{
        TextView device_name_txt;
        LinearLayout deviceRowLayout;
        public DeviceViewHolder(@NonNull View itemView) {
            super(itemView);
            device_name_txt = itemView.findViewById(R.id.device_name_txt);
            deviceRowLayout = itemView.findViewById(R.id.deviceRowLayout);
        }
    }

    private class DeviceRowElementListener implements View.OnClickListener{

        private String current_device_name;
        private BluetoothDevice current_device;
        private DeviceRowElementListener(int position){
            current_device_name = deviceName.get(position);
            current_device = device.get(position);
        }
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "Attempting to connect to device " + current_device_name,
                    Toast.LENGTH_LONG).show();
            BluetoothHelper bluetoothHelper = new BluetoothHelper(context, fragment.getActivity());
            bluetoothHelper.createClientThread(current_device, recipe_name, ingredients, directions);
        }
    }
}
