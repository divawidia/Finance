package com.kelompok8.finance.ui.stats;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.kelompok8.finance.AddPengeluaranActivity;
import com.kelompok8.finance.CategoryActivity;
import com.kelompok8.finance.R;
import com.kelompok8.finance.adapter.PengeluaranOneLineAdapter;
import com.kelompok8.finance.adapter.TransaksiAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class StatisticActivity extends AppCompatActivity {

    private RecyclerView recyclerViewTransaksi;
    private RecyclerView recyclerViewPengeluaran;
    private ArrayList<Pengeluaran> pengeluaranHolder;
    private ArrayList<Pengeluaran> pengeluaranHolder2;
    private SQLiteDatabase sqLiteDatabase;
    private PieChart pieChart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistic);
        getSupportActionBar().hide();

        Button addBtn = (Button) findViewById(R.id.createButton);

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticActivity.this, AddPengeluaranActivity.class);
                intent.putExtra("action", "add");
                startActivity(intent);
            }
        });

        pieChart = findViewById(R.id.chart);

    }

    private void setupPieChart() {
        pieChart.setDrawHoleEnabled(false);

        pieChart.getDescription().setEnabled(false);
//        pieChart.setUsePercentValues(false);
//        pieChart.setEntryLabelTextSize(12);
//        pieChart.setEntryLabelColor(Color.BLACK);
//        pieChart.setCenterText("Spending by Category");
//        pieChart.setCenterTextSize(24);
//        pieChart.getDescription().setEnabled(false);
//
//        Legend l = pieChart.getLegend();
//        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
//        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
//        l.setOrientation(Legend.LegendOrientation.VERTICAL);
//        l.setDrawInside(false);
//        l.setEnabled(true);
    }

    private void loadPieChartData() {
        ArrayList<PieEntry> entries = new ArrayList<>();
        Cursor cursor3 = new DBHelper(this).readPengeluaranGByCategory();

        while(cursor3.moveToNext()){
            entries.add(new PieEntry(cursor3.getInt(3), cursor3.getString(1)));
        }

        ArrayList<Integer> colors = new ArrayList<>();
        for (int color: ColorTemplate.MATERIAL_COLORS) {
            colors.add(color);
        }

        for (int color: ColorTemplate.VORDIPLOM_COLORS) {
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setDrawValues(false);
        data.setValueFormatter(new PercentFormatter(pieChart));
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);

        pieChart.setData(data);
        pieChart.invalidate();

        pieChart.animateY(1400, Easing.EaseInOutQuad);
    }


    @Override
    public void onResume() {
        super.onResume();
        init();
    }

    private void init(){
        pengeluaranHolder = new ArrayList<>();
        pengeluaranHolder2 = new ArrayList<>();
        setupPieChart();
        loadPieChartData();

        DBHelper db = new DBHelper(this);

        recyclerViewTransaksi = (RecyclerView) findViewById(R.id.listTransaksi);
        recyclerViewTransaksi.setLayoutManager(new LinearLayoutManager(this));

        Cursor cursor = new DBHelper(this).readPengeluaran();

        while(cursor.moveToNext()){
            Pengeluaran pengeluaran = new Pengeluaran(cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getInt(2),
                    cursor.getInt(3),
                    cursor.getString(4),
                    cursor.getString(5),
                    cursor.getInt(6));
            pengeluaranHolder.add(pengeluaran);
        }

        TransaksiAdapter pengeluaranAdapter = new TransaksiAdapter(pengeluaranHolder, StatisticActivity.this, sqLiteDatabase);
        recyclerViewTransaksi.setAdapter((RecyclerView.Adapter) pengeluaranAdapter);


        //code spacer


        recyclerViewPengeluaran = (RecyclerView) findViewById(R.id.listPengeluaran);
        LinearLayoutManager horizontalLayoutManagaer = new LinearLayoutManager(StatisticActivity.this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewPengeluaran.setLayoutManager(horizontalLayoutManagaer);

        Cursor cursor2 = new DBHelper(this).readPengeluaranGByCategory();

        while(cursor2.moveToNext()){
            Pengeluaran pengeluaran = new Pengeluaran(cursor2.getInt(0),
                    cursor2.getString(1),
                    cursor2.getInt(2),
                    cursor2.getInt(3),
                    cursor2.getString(4),
                    cursor2.getString(5),
                    cursor2.getInt(6));
            pengeluaranHolder2.add(pengeluaran);
        }

        PengeluaranOneLineAdapter pengeluaranOneLineAdapter = new PengeluaranOneLineAdapter(pengeluaranHolder2, StatisticActivity.this, sqLiteDatabase);
        recyclerViewPengeluaran.setAdapter((RecyclerView.Adapter) pengeluaranOneLineAdapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
    }
}