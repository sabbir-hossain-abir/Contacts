package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class SignupActivity extends AppCompatActivity {

    private EditText usernameField, emailField, passwordField;
    private Button signupButton;
    private TextView loginLink, usernameErrorText, emailErrorText, passwordErrorText;
    private CheckBox signupcheckbox;
    SharedPreferences preferences, preferences1;

    private final static String EMAIL_PATTERN = "^[a-zA-Z0-9_+&*-/=?^|]{1,64}@[a-zA-Z0-9-]+(?:\\.[a-zA-Z0-9-]+)*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        preferences = getSharedPreferences("Userinfo", 0);


        usernameField = findViewById(R.id.username_field);
        emailField = findViewById(R.id.email_field);
        passwordField = findViewById(R.id.password_field);
        signupButton = findViewById(R.id.signup_button);
        loginLink = findViewById(R.id.login_link);
        usernameErrorText = findViewById(R.id.username_error_text);
        emailErrorText = findViewById(R.id.email_error_text);
        passwordErrorText = findViewById(R.id.password_error_text);
        signupcheckbox = findViewById(R.id.signup_checkbox);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String email = emailField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                    if (!validateUsername(username)) {
                        usernameField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        showError(usernameErrorText, "Username must be at least 4 characters and unique!");
                        return;
                    }

                    else if (!validateEmail(email)) {
                        emailField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        showError(emailErrorText, "Please enter a valid email address!");
                        return;
                    }

                    else if (!validatePassword(password)) {
                        passwordField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        showError(passwordErrorText, "Password must be at least 8 characters and contain at least one uppercase letter, one lowercase letter, one number, and one special character!");
                        return;
                    }
                    else {
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("username", usernameField.getText().toString());
                        editor.putString("email", emailField.getText().toString());
                        editor.putString("password", passwordField.getText().toString());
                        editor.apply();

//                        if(signupcheckbox.isChecked()){
//                            preferences = SignupActivity.this.getSharedPreferences("checkbox", 0);
//                            SharedPreferences.Editor editor1 = preferences.edit();
//                            editor.putString("remember-me", "true");
//                            editor1.apply();
//                        }
//                        else if(!signupcheckbox.isChecked()){
//                            preferences = SignupActivity.this.getSharedPreferences("checkbox", 0);
//                            SharedPreferences.Editor editor1 = preferences.edit();
//                            editor1.putString("remember-me", "false");
//                        }

                        Toast.makeText(SignupActivity.this,"Singup Completed", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, ContactListActivity.class);

                        startActivity(intent);

                        Log.d("SharedPreferences", "Saved username: "+username);
                        Log.d("Shared Preferences", "saved email: "+ email);


                    }
                }

        });

        loginLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignupActivity.this, LoginActivity.class));
            }
        });
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            showError(usernameErrorText, "Username cannot be empty!");
            return false;
        } else if (username.length() < 4) {
            return false;
        }
        return true;
    }

    private boolean validateEmail(String email) {
        return email.matches(EMAIL_PATTERN);
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            showError(passwordErrorText, "Password cannot be empty!");
            return false;
        } else if (password.length() < 8) {
            return false;
        } else if (!password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^\\w\\s]).*$")) {
            return false;
        }
        return true;
    }

    private void showError(TextView specificErrorText, String message) {
        if (specificErrorText != null) {
            specificErrorText.setVisibility(View.VISIBLE);
            specificErrorText.setText(message);
        }
    }

}

