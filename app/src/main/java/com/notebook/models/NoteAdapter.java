package com.notebook.models;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.notebook.main.R;
import com.notebook.models.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note>{
    private Context mcontext;
    private int mresource;
    private ArrayList<Note> noteArrayList;
    public NoteAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Note> noteArrayList) {
        super(context, resource, noteArrayList);
        this.mcontext=context;
        this.mresource=resource;
        this.noteArrayList=noteArrayList;
    }
//    public void update(ArrayList<Note> dynamicNotes){
//        noteArrayList=new ArrayList<>();
//        noteArrayList.addAll(dynamicNotes);
//        notifyDataSetChanged();
//    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        LayoutInflater layoutInflater=LayoutInflater.from(mcontext);
        convertView= layoutInflater.inflate(mresource, parent, false);
        if(convertView==null){

        }
        TextView note_title_tv, note_date_tv, note_time_tv, note_content_tv;
        note_title_tv=convertView.findViewById(R.id.note_title_tv);
        note_date_tv=convertView.findViewById(R.id.note_date_tv);
        note_time_tv=convertView.findViewById(R.id.note_time_tv);
        note_content_tv=convertView.findViewById(R.id.note_content_tv);

        note_title_tv.setText(getItem(position).getNoteTitle());
        note_date_tv.setText(getItem(position).getNoteDate());
        note_time_tv.setText(getItem(position).getNoteTime());
        note_content_tv.setText(getItem(position).getNoteContent());
        return convertView;

//        return super.getView(position, convertView, parent);
    }
}
