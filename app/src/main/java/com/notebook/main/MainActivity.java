package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notebook.dbhandler.DbManager;
import com.notebook.models.Response;
import com.notebook.models.User;

public class MainActivity extends AppCompatActivity {

    private Button login_btn;
    private EditText username, password;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
        int isUser=sessionManagement.getSession();
        if(isUser!=-1){
            startActivity(new Intent(this, Dashboard.class));
        }
    }
    SQLiteDatabase sqdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DbManager db=new DbManager(this);
        sqdb=db.getReadableDatabase();
        Cursor cursor=db.get_user(1);
        Log.i("test 111", ""+cursor.getCount());

        if(cursor.getCount()<=0){
            startActivity(new Intent(this, OneTimeSignup.class));
            finish();
        }else{
            cursor.moveToFirst();
        }


        login_btn=findViewById(R.id.login_btn);
        username=findViewById(R.id.uname);
        password=findViewById(R.id.upass);

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uname=username.getText().toString();
                String upass=password.getText().toString();
                if(uname.equals("") || upass.equals("")){

                    Toast.makeText(MainActivity.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
                else {

                    String _email=cursor.getString(3); //email
                    String _pass= cursor.getString(2); //password
                    String _name= cursor.getString(1);//name
                    if(uname.equals(_email) && upass.equals(_pass)){
                        User user=new User();
                        user.setUname(_name);
                        user.setEmail(_email);

                        user.setUid(cursor.getInt(0));
                        SessionManagement sessionManagement=new SessionManagement(MainActivity.this);
                        sessionManagement.saveSession(user);
                        Intent intent=new Intent(MainActivity.this, Dashboard.class);
                        startActivity(intent);
                        Toast.makeText(MainActivity.this, "Redirecting to dashboard ", Toast.LENGTH_SHORT).show();
                        finish();
                    }else{
                        Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });


    }
}