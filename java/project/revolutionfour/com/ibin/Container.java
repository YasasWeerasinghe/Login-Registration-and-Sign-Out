package project.revolutionfour.com.ibin;
//HCI Issue 01
//HCI Issue 02
//HCI Issue 03
//HCI Issue 04
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.io.OutputStream;

public class Container extends AppCompatActivity {
    //encapsulating variables
    private Button onOff;
    private Button manual;
    private TextView signOut;
    private Intent intent;
    private Toast toast;
    private OutputStream outStream = null;
    private Boolean onoff;

    private FirebaseAuth firebaseAuth;

   private SharedPreferences spOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_container);

        firebaseAuth = FirebaseAuth.getInstance(); // initializing the firebase Auth object

        // initializing in the app with name “login” and mode “private”
        spOut = getSharedPreferences("login",MODE_PRIVATE);


        //finding and setting variables
        onOff = findViewById(R.id.on_off);
        manual = findViewById(R.id.manual);
        signOut = findViewById(R.id.signOut);
        //creating a global toast
        toast = new Toast(this);
        //setting value to device on/off switch
        onoff= false;
        onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activate();
            }
        });
        manual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                turnManual();
            }
        });
        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              //  spOut.edit().putBoolean("logged",false).apply();
                userSignOut();  //  call the sign out method

            }
        });
    }

    public void activate() {
        //checking whether the vacuum already on or off
        if(!onoff){
            //turning on vacuum
            sendData("O");
            onoff=true;
        }if(onoff){
            //turning off vacuum
            sendData("N");
        }
    }
    //Using intents to call manualMode method
    public void turnManual(){
        intent = new Intent(this,manualMode.class);
        startActivity(intent);
    }
       // Siging out user class
    public void userSignOut(){
        spOut.edit().remove("logged").commit();// remove all data saved in sharedPreferences
        firebaseAuth.signOut(); // signOut from the firebase
        finish(); // close the current activity
        startActivity(new Intent(this, MainActivity.class)); // open the login screen
    }
    //sending data method to arduino using outStream
    public void sendData(String msg) {
        byte[] charMsg = msg.getBytes();
        toast.makeText(this,"Sending data", Toast.LENGTH_LONG).show();
        try {
            outStream.write(charMsg);
        } catch (IOException e) {
            toast.makeText(this,"Data sending failed",Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() { // prevent the score window open
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }

}
