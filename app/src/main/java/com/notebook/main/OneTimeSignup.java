package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.notebook.dbhandler.DbManager;
import com.notebook.models.Response;

public class OneTimeSignup extends AppCompatActivity {


    private Button signup;
    private EditText fname, lname, uname, upass, rpass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_time_signup);
        signup=findViewById(R.id.signup);

        fname=findViewById(R.id.fname);
        lname=findViewById(R.id.lname);
        uname=findViewById(R.id.uname);
        upass=findViewById(R.id.upass);
        rpass=findViewById(R.id.rpass);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str_email, str_lname, str_fname, str_rpass, str_upass;
                str_email=uname.getText().toString().trim();
                str_lname=lname.getText().toString().trim();
                str_fname=fname.getText().toString().trim();
                str_upass=upass.getText().toString().trim();
                str_rpass=rpass.getText().toString().trim();

                if(str_email.equals("") || str_lname.equals("") || str_fname.equals("") || str_upass.equals("") ||
                        str_rpass.equals("") ){
                    Toast.makeText(OneTimeSignup.this, "Empty fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    String str_name=str_fname+" "+str_lname;
                    DbManager db=new DbManager(OneTimeSignup.this);
                    Response response=db.create_user(str_name, str_upass, str_email);
                    if(response.isSuccessful()){
                        Toast.makeText(OneTimeSignup.this, "Your account has been created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(OneTimeSignup.this, MainActivity.class ));
                        finish();
                    }else{
                        Toast.makeText(OneTimeSignup.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }


            }
        });





    }
}