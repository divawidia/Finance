package com.kelompok8.finance.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import com.kelompok8.finance.R;
import com.kelompok8.finance.ui.stats.StatisticActivity;

public class HomeActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().hide();

        TextView pengeluaranShow = (TextView) findViewById(R.id.pengeluaranShowAll);
        TextView transaksiShow = (TextView) findViewById(R.id.transaksiShowAll);

        pengeluaranShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, StatisticActivity.class));
            }
        });
        transaksiShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, StatisticActivity.class));
            }
        });
    }
}