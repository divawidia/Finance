package com.kelompok8.finance.ui.wallets;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.MainActivity;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.Dompet;
import com.kelompok8.finance.model.User;
import com.kelompok8.finance.ui.home.HomeActivity;
import com.kelompok8.finance.ui.profile.EditPasswordActivity;
import com.kelompok8.finance.ui.profile.ProfileActivity;

public class AddDompetActivity extends AppCompatActivity {

    private User user;
    private DBHelper dbHelper;
    int idUser;

    String namaDompet, jumlahSaldo;

    LoginManager session;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dompet);

        EditText editNamaDompet = findViewById(R.id.descInput);
        EditText editJumlahSaldo = findViewById(R.id.jumlahInput);

        Button buttonAdd = findViewById(R.id.buttonAddWallet);

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

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                namaDompet = editNamaDompet.getText().toString();
                jumlahSaldo = editJumlahSaldo.getText().toString();

                if (TextUtils.isEmpty(namaDompet)){
                    editNamaDompet.requestFocus();
                    editNamaDompet.setError("Nama dompet harus diisi!");
                }else if (TextUtils.isEmpty(jumlahSaldo)){
                    editJumlahSaldo.requestFocus();
                    editJumlahSaldo.setError("Jumlah dompet  harus diisi!");
                }else {

                User currentUser = dbHelper.findUser(user.getIdUser());
                Dompet check = dbHelper.checkNamaDompet(idUser, namaDompet);

                if (check == null){
                    ContentValues values =new ContentValues();
                    values.put("id_user", idUser);
                    values.put("nama_dompet", namaDompet);
                    values.put("saldo_awal", jumlahSaldo);
                    dbHelper.addDompet(values);

                    Toast sukses = Toast.makeText(getApplicationContext(), "Berhasil menambahkan dompet", Toast.LENGTH_SHORT);
                    sukses.show();

                    Intent intent = new Intent(AddDompetActivity.this, HomeActivity.class);
                    startActivity(intent);
                }
                else {
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setTitle("Error");
                    builder.setMessage("Nama dompet sudah ada");
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });
                    builder.show();
                }
            }
            }
        });

    }
}
