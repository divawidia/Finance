package com.kelompok8.finance.ui.login;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;

public class LoginActivity extends AppCompatActivity {
    DBHelper db;
    Button buttonlogin;
    TextView register;
    EditText username, password;

    User user;

    SharedPreferences sharedPreferences;

    LoginManager session = new LoginManager();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        db = new DBHelper(this);

        username = findViewById(R.id.edtusername);
        password = findViewById(R.id.edtpassword);
        buttonlogin = findViewById(R.id.btnlogin);
        register = findViewById(R.id.txvregister);

        if (session.isLogin(LoginActivity.this)){
            startActivity(new Intent(LoginActivity.this, HomeActivity.class));
        }

//        if(db.checkSession("ada")){
//            Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
//            startActivity(mainIntent);
//        }

        //login
        buttonlogin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                String strUsername = username.getText().toString();
                String strPassword = password.getText().toString();

                try {
                    Cursor cursor = db.login(strUsername, strPassword);
                    cursor.moveToLast();
                    user=new User(
                            cursor.getInt(0),
                            cursor.getString(1),
                            cursor.getString(2),
                            cursor.getString(3),
                            cursor.getString(4),
                            cursor.getString(5)
                    );
                }catch (Exception e){
                    Toast.makeText(getApplicationContext(), "Nama Depan atau Password salah", Toast.LENGTH_SHORT).show();
                    Log.e(TAG, "Error:" + e.getMessage());
                    return;
                }

                session.login(user.getIdUser(), user.getUsername(),user.getPassword(), LoginActivity.this);
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                startActivity(intent);

//                if (user == null){
////                    Boolean updateSession = db.upgradeSession("ada", 1,  strUsername, strPassword);
////                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
////                    Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
////                    startActivity(mainIntent);
//                    Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
//                }
//                else {
////                    Toast.makeText(getApplicationContext(), "Login gagal", Toast.LENGTH_SHORT).show();
//                    Boolean updateSession = db.upgradeSession("ada", 1,  strUsername, strPassword);
//                    Toast.makeText(getApplicationContext(), "Login berhasil", Toast.LENGTH_SHORT).show();
//                    Intent mainIntent = new Intent(LoginActivity.this, HomeActivity.class);
//                    mainIntent.putExtra("user", user);
//                    startActivity(mainIntent);
//                }
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
