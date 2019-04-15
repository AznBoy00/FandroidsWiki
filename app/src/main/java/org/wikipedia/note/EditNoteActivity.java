package org.wikipedia.note;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class EditNoteActivity extends Activity {
    private static final String TAG = "EditNoteActivity";
    private DatabaseReference databaseReference;
    private Button noteSaveButton;
    private EditText noteTitle;
    private EditText noteContent;
    private String noteId;
    private Note note;
    private NoteAdapter noteAdapter;
    private ChildEventListener childEventListener;

    String currentTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");
        noteId = getIntent().getStringExtra("noteId");
        note = new Note();

        attachDatabaseReadListener();

        noteTitle = findViewById(R.id.note_title);
        noteContent = findViewById(R.id.note_content);
        Button returnButton = findViewById(R.id.button_return);
        noteSaveButton = findViewById(R.id.button_save_note_from_edit);
        noteTitle.setText("");
        noteContent.setText("");

        returnButton.setOnClickListener(v -> finish());

        // collect edited data and replace the object on Firebase
        saveNote();

    }

    public void saveNote() {
        noteSaveButton.setOnClickListener(v -> {
            String note_title = noteTitle.getText().toString();
            String note_content = noteContent.getText().toString();
            noteId = getIntent().getStringExtra("noteId");

            saveMyNote(note_title,note_content,noteId, note, databaseReference);
        });
    }

    // Method created to help making the code testable
    public Note saveMyNote(String note_title, String note_content, String noteId,Note note, DatabaseReference databaseReference) {
        currentTime = dateFormat.format(Calendar.getInstance().getTime());

        note.setNoteTitle(note_title);
        note.setNoteContent(note_content);
        note.setLastModifiedTime(currentTime);
        Log.e(TAG, noteId + "\n" + note.getNoteContent());
        databaseReference.child(noteId).setValue(note);

        finish();
        return note;
    }

    public void attachDatabaseReadListener() {
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Note temp = dataSnapshot.getValue(Note.class);
                if (temp.getNoteId().equals(noteId)) {
                    note = temp;
                    note.setNoteId(noteId);

                    if (note.getNoteTitle() != null)
                        noteTitle.setText(note.getNoteTitle());
                    else
                        noteTitle.setText("");

                    if (note.getNoteContent() != null)
                        noteContent.setText(note.getNoteContent());
                    else
                        noteContent.setText("");

                    detachDataReadListener();
                }
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };

        databaseReference.addChildEventListener(childEventListener);
    }

    private void detachDataReadListener() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

}