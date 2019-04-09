package org.wikipedia.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreateNoteActivity extends Activity {
    private static final String TAG = "CreateNoteActivity";

    public final int DEFAULT_NOTE_CONTENT_LENGTH_LIMIT = 1000;

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private Button noteReturnButton;
    private Button noteSaveButton;
    private EditText newNoteTitle;
    private EditText newNoteContent;

    String currentTime;
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        user = FirebaseAuth.getInstance().getCurrentUser();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");

        newNoteTitle = (EditText) findViewById(R.id.new_note_title);
        newNoteContent = (EditText) findViewById(R.id.new_note_content);
        noteReturnButton = findViewById(R.id.button_return);
        noteSaveButton = (Button) findViewById(R.id.button_save_note);

        noteReturnButton.setOnClickListener(new View.OnClickListener() {

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
                String str = user.getUid();
                currentTime = dateFormat.format(Calendar.getInstance().getTime());
                String noteId = databaseReference.push().getKey();
                Note newNote = new Note(noteId, user.getUid(), user.getUid(), newNoteTitle.getText().toString(), newNoteContent.getText().toString(), currentTime, currentTime);
                databaseReference.child(noteId).setValue(newNote);

                onFinish();
            }
        });

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
