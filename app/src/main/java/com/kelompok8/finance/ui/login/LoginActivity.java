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
import com.kelompok8.finance.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    DBHelper db;
    Button buttonlogin;
    TextView register;
    EditText username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new DBHelper(this);

        username = findViewById(R.id.edtusername);
        password = findViewById(R.id.edtpassword);
        buttonlogin = findViewById(R.id.btnlogin);
        register = findViewById(R.id.txvregister);

        //login
        buttonlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();
                Boolean masuk = db.checkLogin(strUsername, strPassword);
                if (masuk == true){
                    Boolean updateSession = db.upgradeSession("ada", 1);
                    if (updateSession == true){
                        Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
                        Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
                        startActivity(mainIntent);

                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();                }
            }
        });

        //register
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent (LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
                finish();
            }
        });
    }

}
