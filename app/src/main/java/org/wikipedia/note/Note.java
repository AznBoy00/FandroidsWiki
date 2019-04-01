package org.wikipedia.note;

import android.support.annotation.Nullable;

public class Note {

    private String noteId;
    private String userId;
    private String noteBookId;
    private String noteTitle;
    private String noteContent;
    private String createdTime;
    private String lastModifiedTime;

    public Note(){}

    public Note(String noteId, String userId, String noteBookId, String noteTitle, String noteContent, String createdTime, String lastModifiedTime) {
        this.noteId = noteId;
        this.userId = userId;
        this.noteBookId = noteBookId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
    }

    // TODO Delete this constructor once you add userId, noteBookId, lastModifiedTime
    public Note(String noteId, String noteTitle, String noteContent, String createdTime) {
        this.noteId = noteId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNoteBookId() {
        return noteBookId;
    }

    public void setNoteBookId(String noteBookId) {
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