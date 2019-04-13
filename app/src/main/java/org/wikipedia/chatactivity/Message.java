package org.wikipedia.chatactivity;

public class Message {
    private String text;
    private String name;
    private String photoUrl;
    private String UID;

    public Message() {
    }

    public Message(String text, String name, String photoUrl, String UID) {
        this.text = text;
        this.name = name;
        this.photoUrl = photoUrl;
        this.UID = UID;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

}
