package org.wikipedia.note;

public class Note {

    private String noteId;
    private String userId;
    private String noteBookId;
    private String noteTitle;
    private String noteContent;
    private String createdTime;
    private String lastModifiedTime;

    public Note() {
        this.noteId = "";
        this.userId = "";
        this.noteBookId = "";
        this.noteTitle = "";
        this.noteContent = "";
        this.createdTime = "";
        this.lastModifiedTime = "";
    }

    public Note(String noteId, String userId, String noteBookId, String noteTitle, String noteContent, String createdTime, String lastModifiedTime) {
        this.noteId = noteId;
        this.userId = userId;
        this.noteBookId = noteBookId;
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
        this.lastModifiedTime = lastModifiedTime;
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