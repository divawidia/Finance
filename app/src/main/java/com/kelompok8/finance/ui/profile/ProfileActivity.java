package com.kelompok8.finance.ui.profile;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {
    private User user;
    private DBHelper dbHelper;
    int idUser;

    LoginManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView editProfile = findViewById(R.id.textProfileSetting);
        TextView editPass = findViewById(R.id.textUbahPassword);
        TextView txvLogout = findViewById(R.id.textKeluar);
        TextView username = findViewById(R.id.textUsername);
        TextView email = findViewById(R.id.textEmail);

        idUser = this.getSharedPreferences("login_session", 0).getInt("key_id", 0);
        dbHelper = new DBHelper(this);

        try {
            Cursor cursor = (Cursor) dbHelper.getUserLogin(idUser);
            cursor.moveToLast();
            user =new User(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4),
                    cursor.getString(5)
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

        username.setText(user.getUsername());
        email.setText(user.getEmail());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ProfileActivity.this, EditProfileActivity.class);
                startActivity(intent1);
            }
        });
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(ProfileActivity.this, EditPasswordActivity.class);
                startActivity(intent2);
            }
        });

        txvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog dialog = new AlertDialog.Builder(ProfileActivity.this).setTitle("Konfirmasi Logout")
                        .setMessage("Yakin ingin keluar?")
                        .setPositiveButton("Lanjutkan", null)
                        .setNegativeButton("Kembali", null)
                        .show();
                Button btn_kembali = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                btn_kembali.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                Button btn_lanjutkan = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                btn_lanjutkan.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        session = new LoginManager();
                        session.logout(ProfileActivity.this);
                        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(ProfileActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
