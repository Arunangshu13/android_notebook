package com.notebook.main;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.database.Cursor;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.notebook.dbhandler.DbManager;

import com.notebook.models.Note;
import com.notebook.models.NoteAdapter;

import java.io.Serializable;
import java.util.ArrayList;

public class ViewNotes extends AppCompatActivity {

    @Override
    public void onBackPressed() {
        finish();
        startActivity(new Intent(this, Dashboard.class));
    }
    private ListView listView;
    private SearchView searchView;
    private NoteAdapter noteAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_notes);

        listView=findViewById(R.id.note_list);
        searchView=findViewById(R.id.searchView);
        ArrayList<Note> noteArrayList=new ArrayList();

        DbManager dbManager=new DbManager(this);

        Cursor notes=dbManager.getNotes();
        if(notes.getCount()==0){
            Toast.makeText(this, "No Data present in the databse ", Toast.LENGTH_SHORT).show();
        }
        else {
           while(notes.moveToNext()){

               noteArrayList.add(new Note(
                        notes.getInt(0),
                        notes.getString(1),
                        notes.getString(2),
                        notes.getString(3),
                        notes.getString(4)));
           }

        }


        noteAdapter=new NoteAdapter(ViewNotes.this, R.layout.note_item, noteArrayList);
        listView.setAdapter(noteAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ViewNotes.this, NoteManager.class);
                intent.putExtra("Note", (Serializable) noteArrayList.get(position));
//                Log.i("test", ""+noteArrayList.get(position).getNote_id());
                startActivity(intent);
                finish();
                Log.i("test", "Directing to note manager ");
            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ArrayList<Note> dynamicNotes=new ArrayList<>();
                for(Note x:noteArrayList){
                    if(x.getNoteTitle().contains(newText)|| x.getNoteContent().contains(newText)){
                        dynamicNotes.add(x);
                    }
                }

                noteAdapter=new NoteAdapter(ViewNotes.this, R.layout.note_item, dynamicNotes);
                listView.setAdapter(noteAdapter);

//                ((NoteAdapter) listView.getAdapter()).update(dynamicNotes);
                return false;

            }
        });

    }

}