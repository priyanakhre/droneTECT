package com.example.a591669.c_uasnotficiation;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class SignupActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;

    private static final String TAG = "SignupActivity" ;
    private Button btnSignUp,btnLinkToLogIn;
    //private ProgressBar progressBar;

    private EditText signupInputEmail, signupInputPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        mAuth = FirebaseAuth.getInstance();

        //Initialize all objects (Username/Password EditTexts, Sign In/Log In buttons, and Progress BAr
        //progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkToLogIn = (Button) findViewById(R.id.btn_link_login);

        //When Sign up Button is clicked, call submitForm() method

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        //When Login button is clicked, move to Login Activity

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                LoginForm();//IS THIS NEEDED?
            }
        });

    }

    /**
     * Validating form
     */
    private void submitForm() {
        //get email and password text from the edit text
        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if(!checkEmail()) {   //if not valid email or password,  exit submitForm method
            return;
        }
        if(!checkPassword()) {
            return;
        }

        //progressBar.setVisibility(View.VISIBLE); //set Progress Bar as Visible
        //create user
        mAuth.createUserWithEmailAndPassword(email, password)    //Create a user with input email/password
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() { //When user is created
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());   //Display successful or Failure message to LogCat
                        //progressBar.setVisibility(View.GONE);   //Remove Progress Bar
                        // If sign in fails, Log the message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG,"Authentication failed." + task.getException());

                        } else {  //if successful
                            startActivity(new Intent(SignupActivity.this, UserActivity.class));  //start an activity of the User Activity
                            finish();
                        }
                    }
                });
        Toast.makeText(getApplicationContext(), "You are successfully Registered", Toast.LENGTH_SHORT).show();  //Put up Toast of successfully Registering
    }

    private void LoginForm() {
        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if(!checkEmail()) {
            return;
        }
        if(!checkPassword()) {
            return;
        }


        //progressBar.setVisibility(View.VISIBLE);
        //authenticate user
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        // If sign in fails, Log a message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                      //  progressBar.setVisibility(View.GONE);
                        if (!task.isSuccessful()) {
                            // there was an error
                            Toast.makeText(SignupActivity.this, getString(R.string.auth_failed), Toast.LENGTH_LONG).show();

                        } else {
                            Intent intent = new Intent(SignupActivity.this, MapsNotifyActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });
    }


    private boolean checkEmail() {

        String email = signupInputEmail.getText().toString().trim(); //get string of email
        if (email.isEmpty() || !isEmailValid(email)) {    //if email is blank or not valid


            signupInputEmail.setError(getString(R.string.err_msg_required));    //Send "required" error message
            requestFocus(signupInputEmail);  //Gives Focus to the TextView
            return false;
        }

        return true;
    }

    private boolean checkPassword() {

        String password = signupInputPassword.getText().toString().trim(); //Get password string
        if (password.isEmpty() || !isPasswordValid(password)) { //if empty or not valid (length must be greater than 6), display "Required" error message


            signupInputPassword.setError(getString(R.string.err_msg_required));
            requestFocus(signupInputPassword);
            return false;
        }

        return true;
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }
}