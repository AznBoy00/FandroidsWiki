package org.wikipedia.note;

import android.annotation.SuppressLint;
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
import java.util.Locale;


public class CreateNoteActivity extends Activity {

    private FirebaseUser user;
    private DatabaseReference databaseReference;
    private Button noteSaveButton;
    private EditText newNoteTitle;
    private EditText newNoteContent;

    String currentTime;
    @SuppressLint("SimpleDateFormat")
    DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd, HH:mm:ss z");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_note);

        user = FirebaseAuth.getInstance().getCurrentUser();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");

        newNoteTitle = findViewById(R.id.new_note_title);
        newNoteContent = findViewById(R.id.new_note_content);
        Button noteReturnButton = findViewById(R.id.button_return);
        noteSaveButton = findViewById(R.id.button_save_note);

        noteReturnButton.setOnClickListener(v -> finish());

        saveNote();

    }

    public void saveNote() {
        noteSaveButton.setOnClickListener(v -> {
            currentTime = dateFormat.format(Calendar.getInstance().getTime());
            String noteId = databaseReference.push().getKey();
            Note newNote = new Note(noteId, user.getUid(), user.getUid(), newNoteTitle.getText().toString(), newNoteContent.getText().toString(), currentTime, currentTime);
            databaseReference.child(noteId).setValue(newNote);

            onFinish();
        });

    }

    protected void onFinish() {
        this.finish();
    }

}
