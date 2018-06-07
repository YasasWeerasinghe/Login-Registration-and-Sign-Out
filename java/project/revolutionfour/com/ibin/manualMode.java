package project.revolutionfour.com.ibin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import java.io.IOException;
import java.io.OutputStream;
//HCI Issue 01
//HCI Issue 02
//HCI Issue 03
//HCI Issue 04
public class manualMode extends AppCompatActivity {
    //encapsulated Global Variables
    private Toast toast;
    private OutputStream outStream = null;
    private ImageButton front,back,right,left;
    private Switch vacuumSwitch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manual_mode);
        //passing letter 'm' to arduino
        sendData("M");
        //finding and setting variables
        toast = new Toast(this);
        front = findViewById(R.id.front);
        back = findViewById(R.id.back);
        right = findViewById(R.id.right);
        left = findViewById(R.id.left);
        vacuumSwitch =findViewById(R.id.vacuumSwitch);
        // if user wants the device to go front the android passes letter 'f'
        front.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("F");
            }
        });
        // if user wants the device to go back the android passes letter 'b'
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("B");
            }
        });
        // if user wants the device to go right the android passes letter 'r'
        right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("R");
            }
        });
        // if user wants the device to go left the android passes letter 'l'
        left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendData("L");
            }
        });
        // if user wants to turn on/off vacuum the android passes letter 'o' or 'n'
        vacuumSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
              if(isChecked) {
                  sendData("O");
              }if(!isChecked){
                  sendData("N");
                }
            }
        });
    }
    //send data method which passes data to arduino using outStream
    public void sendData(String msg) {
        byte[] charMsg = msg.getBytes();

        toast.makeText(this,"Sending data", Toast.LENGTH_LONG).show();

        try {
            outStream.write(charMsg);
        } catch (IOException e) {
            toast.makeText(this,"Data sending failed",Toast.LENGTH_LONG).show();

        }
    }
}
