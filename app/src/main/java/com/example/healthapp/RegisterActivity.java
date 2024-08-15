package com.example.healthapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button registerButton;
    private TextView haveAccountTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        EdgeToEdge.enable(this);

        usernameEditText = findViewById(R.id.editTextAppFullName);
        emailEditText = findViewById(R.id.editTextAppAddress);
        passwordEditText = findViewById(R.id.editTextAppContactNumber);
        confirmPasswordEditText = findViewById(R.id.editTextAppFees);
        registerButton = findViewById(R.id.buttonAppBack);
        haveAccountTextView = findViewById(R.id.account);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String email = emailEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();
                Database db = new Database(getApplicationContext(),"Healthcare",null,1);
                if (isEmpty(username, email, password, confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Please fill all details", Toast.LENGTH_LONG).show();
                } else if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_LONG).show();
                } else if (!isValid(password)) {
                    Toast.makeText(RegisterActivity.this, "Password must be at least 8 characters with letters, digits, and  one special symbols", Toast.LENGTH_LONG).show();
                } else {
                    // Perform secure registration logic here (e.g., send data to server)
                    db.register(username,email,password);
                    Toast.makeText(RegisterActivity.this, "Record inserted", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                }
            }
        });

        haveAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

    private boolean isEmpty(String... fields) {
        for (String field : fields) {
            if (field.isEmpty()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValid(String password) {
        if (password == null || password.length() < 8) {
            return false;
        }

        boolean hasLetter = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isLetter(c)) {
                hasLetter = true;
            } else if (Character.isDigit(c)) {
                hasDigit = true;
            } else if (c >= 33 && c <= 47 || c == 64) {
                hasSpecialChar = true;
            }
        }

        return hasLetter && hasDigit && hasSpecialChar;
    }
}

