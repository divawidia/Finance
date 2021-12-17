package com.kelompok8.finance.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.login.LoginActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        TextView editProfile = findViewById(R.id.textProfileSetting);
        TextView editPass = findViewById(R.id.textUbahPassword);
        TextView txvLogout = findViewById(R.id.textKeluar);

        DBHelper db = new DBHelper(this);
        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditProfileActivity.class));
            }
        });
        editPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ProfileActivity.this, EditPasswordActivity.class));
            }
        });
        txvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.Logout();
                startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
            }
        });
    }
}
