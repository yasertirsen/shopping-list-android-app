package com.example.ca1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;
    public static String USERNAME_KEY = "USERNAME";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText)findViewById(R.id.etUsername);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btnLogin);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validate(username.getText().toString(), password.getText().toString());
            }
        });
    }

    public void validate(String username, String password) {
        if(username.contains("\\S+") || password.contains("\\S+") || username.length() < 6 || password.length() < 6)
            Toast.makeText(getApplicationContext(), "Username and Password cannot have spaces or less than 6 characters", Toast.LENGTH_SHORT).show();
        else if((!username.equals("YacerTirsen")) || (!password.equals("123456")))
            Toast.makeText(getApplicationContext(), "Incorrect Username or Password!", Toast.LENGTH_SHORT).show();
        else {
            Intent intent = new Intent(MainActivity.this, ShoppingListActivity.class);
            intent.putExtra(USERNAME_KEY, username);
            startActivity(intent);
        }

    }
}