package com.kelompok8.finance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.login.LoginActivity;
import com.kelompok8.finance.ui.stats.StatisticActivity;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kelompok8.finance.database.DBHelper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splashscreen);
        getSupportActionBar().hide();

        LoginManager session = new LoginManager();

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (session.isLogin(MainActivity.this)){
                    startActivity(new Intent(MainActivity.this, HomeActivity.class));
                }else{
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                }
                finish();
            }
        }, 3000);
    }
}