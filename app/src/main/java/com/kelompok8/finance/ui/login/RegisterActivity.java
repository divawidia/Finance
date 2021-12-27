package com.kelompok8.finance.ui.login;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;

import org.w3c.dom.Text;

public class RegisterActivity extends AppCompatActivity {
    DBHelper db;
    TextView login;
    Button register;
    EditText username, password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);

        db = new DBHelper(this);

        email = findViewById(R.id.emailregis);
        username = findViewById(R.id.usernameregis);
        password = findViewById(R.id.passregis);
        register = findViewById(R.id.btnregis);
        login = findViewById(R.id.btnmasuk);


        //login
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent (RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
                finish();
            }
        });


        //register
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strEmail = email.getText().toString();
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Boolean daftar = db.insertUser(strEmail, strUsername, strPassword);
                if (daftar){
                    Toast.makeText(getApplicationContext(), "Pendaftaran berhasil", Toast.LENGTH_SHORT).show();
                    Intent loginIntent = new Intent (RegisterActivity.this, LoginActivity.class);
                    startActivity(loginIntent);
                    finish();
                }
                else{
                    Toast.makeText(getApplicationContext(), "Pendaftaran gagal", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}