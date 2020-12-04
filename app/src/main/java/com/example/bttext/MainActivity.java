package com.example.bttext;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
Button buttonON, buttonOFF, buttonPairedDevices, buttonAvailableDevices, buttonDiscoverability;
TextView disc;
BluetoothAdapter myBluetoothAdapter;
Intent btEnablingIntent;
int EnableRequestCode;
IntentFilter discoverIntentFilter=new IntentFilter(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED);
BroadcastReceiver discoverable_receiver=new BroadcastReceiver() {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action=intent.getAction();
        if (action.equals(BluetoothAdapter.ACTION_SCAN_MODE_CHANGED)){
            int modeValue=intent.getIntExtra(BluetoothAdapter.EXTRA_SCAN_MODE, BluetoothAdapter.ERROR);
            if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE){
                disc.setText("The device is not discoverable but can still receive connections");
            }else if (modeValue==BluetoothAdapter.SCAN_MODE_CONNECTABLE_DISCOVERABLE){
                disc.setText("The device is discoverable");
            }else if (modeValue==BluetoothAdapter.SCAN_MODE_NONE){
                disc.setText("The device is not discoverable and cannot receive any connection");
            }else {
                disc.setText("Error");
            }
        }
    }
};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonON= (Button) findViewById(R.id.btON);
        buttonOFF= (Button) findViewById(R.id.btOFF);
        buttonPairedDevices= (Button) findViewById(R.id.bt_Paired);
        buttonAvailableDevices= (Button) findViewById(R.id.bt_scan);
        disc=(TextView) findViewById(R.id.textView);
        myBluetoothAdapter= BluetoothAdapter.getDefaultAdapter();
        btEnablingIntent= new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        EnableRequestCode=1;
        bluetoothON();
        bluetoothOFF();
        bluetoothPaired();
        bluetoothScan();
    }
    private void bluetoothScan() {
        buttonAvailableDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoList_Available_Devices();
            }
        });
    }

    private void movetoList_Available_Devices() {
        Intent intent = new Intent(MainActivity.this, list_available_devices.class);
        startActivity(intent);
    }

    private void bluetoothPaired() {
        buttonPairedDevices.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                movetoList_Paired_Devices();
            }
        });
    }

    private void movetoList_Paired_Devices() {
        Intent intent = new Intent(MainActivity.this, List_Paired_Devices.class);
        startActivity(intent);
    }

    private void bluetoothOFF()
    {
        buttonOFF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(myBluetoothAdapter.isEnabled())
                {
                   myBluetoothAdapter.disable();
                }
                }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (requestCode==EnableRequestCode){
            if (requestCode==RESULT_OK)
            {
             Toast.makeText(getApplicationContext(), "Bluetooth is enabled", Toast.LENGTH_LONG).show();
            }
            else if(requestCode==RESULT_CANCELED)
            {
             Toast.makeText(getApplicationContext(), "Bluetooth Enabling Cancelled", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void bluetoothON()
    {
        buttonON.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBluetoothAdapter==null)
                {
                    Toast.makeText(getApplicationContext(), "Bluetooth is not supported on this device", Toast.LENGTH_LONG).show();
                }
                else
                    {
                    if (!myBluetoothAdapter.isEnabled())
                    {
                        startActivityForResult(btEnablingIntent, EnableRequestCode);

                    }
                }
            }
        });
    }
}
