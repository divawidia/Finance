package com.kelompok8.finance.ui.profile;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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

import com.kelompok8.finance.LoginManager;
import com.kelompok8.finance.R;
import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;

public class EditPasswordActivity extends AppCompatActivity {

    private User user;
    private DBHelper dbHelper;
    int idUser;

    String passLama, passBaru, passKonfirm;

    LoginManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);

        EditText editPassLama = findViewById(R.id.editTextPasswordlama);
        EditText editPassBaru = findViewById(R.id.editTextPasswordbaru);
        EditText editPassKonfirm = findViewById(R.id.editTextKonfirmasipassword);

        Button simpan = findViewById(R.id.btnSimpanPassword);

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
                    cursor.getString(5),
                    cursor.getString(6)
            );
        }catch (Exception e){
            Log.e("error user", "Error:" + e.getMessage());
            return;
        }

        simpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                passLama = editPassLama.getText().toString();
                passBaru = editPassBaru.getText().toString();
                passKonfirm = editPassKonfirm.getText().toString();

                if (TextUtils.isEmpty(passLama)){
                    editPassLama.requestFocus();
                    editPassLama.setError("Password lama harus diisi!");
                }else if (TextUtils.isEmpty(passBaru)){
                    editPassBaru.requestFocus();
                    editPassBaru.setError("Email harus diisi!");
                }else if (TextUtils.isEmpty(passKonfirm)){
                    editPassKonfirm.requestFocus();
                    editPassKonfirm.setError("Tanggal lahir harus diisi!");
                }else if (!TextUtils.equals(passBaru, passKonfirm)){
                    editPassKonfirm.requestFocus();
                    editPassKonfirm.setError("Konfirmasi password harus sama dengan password baru");
                }else {

                    User currentUser = dbHelper.findUser(user.getIdUser());
                    User check = dbHelper.checkPasswordLama(idUser, passLama);

                    if (check != null){
                        ContentValues values =new ContentValues();
                        values.put("password", passBaru);
                        dbHelper.updateUser(values, idUser);

                        Intent intent = new Intent(EditPasswordActivity.this, ProfileActivity.class);
                        Toast.makeText(EditPasswordActivity.this, "Password berhasil diubah", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    }
                    else {
                        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                        builder.setTitle("Error");
                        builder.setMessage("Password lama tidak sesuai");
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