package com.example.courseratingapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Intent;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.courseratingapp.domain.User;



public class MainActivity extends AppCompatActivity {

    private final static String TAG = "MainActivity";

    // Variable that holds the current state of the email input text field, password does not persist through orientation changes //
    private final static String EMAIL_STATE = "";

    // Key name for the User Object value //
    public final static String USER_KEY = "USER";

    public final static int RETURNED_USER_KEY = 1;

    private TextView appName;
    private EditText email;
    private EditText password;
    private Button SignIn;
    private Button Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(TAG, getString(R.string.onCreate));
        init();

        // We restore the state if there is one //
        if (savedInstanceState != null){
            email.setText(savedInstanceState.getString(EMAIL_STATE));
        }

        final SharedPreferences sharedPreferences = getApplication().getSharedPreferences(getString(R.string.shared_preferences_create_user_key), Context.MODE_PRIVATE);


        // The Sign In button. Starts the CoursesPageActivity and sends the user info along as Extra //
        SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, getString(R.string.onClick));

                Intent intent = new Intent(MainActivity.this, com.example.courseratingapp.CoursesPageActivity.class);

                String email = MainActivity.this.email.getText().toString();
                String password = MainActivity.this.password.getText().toString();
                User user = new User(email, password);

                // If users does not fill out a field we display a Toast //
                if (MainActivity.this.email.getText().toString().isEmpty() || MainActivity.this.password.getText().toString().isEmpty()) {
                    Toast.makeText(MainActivity.this, getString(R.string.fill_out_fields), Toast.LENGTH_LONG).show();


                // Otherwise we check whether the info the user submitted is valid //
                } else {

                    // We check if he exists in our SharedPreferences //
                    String userDetails = sharedPreferences.getString(email + password, getString(R.string.user_not_found));
                    Log.d(TAG, userDetails);
                    // If he does we let him continue //
                    if (userDetails.contains(email + password)) {
                        intent.putExtra(USER_KEY, user);

                        startActivity(intent);

                    // If he does NOT, we let him know //
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.user_not_found), Toast.LENGTH_LONG).show();
                    }

                }
            }
        });

        // Sends the user to the Sign Up page //
        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent register = new Intent(MainActivity.this, com.example.courseratingapp.CreateUserActivity.class);
                startActivityForResult(register, RETURNED_USER_KEY);
            }
        });

    }

    // We receive the result from the User inputting their data and type in their credentials for them //
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        try {
            if (requestCode == RETURNED_USER_KEY) {
                if (resultCode == RESULT_OK) {
                    //Create a new user based on the Intent data result //
                    User user = data.getParcelableExtra(getString(R.string.new_user));
                    //assign our two input fields to the User's data //
                    String userEmail = user.getEmail();
                    String userPassword = user.getPassword();
                    email.setText(userEmail);
                    password.setText(userPassword);
                }
                //In case the result is canceled, i.e. the user has hit the back button, we let them know this has occurred //
                if (resultCode == RESULT_CANCELED) {
                    // In case the user tries to sign up several times, we clear the input fields //
                    email.setText(getString(R.string.empty_string));
                    password.setText(getString(R.string.empty_string));
                    Toast.makeText(this, getString(R.string.toast_canceled_sign_up), Toast.LENGTH_LONG).show();
                }
                if (resultCode == RESULT_FIRST_USER) {
                    email.setText(getString(R.string.empty_string));
                    password.setText(getString(R.string.empty_string));
                }

            } else {
                // If some unforeseen event happens simply log it //
                Log.d(TAG, getString(R.string.canceled_sign_up));
            }
        // If our parcelable data is null we will get a NullPointerException //
        } catch (NullPointerException e) {
            Log.d(TAG, e + getString(R.string.data_null));
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, getString(R.string.onSaveInstanceState));

        outState.putString(EMAIL_STATE, email.getText().toString());
    }



    protected void init() {
        Log.d(TAG, getString(R.string.init));
        appName = findViewById(R.id.tv1);
        email = findViewById(R.id.et1);
        password = findViewById(R.id.et2);
        SignIn = findViewById(R.id.b1);
        Register = findViewById(R.id.b2);
    }
}