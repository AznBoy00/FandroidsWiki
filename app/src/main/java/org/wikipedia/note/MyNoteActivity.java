package org.wikipedia.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

public class MyNoteActivity extends AppCompatActivity {

    private ListView notesListView;
    private Button button_add_note;

    private String userName;

    // Firebase connection
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();



        notesListView = findViewById(R.id.notes_listView);
        button_add_note = findViewById(R.id.button_add_note);

    }

    public void createNote() {

    }

    public void updateNote() {

    }

    public void deleteNote() {

    }



}