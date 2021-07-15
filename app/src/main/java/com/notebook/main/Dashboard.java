package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.notebook.models.User;

public class Dashboard extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, MainActivity.class));
    }


    private Button create_note, show_notes, logout, update;
    private TextView name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        create_note=findViewById(R.id.create_btn);
        show_notes=findViewById(R.id.show_notes);
        logout=findViewById(R.id.leave_btn);
        name =findViewById(R.id.uname_text);
        update=findViewById(R.id.profile_btn);
        SessionManagement sessionManagement=new SessionManagement(Dashboard.this);
        User user=sessionManagement.getUserFromSession();

        name.setText(user.getUname());
        create_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,CreateNote.class));
                finish();
            }
        });
        show_notes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this,ViewNotes.class));
                finish();
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SessionManagement sessionManagement=new SessionManagement(Dashboard.this);
                sessionManagement.removeSession();
                startActivity(new Intent(Dashboard.this, MainActivity.class));
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Dashboard.this, UpdateProfile.class));
                finish();
            }
        });
    }
}