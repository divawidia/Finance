package com.kelompok8.finance.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.Group;

import com.kelompok8.finance.R;
import com.kelompok8.finance.ui.home.HomeActivity;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Group editProfile = findViewById(R.id.groupEditProfile);
        Group editPass = findViewById(R.id.groupEditPass);

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
    }
}
