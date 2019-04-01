package org.wikipedia.note.Model;

public class Note {

    private String noteId;
    private String noteTitle;
    private String noteContent;
    private String createdTime;

    public Note(){

    }

    public Note(String noteTitle, String noteContent, String createdTime, String noteId) {
        this.noteTitle = noteTitle;
        this.noteContent = noteContent;
        this.createdTime = createdTime;
        this.noteId = noteId;
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

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }
}
