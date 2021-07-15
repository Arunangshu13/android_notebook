package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
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

import java.sql.Time;
import java.util.Calendar;

public class CreateNote extends AppCompatActivity {
    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, Dashboard.class));
    }
    private EditText note_title, note_content;
    private Button save_note;
    private AlertDialog datePicker, timePicker;
    private TextView note_date, note_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        note_title =findViewById(R.id.note_title);
        note_content=findViewById(R.id.note_content);
        note_date =findViewById(R.id.note_date);
        note_time =findViewById(R.id.note_time);
        save_note=findViewById(R.id.save_note);
        String invalidDate=note_date.getText().toString();
        initializeDateTimePicker();

        note_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show();
            }
        });
        note_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timePicker.show();
            }
        });

        save_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String note_date_val=note_date.getText().toString().trim();
                String note_time_val=note_time.getText().toString().trim();
                String note_title_val=note_title.getText().toString().trim();
                String note_content_val=note_content.getText().toString().trim();

                if(note_date_val.equals(invalidDate) || note_time_val.equals("") || note_time_val.equals("") ||
                        note_title_val.equals("") || note_content_val.equals("")){
                    Toast.makeText(CreateNote.this, "Enter valid data. ", Toast.LENGTH_SHORT).show();
                }
                else{
                    DbManager db=new DbManager(CreateNote.this);
                    Note note=new Note();

                    note.setNoteContent(note_content_val);
                    note.setNoteDate(note_date_val);
                    note.setNoteTime(note_time_val);
                    note.setNoteTitle(note_title_val);

                    Response response= db.create_note(note);
                    if(response.isSuccessful()){
                        Toast.makeText(CreateNote.this , "Note created Successfully", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(CreateNote.this, response.getMessage(), Toast.LENGTH_SHORT).show();
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
                note_date.setText(date);
            }
        };

        TimePickerDialog.OnTimeSetListener timeSetListener=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                String time =makeTimeString(hourOfDay, minute);
                note_time.setText(time);
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