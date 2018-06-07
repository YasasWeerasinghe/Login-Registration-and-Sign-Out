package project.revolutionfour.com.ibin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by Yasas Weerasingh on 01-Apr-18.
 */

public class Registration extends MainActivity {
    // variable declaration
    private EditText UserName;
    private EditText Password;
    private EditText rePassword;

    private ProgressDialog progressDialog;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        // textview initialization with the xml textviews
        UserName=(EditText) findViewById(R.id.txtRegUserName);
        Password=(EditText) findViewById(R.id.txtRegPassword);
        rePassword=(EditText) findViewById(R.id.txtRegRePassword);


        progressDialog = new ProgressDialog(this);  // initializing the progressDialog object
        auth = FirebaseAuth.getInstance(); // initializing the firebase Auth object


    }
    // User Registering method
    private void Register(){
        //initializing textView data to the String variables
        String userName = UserName.getText().toString().trim();
        String password = Password.getText().toString().trim();
        String repassword= rePassword.getText().toString().trim();

        if(TextUtils.isEmpty(userName)){ // if the userName field is empty
            Toast.makeText(this,"Please enter the Email",Toast.LENGTH_SHORT).show(); // show toast
            return; // stop further function execution
        }
        if(TextUtils.isEmpty(password)){ // if the password field is empty
            Toast.makeText(this,"Please enter the Password",Toast.LENGTH_SHORT).show(); // show toast
            return; // stop further function execution

        } else {
            if(password.length()<6){ // check password character length
                Toast.makeText(Registration.this,"Password must contain least 6 characters",Toast.LENGTH_SHORT).show(); // show the toast
            }else

            if(password.equals(repassword)){ // check both passwords are same or not

                progressDialog.setMessage("Registering User"); // set a text to the progress dialog
                progressDialog.show(); //  show a message when data sending to he firebase
                // use this method to store the to string arguments to the firebase
                auth.createUserWithEmailAndPassword(userName,password).
                        addOnCompleteListener(this, new OnCompleteListener<AuthResult>() { // use this listener to the add toast and when registration finish go to the login screen
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){ // when task success
                                    Toast.makeText(Registration.this,"Registered successfully",Toast.LENGTH_SHORT).show(); // show the toast
                                    finish(); // finish the current activity
                                    startActivity(new Intent(Registration.this, MainActivity.class)); // open the login screen
                                }else {
                                    Toast.makeText(Registration.this,"Registration failed. Please try again.",Toast.LENGTH_SHORT).show(); // show the toast
                                }
                                progressDialog.dismiss(); // close the progressDialog
                            }
                        });

            }else{ // passwords are not same show the error toast
                Toast.makeText(Registration.this,"Passwords not mach. Please try again.",Toast.LENGTH_SHORT).show(); // show the toast
                // set password fields empty
                Password.setText("");
                rePassword.setText("");

            }
        }



    }
    // Register button method
    public void bRegisterUser(View view){
        Register(); // user registration method is calling
    }

    // Already registered link to go the Login screen
    public void alreadyRegisteredLink(View view){
        finish(); // stop the current activity
        startActivity(new Intent(Registration.this, MainActivity.class)); // open a new activity
    }

    @Override
    public void onBackPressed() { // prevent the score window open
        Intent intent = new Intent(this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // This flag is used to create a new task and launch an activity into it.
        startActivity(intent);  // start the activity
    }


}
