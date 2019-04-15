package org.wikipedia.note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class CreateNoteActivity extends Activity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private Button noteSaveButton;
    private EditText newNoteTitle;
    private EditText newNoteContent;
    private String noteTitle;
    private String noteContent;
    private ChildEventListener childEventListener;

    String currentTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        // get the info of Wiki++ user
        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");
        newNoteTitle = findViewById(R.id.new_note_title);
        newNoteContent = findViewById(R.id.new_note_content);
        Button noteReturnButton = findViewById(R.id.button_return);
        noteSaveButton = findViewById(R.id.button_save_note);

        noteReturnButton.setOnClickListener(v -> finish());
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            noteTitle = extras.get("noteTitle").toString();
            newNoteTitle.setText(noteTitle);
            noteContent = extras.getString("noteContent");
            newNoteContent.setText(noteContent);
        }
        // collect object's data and put it on Firebase
        saveNote();
    }

    public void saveNote() {
        noteSaveButton.setOnClickListener(v -> {
            String noteId = databaseReference.push().getKey();
            if (noteContent != null) {
                saveMyNote(noteId, user.getUid(), user.getUid(), newNoteTitle.getText().toString(), noteContent, databaseReference);
            }
            saveMyNote(noteId, user.getUid(), user.getUid(), newNoteTitle.getText().toString(), newNoteContent.getText().toString(), databaseReference);
            finish();
        });
    }

    // Method created to help making the code testable
    public Note saveMyNote(String noteId, String userId, String noteBookId, String noteTitle, String noteContent, DatabaseReference databaseReference) {
        currentTime = dateFormat.format(Calendar.getInstance().getTime());
        Note newNote = new Note(noteId, userId, noteBookId, noteTitle, noteContent, currentTime, currentTime);
        databaseReference.child(noteId).setValue(newNote);
        finish();
        return newNote;
    }

}