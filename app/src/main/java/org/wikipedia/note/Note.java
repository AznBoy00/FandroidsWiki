package org.wikipedia.note;

import android.support.annotation.Nullable;

public class Note {

    private int noteId;
    private int authorId;
    private int noteBookId;
    private String noteTitle;
    private String noteContent;
    private String createdTime;
    private String lastModifiedTime;

    public Note(int noteId, int authorId, int noteBookId, String noteTitle, String noteContent, String createdTime, String lastModifiedTime) {
        this.noteId = noteId;
        this.authorId = authorId;
        this.noteBookId = noteBookId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
    }

    // TODO Delete this constructor once you add authorId, noteBookId, lastModifiedTime
    public Note(int noteId, String noteTitle, String noteContent, String createdTime) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public int getNoteId() {
        return noteId;
    }

    public void setNoteId(int noteId) {
        this.noteId = noteId;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public int getNoteBookId() {
        return noteBookId;
    }

    public void setNoteBookId(int noteBookId) {
        this.noteBookId = noteBookId;
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


    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}