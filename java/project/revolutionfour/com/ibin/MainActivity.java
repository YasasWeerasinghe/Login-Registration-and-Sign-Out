package project.revolutionfour.com.ibin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Yasas Weerasingh on 01-Apr-18.
 */

public class MainActivity extends AppCompatActivity {

    // variable declaration
    private EditText loginUserName;
    private EditText loginPasword;

    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    private SharedPreferences sp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loginUserName = (EditText) findViewById(R.id.txtUserName);
        loginPasword = (EditText) findViewById(R.id.txtPassword);

        progressDialog = new ProgressDialog(this);  // initializing the progressDialog object
        auth = FirebaseAuth.getInstance(); // initializing the firebase Auth object

        // initializing in the app with name “login” and mode “private”
        sp = getSharedPreferences("login",MODE_PRIVATE);

        //  if sp get false mean logging is not happen
        if(sp.getBoolean("logged",false)){
                // so when sp false it always open the logging screen
            startActivity(new Intent(MainActivity.this, Container.class));
            //System.out.println("SP1***"+ sp.toString());
        }

    }


    public void Login(){
        //initializing textView data to the String variables
        String userName = loginUserName.getText().toString().trim();
        String password = loginPasword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){ // if the userName field is empty
            Toast.makeText(this,"Please enter the Email",Toast.LENGTH_SHORT).show(); // show toast
            return; // stop further function execution
        }

        if(TextUtils.isEmpty(password)){ // if the password field is empty
            Toast.makeText(this,"Please enter the Password",Toast.LENGTH_SHORT).show(); // show toast
            return; // stop further function execution
        }

        progressDialog.setMessage("Login User"); // set a text to the progress dialog
        progressDialog.show(); // show a message when data sending to he firebase

        auth.signInWithEmailAndPassword(userName,password).
                addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // use this listener to show the toast and to call the activities
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) { // this method call when sign in complete
                        progressDialog.dismiss(); // cancel the progress dialog
                        if(task.isSuccessful()){ //  is sign in complete
//                            sp.edit().putBoolean("logged",true).apply();
//                            System.out.println("SP2***"+ sp.toString());
                            Toast.makeText(MainActivity.this,"Login successfully",Toast.LENGTH_SHORT).show(); // show the toast
                            finish();// finish the current activity before start the new activity
                            startActivity(new Intent(MainActivity.this, Container.class));  // open the new activity  to proseed
                        } else{
                            Toast.makeText(MainActivity.this,"Login failed. Please try again.",Toast.LENGTH_SHORT).show(); // show the toast
                        }
                    }
                });
    }

    // login button method
    public void bLogin(View view){
        Login(); // login method
    }

    // register link method
    public void RegisterLink(View view){
        finish(); // close the current activity
        // start a  new activity
        startActivity(new Intent(MainActivity.this, Registration.class));
    }

    // prevent the ending the app when touch the back button without going to privies activity
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);   // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity

    }

}
