package com.notebook.models;

import java.io.Serializable;
import java.util.Date;

public class Note implements Serializable {
    private int note_id=-1;
    private String noteTitle, noteContent, noteTime, noteDate;

    public Note(){}
    public Note(int note_id, String noteTitle,String noteDate, String noteTime, String noteContent ) {
        this.note_id = note_id;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.noteTime = noteTime;
        this.noteDate = noteDate;
    }

    public int getNote_id() {
        return note_id;
    }

    public void setNote_id(int note_id) {
        this.note_id = note_id;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public String getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(String noteTime) {
        this.noteTime = noteTime;
    }

    public String getNoteDate() {
        return noteDate;
    }

    public void setNoteDate(String noteDate) {
        this.noteDate = noteDate;
    }
}
