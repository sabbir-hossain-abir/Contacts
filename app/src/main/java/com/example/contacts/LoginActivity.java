package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField, passwordErrorText;
    private Button loginButton;
    private TextView signupLink;
    SharedPreferences preferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        loginButton = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);

        preferences  = getSharedPreferences("Userinfo", 0);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                String signupUserName = preferences.getString("username","");
                String singupPassword = preferences.getString("password","");


                    if (!validateUsername(username)) {
                        usernameField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        Toast.makeText(LoginActivity.this, "Username must be at least 4 characters!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if (!validatePassword(password)) {
                        passwordField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        Toast.makeText(LoginActivity.this, "Password must be at least 8 characters and contain at least one uppercase letter!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // credential verification
                    else {

                        if (username.equals(signupUserName) && password.equals(singupPassword)) {

                            //Useless
//                            SharedPreferences.Editor editor = preferences.edit();
//                            editor.putString("username", username);
//                            editor.putString("password", password);
//                            editor.putBoolean("remember-me", false);
//                            editor.apply();

                            Toast.makeText(LoginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                            // Navigate to Contact List Activity
                            Intent intent = new Intent(LoginActivity.this, ContactListActivity.class);
                            startActivity(intent);
                        }
                        else {
                            Toast.makeText(LoginActivity.this, "Invalid User Information", Toast.LENGTH_SHORT).show();
                        }
                    }

            }
        });

        signupLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignupActivity.class));
            }
        });
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            showError(passwordErrorText, "Username cannot be empty!");
            return false;
        }
        return true;
    }

    private boolean validatePassword(String password) {
        if (password.isEmpty()) {
            showError(passwordErrorText, "Password cannot be empty!");
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


