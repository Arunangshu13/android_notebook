package com.notebook.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.notebook.models.User;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME="session";
    String SESSION_KEY="session_user";
    public SessionManagement(Context context){
        sharedPreferences=context.getSharedPreferences(SHARED_PREF_NAME, context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
    }
    public void saveSession(User user){
        int uid=user.getUid();
        editor.putInt(SESSION_KEY, uid).commit();
        editor.putString("user_name", user.getUname()).commit();
        editor.putString("email", user.getEmail()).commit();
    }
    public int getSession(){
        return sharedPreferences.getInt(SESSION_KEY, -1 );
    }
    public User getUserFromSession(){
        User user=new User();
        user.setUid(sharedPreferences.getInt(SESSION_KEY,-1));

        user.setEmail(sharedPreferences.getString("email","null"));
        user.setUname(sharedPreferences.getString("user_name", "null"));
        return user;
    }
    public void removeSession(){
        editor.putInt(SESSION_KEY, -1).commit();
        editor.putString("user_name", "").commit();
        editor.putString("email", "").commit();

    }
}
