package project.revolutionfour.com.ibin;
//HCI Issue 01
//HCI Issue 02
//HCI Issue 03
//HCI Issue 04
//SEC Issue 04
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.widget.Toast;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;
import android.bluetooth.BluetoothSocket;

public class

connectScreen extends AppCompatActivity {
    //Encapsulating global variables
    private Button connectBtn;
    private BluetoothAdapter bluetoothAdapter = null;
    private BluetoothSocket bluetoothSocket = null;
    private OutputStream outStream = null;
    private static final UUID MY_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB"); //UUID to connect to device
    private static String address = "98:D3:32:11:03:44"; // MAC address of the device
    private Toast toast;
    //Request enable to handle bluetooth turning on
    private final static int REQUEST_ENABLE_BT = 1;
    private Intent intent;


    @RequiresApi(api = Build.VERSION_CODES.ECLAIR)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connect_screen);
        //finding and setting variables
        connectBtn = findViewById(R.id.connect);
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        toast = new Toast(this);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startD();
            }
        });
    }

    private void startD() {
        //to check bluetooth connections
        checkBluetooth();
    }
    public void checkBluetooth(){
        if(bluetoothAdapter==null) {
            //Bluetooth does not support
            toast.makeText(this,"Invalid Bluetooth",Toast.LENGTH_LONG).show();
        } else {
            if (bluetoothAdapter.isEnabled()) {
                //bluetooth enabled and connected
                toast.makeText(this,"Enabled Bluetooth",Toast.LENGTH_LONG).show();
                intent = new Intent(this, project.revolutionfour.com.ibin.Container.class);
                startActivity(intent);

            } else {
                //Prompting user to turn on Bluetooth
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent,REQUEST_ENABLE_BT);
            }
        }
    }

    //overriding onResume method
    @Override
    public void onResume() {
        super.onResume();
        toast.makeText(this,"Connecting to user...",Toast.LENGTH_LONG).show();
        //creating a bluetooth device with the device MAC address
        BluetoothDevice device = bluetoothAdapter.getRemoteDevice(address);
        //trying to connect socket to device
        try {
            bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID);
        } catch (IOException e) {
        }
        //cancelling bluetooth discovery
        bluetoothAdapter.cancelDiscovery();
        //connecting to the appropriate device
        try {
            bluetoothSocket.connect();
            toast.makeText(this,"Successfully connected to user...",Toast.LENGTH_LONG).show();
        } catch (IOException e) {
            try {
                bluetoothSocket.close();
            } catch (IOException e2) {
                toast.makeText(this,"Connection failed",Toast.LENGTH_LONG).show();
            }
        }

        try {
            outStream = bluetoothSocket.getOutputStream();
        } catch (IOException e) {
        }
    }
    //overriding onPause method
    @Override
    public void onPause() {
        super.onPause();
        //checking whether any information is passed
        if (outStream != null) {
            try {
                //if not null clearing the outStream
                outStream.flush();
            } catch (IOException e) {
            }
        }
        //closing the socket
        try  {
            bluetoothSocket.close();
            toast.makeText(this,"Bluetooth disconnected",Toast.LENGTH_LONG).show();
        } catch (IOException e2) {
        }
    }




}
