package com.notebook.main;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.notebook.dbhandler.DbManager;
import com.notebook.models.Note;
import com.notebook.models.Response;

public class NoteManager extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, ViewNotes.class));
        finish();
    }

    TextView note_title_mn, note_date_mn, note_time_mn, note_content_mn;
    Button edit_btn, delete_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_manager);
        Intent intent= this.getIntent();
        
        Note note= (Note) intent.getSerializableExtra("Note");
        if(note==null){
            Toast.makeText(this, "Some issue. No note received", Toast.LENGTH_SHORT).show();
        }
        note_content_mn=findViewById(R.id.note_content_mn);
        note_title_mn=findViewById(R.id.note_title_mn);
        note_date_mn=findViewById(R.id.note_date_mn);
        note_time_mn=findViewById(R.id.note_time_mn);

        edit_btn=findViewById(R.id.edit_btn);
        delete_btn=findViewById(R.id.delete_btn);

        String content="", title="", date="", time="";

        content=note.getNoteContent();
        title=note.getNoteTitle();
        time=note.getNoteTime();
        date=note.getNoteDate();

        note_content_mn.setText(content);
        note_title_mn.setText(title);
        note_time_mn.setText(time);
        note_date_mn.setText(date);

        AlertDialog.Builder confirm =new AlertDialog.Builder(this);



        edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_=new Intent(NoteManager.this, EditNote.class);
                intent_.putExtra("Note", note);
                startActivity(intent_);
            }
        });

        delete_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirm.setTitle("Confirm Delete")
                        .setMessage("Are you sure you wnat to delete this note ? ")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                DbManager dbManager=new DbManager(NoteManager.this);
//                                SQLiteDatabase db=dbManage.getWritableDatabase();
                                Response response= dbManager.delete_note(note.getNote_id());
                                if(response.isSuccessful()){
                                    Toast.makeText(NoteManager.this, "Note deleted ", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(NoteManager.this, ViewNotes.class));
                                    finish();
                                }
                                else{
                                    Toast.makeText(NoteManager.this, "Problem", Toast.LENGTH_SHORT).show();
                                }


                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Toast.makeText(NoteManager.this, "Delete Canceled", Toast.LENGTH_SHORT).show();
                            }
                        }).show();
            }
        });

    }
}