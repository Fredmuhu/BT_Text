package com.example.bttext;

import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Set;

public class List_Paired_Devices extends AppCompatActivity {
ListView paired_devices;
BluetoothAdapter myBluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list__paired__devices);
        paired_devices=(ListView) findViewById(R.id.lst_paired);
        myBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
        display_paired_devices();
    }

    private void display_paired_devices() {
        Set<BluetoothDevice> bt=myBluetoothAdapter.getBondedDevices();
        String[] strings=new String[bt.size()];
        int index=0;

            if (bt.size() > 0) {
                for (BluetoothDevice device : bt) {
                    strings[index] = device.getName();
                    index++;
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, strings);
                paired_devices.setAdapter(arrayAdapter);
            }
        }

}
