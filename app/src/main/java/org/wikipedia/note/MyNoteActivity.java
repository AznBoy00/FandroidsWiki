package org.wikipedia.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

public class MyNoteActivity extends AppCompatActivity {

    private ListView notesListView;
    private Button button_add_new_note;

    private String userName;
    private RecyclerView myNotesList;

    // Firebase connection
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");

        notesListView = findViewById(R.id.notes_listView);
        button_add_new_note = findViewById(R.id.button_add_new_note);

        // Load and show created notes
        loadNotes();

        button_add_new_note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateNoteActivity();
            }
        });

    }

    // find noteId, verify user, and list
    private void loadNotes() {



    }

    private void openCreateNoteActivity() {

        Intent intent = new Intent(this, CreateNoteActivity.class);
        startActivity(intent);

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
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

}