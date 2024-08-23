package com.cwtstudio.expensemanager.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.cwtstudio.expensemanager.R;
import com.cwtstudio.expensemanager.repository.DatabaseHelper;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private EditText editTextFullName;
    private EditText editTextGender;
    private EditText editTextAge;
    private EditText editTextEmail;
    private EditText editTextPhone;
    private Button buttonRegister;
    private TextView textViewLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextFullName = findViewById(R.id.editTextFullName);
        editTextGender = findViewById(R.id.editTextGender);
        editTextAge = findViewById(R.id.editTextAge);
        editTextEmail = findViewById(R.id.editTextEmail);
        editTextPhone = findViewById(R.id.editTextPhone);
        buttonRegister = findViewById(R.id.buttonRegister);
        textViewLogin = findViewById(R.id.textViewLogin);

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle registration logic here
                String username = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                String fullName = editTextFullName.getText().toString();
                String gender = editTextGender.getText().toString();
                int age = Integer.parseInt(editTextAge.getText().toString());
                String email = editTextEmail.getText().toString();
                String phone = editTextPhone.getText().toString();

                DatabaseHelper db = new DatabaseHelper(RegisterActivity.this);
                db.addUser(username, password, fullName, gender, age, email, phone);

                // Redirect to login page after registration
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        textViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}
