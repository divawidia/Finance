package com.kelompok8.finance;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kelompok8.finance.adapter.CategoryAdapter;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.helper.IconPickerHelper;
import com.kelompok8.finance.model.Category;
import com.maltaisn.icondialog.IconDialog;
import com.maltaisn.icondialog.IconDialogSettings;
import com.maltaisn.icondialog.pack.IconPack;
import com.thebluealliance.spectrum.SpectrumPalette;

import java.util.ArrayList;
import java.util.List;

public class AddCategoryActivity extends AppCompatActivity implements IconDialog.Callback {
    private Category category;
    private DBHelper db;
    private static final String ICON_DIALOG_TAG = "icon-dialog";
    private String colorString;
    private RecyclerView recyclerViewKategori;
    private ArrayList<Category> kategoriHolder = new ArrayList<>();
    private SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_category);

        IconDialog dialog = (IconDialog) getSupportFragmentManager().findFragmentByTag(ICON_DIALOG_TAG);
        IconDialog iconDialog = dialog != null ? dialog
                : IconDialog.newInstance(new IconDialogSettings.Builder().build());

        Button btn = findViewById(R.id.selectIcon);
        btn.setOnClickListener(v -> {
            // Open icon dialog
            iconDialog.show(getSupportFragmentManager(), ICON_DIALOG_TAG);
        });

        SpectrumPalette spectrumPalette = (SpectrumPalette) findViewById(R.id.colorPicker);
        spectrumPalette.setOnColorSelectedListener(new SpectrumPalette.OnColorSelectedListener() {
            @Override
            public void onColorSelected(@ColorInt int color) {
                colorString = "#" + Integer.toHexString(color).toUpperCase();
            }
        });

        DBHelper db = new DBHelper(this);

        recyclerViewKategori = (RecyclerView) findViewById(R.id.listCategory);
        recyclerViewKategori.setLayoutManager(new GridLayoutManager(this, 4));

        Cursor cursor = new DBHelper(this).readKategori();

        while(cursor.moveToNext()){
            Category category = new Category(cursor.getInt(0),
                    cursor.getInt(1),
                    cursor.getString(2),
                    cursor.getString(3),
                    cursor.getString(4));
            kategoriHolder.add(category);
        }

        CategoryAdapter categoryAdapter = new CategoryAdapter(kategoriHolder, AddCategoryActivity.this, sqLiteDatabase);
        recyclerViewKategori.setAdapter((RecyclerView.Adapter) categoryAdapter);


        EditText namaCategory = findViewById(R.id.categoryInput);
        Button btnSubmit = (Button) findViewById(R.id.button_update);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = new Category(0, 1,
                        namaCategory.getText().toString(),
                        "",
                        colorString);

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

    @Nullable
    @Override
    public IconPack getIconDialogIconPack() {
        return ((IconPickerHelper) getApplication()).getIconPack();
    }

    @Override
    public void onIconDialogCancelled() {}

    @Override
    public void onIconDialogIconsSelected(@NonNull IconDialog iconDialog, @NonNull List<com.maltaisn.icondialog.data.Icon> list) {
        // Show a toast with the list of selected icon IDs.
        StringBuilder sb = new StringBuilder();
        for (com.maltaisn.icondialog.data.Icon icon : list) {
            sb.append(icon.getId());
            sb.append(", ");
        }
        sb.delete(sb.length() - 2, sb.length());
        Toast.makeText(this, "Icons selected: " + sb, Toast.LENGTH_SHORT).show();
    }

}
