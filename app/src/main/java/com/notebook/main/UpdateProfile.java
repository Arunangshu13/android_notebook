package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class UpdateProfile extends AppCompatActivity {

    private EditText fname, pass, rpass, email, lname;
    private Button update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        fname =findViewById(R.id.fname_update);
        lname =findViewById(R.id.lname_update);
        email =findViewById(R.id.email_update);
        pass =findViewById(R.id.upass_update);
        rpass =findViewById(R.id.rpass_update);



        DbManager dbManager=new DbManager(UpdateProfile.this);
        SessionManagement sessionManagement=new SessionManagement(this);
        User user=sessionManagement.getUserFromSession();

        fname.setText(user.getUname().split(" ")[0]);
        lname.setText(user.getUname().split(" ")[1]);
        email.setText(user.getEmail());

        update=findViewById(R.id.update_profile);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String fname_str=fname.getText().toString().trim();
                String lname_str=lname.getText().toString().trim();
                String name_str=fname_str+" "+lname_str;
                String email_str=email.getText().toString().trim();
                String pass_str=pass.getText().toString().trim();
                String rpass_str=rpass.getText().toString().trim();
                boolean flag=true;
                if(fname_str.equals("") || lname_str.equals("") || email_str.equals("") || pass_str.equals("") || rpass.equals(""))
                {
                    flag=false;
                    Toast.makeText(UpdateProfile.this, "Empty Fields", Toast.LENGTH_SHORT).show();
                }
                if(!rpass_str.equals(pass_str)){
                    flag=false;
                    Toast.makeText(UpdateProfile.this,  "Passwords does not match", Toast.LENGTH_SHORT).show();
                }
                if(flag){
                    User user_update=new User();
                    user_update.setUid(user.getUid());
                    Log.i("setting uid", ""+user.getUid());
                    user_update.setPassword(pass_str);
                    user_update.setEmail(email_str);
                    user_update.setUname(name_str);
                    Response response=dbManager.update_user(user_update);
                    if(response.isSuccessful()){
                        sessionManagement.removeSession();
                        Toast.makeText(UpdateProfile.this, "Profile Updated", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateProfile.this, MainActivity.class));
                        finish();

                    }else{
                        Toast.makeText(UpdateProfile.this, "Some problem", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });


    }
}