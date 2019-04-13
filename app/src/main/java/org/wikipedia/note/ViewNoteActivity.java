package org.wikipedia.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.wikipedia.R;
import org.wikipedia.nfc.NfcActivity;

public class ViewNoteActivity extends Activity {
    private static final String TAG = "ViewNoteActivity";

    private Note note;
    private TextView createdAt;
    private TextView lastModified;
    private TextView noteTitle;
    private TextView noteContent;

    private DatabaseReference databaseReference;

    // Display the data of Notes
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String noteId = getIntent().getStringExtra("noteId");
        databaseReference = firebaseDatabase.getReference().child("Notes").child(noteId);

        note = new Note();

        createdAt  = findViewById(R.id.createdAt_insert);
        lastModified = findViewById(R.id.lastModified_insert);
        noteTitle = findViewById(R.id.noteTitle_insert);
        noteContent = findViewById(R.id.noteContent_insert);

        Log.v(TAG, "title" + noteTitle.toString());
        Log.v(TAG, "content" + noteContent.toString());

        attachDatabaseReadListener();

        Button button_return = findViewById(R.id.button_return_note);
        button_return.setOnClickListener(v -> finish());

        Button button_edit = findViewById(R.id.button_edit_note);
        onEditListener(button_edit, noteId);

        Button button_delete = findViewById(R.id.button_delete_note);
        onDeleteListener(button_delete, noteId);

        Button button_share = findViewById(R.id.button_share_note);
        button_share.setOnClickListener( v -> shareViaNfc(noteTitle.toString(), noteContent.toString()) );

    }

    private void onEditListener(Button btn, String id) {

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
            intent.putExtra("noteId",id);
            startActivity(intent);
            finish();
        });

    }

    private void onDeleteListener(Button btn, String id) {

        btn.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference().child("Notes").child(id).removeValue().addOnCompleteListener(task -> finish());
        });

    }

    public void attachDatabaseReadListener(){

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                note = dataSnapshot.getValue(Note.class);
                createdAt.setText(note.getCreatedTime());
                lastModified.setText(note.getLastModifiedTime());
                noteTitle.setText(note.getNoteTitle());
                noteContent.setText(note.getNoteContent());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void shareViaNfc(String title, String content) {
        Intent intent = new Intent(getApplicationContext(), NfcActivity.class);
        intent.putExtra("for", "note");
        intent.putExtra("title", title);
        intent.putExtra("content", content);
        Log.v(TAG, "for=" + intent.getStringExtra("for"));
        Log.v(TAG, "for=" + intent.getStringExtra("title"));
        Log.v(TAG, "for=" + intent.getStringExtra("content"));
        startActivity(intent);
    }

}