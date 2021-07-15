package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.notebook.dbhandler.DbManager;
import com.notebook.models.Note;
import com.notebook.models.Response;

import java.io.Serializable;
import java.util.Calendar;

public class EditNote extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        startActivity(new Intent(EditNote.this, ViewNotes.class));
        finish();
    }
    TextView note_date_edit, note_time_edit;
    EditText note_content_edit, note_title_edit;
    Button save_btn;
    private AlertDialog datePicker, timePicker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);
        Intent data=this.getIntent();
        Note note = (Note) data.getSerializableExtra("Note");
//        Log.i("EditNote", ""+note.getNote_id());
        note_content_edit=findViewById(R.id.note_content_edit);
        note_date_edit=findViewById(R.id.note_date_edit);
        note_time_edit=findViewById(R.id.note_time_edit);
        note_title_edit=findViewById(R.id.note_title_edit);
        save_btn=findViewById(R.id.save_note_edit);

        note_content_edit.setText(note.getNoteContent());
        note_title_edit.setText(note.getNoteTitle());
        note_time_edit.setText(note.getNoteTime());
        note_date_edit.setText(note.getNoteDate());
        initializeDateTimePicker();

        note_date_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });
        note_time_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });


        save_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_date_val=note_date_edit.getText().toString().trim();
                String note_time_val=note_time_edit.getText().toString().trim();
                String note_title_val=note_title_edit.getText().toString().trim();
                String note_content_val=note_content_edit.getText().toString().trim();

                if(note_date_val.equals("") || note_time_val.equals("") || note_time_val.equals("") ||
                        note_title_val.equals("") || note_content_val.equals("")){
                    Toast.makeText(EditNote.this, "Enter valid data. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbManager db=new DbManager(EditNote.this);
                    Note note_=new Note();
                    note_.setNote_id(note.getNote_id());
                    note_.setNoteContent(note_content_val);
                    note_.setNoteDate(note_date_val);
                    note_.setNoteTime(note_time_val);
                    note_.setNoteTitle(note_title_val);

                    Response response= db.update_note(note_);
                    if(response.isSuccessful()){
                        Toast.makeText(EditNote.this , "Note updated successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(EditNote.this, Dashboard.class));
                        finish();
                    }
                    else {
                        Toast.makeText(EditNote.this, response.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
    }
    public void initializeDateTimePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener=new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month+=1;
                String date=makeDateString(dayOfMonth, month, year);
                note_date_edit.setText(date);
            }
        };

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time =makeTimeString(hourOfDay, minute);
                note_time_edit.setText(time);
            }
        };

        Calendar calendar = Calendar.getInstance();
        int year= calendar.get(Calendar.YEAR);
        int month= calendar.get(Calendar.MONTH);
        int day= calendar.get(Calendar.DAY_OF_MONTH);
        int dateStyle= AlertDialog.THEME_HOLO_LIGHT;

        int hours=calendar.get(Calendar.HOUR_OF_DAY);
        int minutes=calendar.get(Calendar.MINUTE);
        int timeStyle= R.style.Theme_AppCompat_Dialog;

        datePicker=new DatePickerDialog(this, dateStyle, dateSetListener, year, month,day);
        timePicker=new TimePickerDialog(this, timeStyle, timeSetListener, hours, minutes, false);
    }
    public String makeDateString(int d, int m, int y){
        return d+" / "+m+" / "+y;
    }
    public String makeTimeString(int hour, int minute){
        String hr="", min="", time="";
        if(hour<10){
            hr="0"+hour;
        }
        else{
            hr=""+hour;
        }
        if(minute<10){
            min="0"+minute;
        }else{
            min=""+minute;
        }
        time=hr+" : "+min;
        return time;
    }
}