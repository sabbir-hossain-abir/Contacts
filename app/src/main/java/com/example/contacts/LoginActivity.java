package com.example.contacts;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends AppCompatActivity {

    private EditText usernameField, passwordField, usernameErrorText,passwordErrorText;
    private CheckBox loginCheckbox;
    private Button loginButton;
    private TextView signupLink;
    SharedPreferences preferences, preferences1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameField = findViewById(R.id.username_field);
        passwordField = findViewById(R.id.password_field);
        loginCheckbox = findViewById(R.id.login_checkbox);
        loginButton = findViewById(R.id.login_button);
        signupLink = findViewById(R.id.signup_link);

        preferences  = getSharedPreferences("Userinfo", 0);
        preferences1 = getSharedPreferences("checkbox",0);
        String checkbox = preferences1.getString("remember","");
        if (checkbox.equals("true")){
            Intent intent = new Intent(LoginActivity.this, ContactListActivity.class);
            startActivity(intent);
        }else if (checkbox.equals("false")){
            Toast.makeText(this,"Your login info is unsaaved", Toast.LENGTH_SHORT).show();
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameField.getText().toString().trim();
                String password = passwordField.getText().toString().trim();

                String signupUserName = preferences.getString("username","");
                String singupPassword = preferences.getString("password","");


                    if (!validateUsername(username)) {
                        usernameField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        Toast.makeText(LoginActivity.this, "Enter a valid username", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if (!validatePassword(password)) {
                        passwordField.setBackgroundColor(getResources().getColor(R.color.error_highlight));
                        Toast.makeText(LoginActivity.this, "Incorrect Password", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    // credential verification
                    else {

                        if (username.equals(signupUserName) && password.equals(singupPassword)) {

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

        loginCheckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (compoundButton.isChecked()){
                    SharedPreferences preferences1 = getSharedPreferences("checkbox",0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("remember","true");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Checked", Toast.LENGTH_SHORT).show();
                } else if (!compoundButton.isChecked()) {
                    SharedPreferences preferences1 = getSharedPreferences("checkbox",0);
                    SharedPreferences.Editor editor = preferences1.edit();
                    editor.putString("remember","false");
                    editor.apply();
                    Toast.makeText(LoginActivity.this, "Unchecked", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateUsername(String username) {
        if (username.isEmpty()) {
            showError(usernameErrorText, "Username cannot be empty!");
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


