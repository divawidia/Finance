package com.kelompok8.finance;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.kelompok8.finance.database.DBHelper;
import com.kelompok8.finance.model.User;

import java.util.List;

public class LoginManager {

    public void login(Integer user_id, String username, String password, Context context ){
        SharedPreferences sharedPreferences = context.getSharedPreferences("login_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt("key_id", user_id);
        editor.putString("key_username", username);
        editor.putString("key_password", password);
        editor.putBoolean("isLogin", true);
        editor.apply();
    }

    public void logout(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences("login_session", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.clear();
        editor.apply();
    }

    public boolean isLogin(Context context){
        return context.getSharedPreferences("login_session", 0).getBoolean("isLogin", false);
//        return sharedPreferences.getBoolean("isLogin", false);
    }
}