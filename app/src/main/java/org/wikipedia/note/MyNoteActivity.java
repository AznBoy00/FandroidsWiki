package org.wikipedia.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

import java.util.ArrayList;
import java.util.List;

public class MyNoteActivity extends AppCompatActivity {

    private NoteAdapter noteAdapter;

    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Firebase connection
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");

        // locate the appropriate elements in the layout xml
        ListView notesListView = findViewById(R.id.notes_listView);
        Button button_return = findViewById(R.id.button_return);
        Button button_add_new_note = findViewById(R.id.button_add_new_note);

        // terminate this activity and return to Wiki's main menu
        button_return.setOnClickListener(v -> finish());

        // open another activity to start creating Note object
        button_add_new_note.setOnClickListener(v -> openCreateNoteActivity());

        // setup adapter to display CardView (list) of Notes
        List<Note> myNotes = new ArrayList<>();
        noteAdapter = new NoteAdapter(this, R.layout.item_notes, myNotes);
        notesListView.setAdapter(noteAdapter);

        // read object data from Firebase
        attachDatabaseReadListener();

    }

    private void openCreateNoteActivity() {

        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);

    }

    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Note note = dataSnapshot.getValue(Note.class);
                    noteAdapter.add(note);
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
    }

    private void detachDataReadListener() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    protected void onResume() {
        attachDatabaseReadListener();
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        detachDataReadListener();
        noteAdapter.clear();
    }

}