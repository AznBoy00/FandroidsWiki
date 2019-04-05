package org.wikipedia.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
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


public class CreateNoteActivity extends AppCompatActivity {
    private static final String TAG = "CreateNoteActivity";

    public final int DEFAULT_NOTE_CONTENT_LENGTH_LIMIT = 1000;

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private DatabaseReference dbReferenceNoteBook;
    private Button noteSaveButton;
    private EditText newNoteTitle;
    private EditText newNoteContent;
    private ChildEventListener childEventListener;

    String currentTime;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");
        dbReferenceNoteBook = firebaseDatabase.getReference().child("NoteBook");

        newNoteTitle = (EditText) findViewById(R.id.new_note_title);
        newNoteContent = (EditText) findViewById(R.id.new_note_content);
        noteSaveButton = (Button) findViewById(R.id.button_save_note);

        saveNote();

    }

    public void saveNote() {
        noteSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = user.getUid();
                currentTime = dateFormat.format(Calendar.getInstance().getTime());
                String noteId = databaseReference.push().getKey();
                Note newNote = new Note(noteId, user.getUid(), user.getUid(), newNoteTitle.getText().toString(), newNoteContent.getText().toString(), currentTime, currentTime);
                databaseReference.child(noteId).setValue(newNote);
                // add note to noteBook
                AddToNoteBook addToNoteBook = new AddToNoteBook();
                addToNoteBook.saveToNoteBook(noteId, user.getUid());

                onFinish();
            }
        });

    }

    protected void onFinish() {
        this.finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        this.finish();
    }



}
