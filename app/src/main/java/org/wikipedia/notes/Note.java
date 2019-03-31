package org.wikipedia.notes;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import org.wikipedia.R;
import java.io.Serializable;

public class Note implements Serializable {

    //region Member attributes
    private String name;
    private String text;
    private String pushId;
    //endregion -- end --

    //region Constructors
    public Note(String name, String text, String pushId) {
        this.name = name;
        this.text = text;
        this.pushId = pushId;
    }

    public Note(String name, String text) {
        this(name, text, null);
    }

    public Note(){
        // Required for firebase
    }
    //endregion

    public static void sendNote(AppCompatActivity context, Note note) {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, note.getName());
            shareIntent.putExtra(Intent.EXTRA_TEXT, note.getText());
            //context.startActivity(Intent.createChooser(shareIntent, context.getString(R.string.share_with_title)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //region Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPushId(String pushId) {
        this.pushId = pushId;
    }

    public String getPushId() {
        return pushId;
    }
    //endregion -- end --
}
