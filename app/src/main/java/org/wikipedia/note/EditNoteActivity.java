package org.wikipedia.note;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
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
    private String userName;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button returnButton;
    private Button noteSaveButton;
    private EditText noteTitle;
    private EditText noteContent;
    private String noteId;
    private Note note;
    private DatabaseReference noteDBRef;
    private NoteAdapter noteAdapter;
    private ChildEventListener childEventListener;

    String currentTime;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");
        noteId = getIntent().getStringExtra("noteId");
        noteDBRef = firebaseDatabase.getReference().child("Notes").child(noteId);
        note = new Note();

        attachDatabaseReadListener();

        noteTitle = (EditText) findViewById(R.id.note_title);
        noteContent = (EditText) findViewById(R.id.note_content);
        returnButton = findViewById(R.id.button_return);
        noteSaveButton = (Button) findViewById(R.id.button_save_note_from_edit);
        noteTitle.setText("");
        noteContent.setText("");

        returnButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });

        saveNote();

    }

    public void saveNote() {
        noteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                currentTime = dateFormat.format(Calendar.getInstance().getTime());
                //String noteId = databaseReference.push().getKey();

                note.setNoteTitle(noteTitle.getText().toString());
                note.setNoteContent(noteContent.getText().toString());
                note.setLastModifiedTime(currentTime);
                Log.e(TAG,noteId +"\n"+note.getNoteContent());
                databaseReference.child(noteId).setValue(note);

                //setResult(RESULT_OK, null);
                //finish();
                onFinish();
            }
        });

    }

    public void attachDatabaseReadListener(){
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Note temp = dataSnapshot.getValue(Note.class);
                if(temp.getNoteId().equals(noteId)) {
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
                    //detach
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

    protected void onFinish() {
        this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}