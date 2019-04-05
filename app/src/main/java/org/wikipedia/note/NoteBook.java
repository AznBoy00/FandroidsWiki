package org.wikipedia.note;

import android.support.annotation.Nullable;

import java.util.ArrayList;

public class NoteBook {

    private String noteBookId;
    private ArrayList <String> noteBook;


    public NoteBook() {
        this.noteBookId = "";
        this.noteBook = null;
    }


    public NoteBook(String noteBookId, ArrayList<String> noteBook) {
        this.noteBookId = noteBookId;
        this.noteBook= noteBook;

    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public String getNoteBookId() {
        return noteBookId;
    }

    public void setNoteBookId(String noteBookId) {
        this.noteBookId = noteBookId;
    }

    public ArrayList <String> getNoteBook() {
        return noteBook;
    }

    public void setNoteBook(ArrayList <String> noteBook) {
        this.noteBook = noteBook;
    }

}
