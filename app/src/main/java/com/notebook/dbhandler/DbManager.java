package com.notebook.dbhandler;
import android.content.ContentValues;
import android.content.Context;
import android.content.ContextWrapper;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.notebook.models.Note;
import com.notebook.models.Response;
import com.notebook.models.User;

public class DbManager extends SQLiteOpenHelper {


    private static final String DB_NAME="notebook";
    private static final String USERS="CREATE TABLE users ( u_id integer primary key autoincrement," +
            "name text not null," +
            "password text not null, " +
            "email text not null)";
    private static final String NOTES="CREATE TABLE notes ( n_id integer primary key autoincrement," +
            "note_title text not null," +
            "note_date text not null," +
            "note_time text not null," +
            "note_content text not null)";
    public DbManager(@Nullable Context context) {
        super(context, DB_NAME, null, 1);
    }

    public Response create_user(String name, String password, String email){
        Response response=new Response();
        try{
            SQLiteDatabase db= this.getWritableDatabase();
            ContentValues contentVals=new ContentValues();
            contentVals.put("email", email);
            contentVals.put("password", password);
            contentVals.put("name", name);
            long r=db.insert("users", null, contentVals);
            if(r!=-1){
                response.setSuccessful(true);
            }else{
                response.setSuccessful(false);
                response.setMessage("Some problem occured ");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return response;
    }
    public Cursor get_user(int uid){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * from users WHERE u_id = ? ", new String[]{String.valueOf(uid)});
        return cursor;
    }
    public Response update_user(User user){
        Response response=new Response();
        String fname=user.getUname().split(" ")[0];
        String lname=user.getUname().split(" ")[1];
        String email=user.getEmail();
        String pass=user.getPassword();
        Log.i("testpass", pass);
        Log.i("testid", ""+user.getUid());
        ContentValues contentValues=new ContentValues();
        contentValues.put("name",fname+" "+lname);
        contentValues.put("email",email);
        contentValues.put("password",pass);
        SQLiteDatabase db=this.getWritableDatabase();
        long r=db.update("users", contentValues, "u_id=?", new String[]{String.valueOf(user.getUid())});
        if(r!=-1){
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
        }
        return response;
    }
    public Response create_note(Note note){

        Response response=new Response();
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();

        contentValues.put("note_content", note.getNoteContent());
        contentValues.put("note_date", note.getNoteDate());
        contentValues.put("note_time", note.getNoteTime());
        contentValues.put("note_title", note.getNoteTitle());
        long res=db.insert("notes", null, contentValues);
        if(res==-1){
            response.setSuccessful(false);
            response.setMessage("Some problem ");
        }else
        {
            response.setSuccessful(true);
        }

        return  response;
    }
    public Response update_note(Note note){
        Response response=new Response();
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("note_content", note.getNoteContent());
        contentValues.put("note_title", note.getNoteTitle());
        contentValues.put("note_date", note.getNoteDate());
        contentValues.put("note_time", note.getNoteTime());

        long r=db.update("notes", contentValues, "n_id=?", new String[]{String.valueOf(note.getNote_id())});
        if(r==-1){
            response.setSuccessful(false);
        }
        else{
            response.setSuccessful(true);
        }
        return  response;
    }
    public Response delete_note(int note_id){
        Response response=new Response();
        SQLiteDatabase db=this.getWritableDatabase();
        long r=db.delete("notes", "n_id=?", new String[]{String.valueOf(note_id)});
        if(r==-1){
            response.setSuccessful(false);
        }else{
            response.setSuccessful(true);
        }
        return  response;
    }
    public Response update_password(String old_password, int secret_number){
        Response response=new Response();
        return  response;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(USERS);
        db.execSQL(NOTES);
        Log.i("test", "Database and tables created successfully .");

    }

    public Cursor getNotes(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor cursor=db.rawQuery("SELECT * FROM notes", null);
        return cursor;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists users");
        db.execSQL("drop table if exists notes");
        onCreate(db);
    }
}
