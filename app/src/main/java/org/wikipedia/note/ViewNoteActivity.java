package org.wikipedia.note;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

public class ViewNoteActivity extends AppCompatActivity {

    private String userName;
    private String noteId;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference().child("Notes");

        /** try {
            noteId = getIntent().getStringExtra("noteId");
        } catch (Exception e) {
            e.printStackTrace();
        } **/

        /** Button btn1 = findViewById(R.id.button_edit_note);
        Button btn2 = findViewById(R.id.button_delete_note);

        btn1.setVisibility(View.GONE);
        btn2.setVisibility(View.GONE); **/

    }
}