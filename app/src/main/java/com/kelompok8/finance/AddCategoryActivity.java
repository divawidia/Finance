package com.kelompok8.finance;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Category;
import com.kelompok8.finance.model.Pengeluaran;
import com.kelompok8.finance.ui.stats.StatisticActivity;

public class AddCategoryActivity extends AppCompatActivity {
    private Category category;
    private DBHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        EditText namaCategory = findViewById(R.id.categoryInput);
        Button btnSubmit = (Button) findViewById(R.id.button_update);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = new Category(0, 1,
                        namaCategory.getText().toString(),
                        "",
                        "");

                saveDataToDB();

                Intent intent = new Intent(AddCategoryActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

    }

    private void saveDataToDB(){
        db = new DBHelper(this);
        db.insertKategori(category.getIdUser(),
                category.getNamaKategori(),
                category.getIcon(),
                category.getWarna());
    }
}
