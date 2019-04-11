package org.wikipedia.note;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.wikipedia.R;

public class ViewNoteActivity extends Activity {

    private Note note;
    private TextView createdAt;
    private TextView lastModified;
    private TextView noteTitle;
    private TextView noteContent;

    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        String noteId = getIntent().getStringExtra("noteId");
        databaseReference = firebaseDatabase.getReference().child("Notes").child(noteId);

        note = new Note();

        createdAt  = findViewById(R.id.textView8);
        lastModified = findViewById(R.id.textView10);
        noteTitle = findViewById(R.id.textView12);
        noteContent = findViewById(R.id.textView14);

        attachDatabaseReadListener();

        Button btn1 = findViewById(R.id.button_return_note);
        Button btn2 = findViewById(R.id.button_edit_note);
        Button btn3 = findViewById(R.id.button_delete_note);

        onReturnListener(btn1);
        onEditListener(btn2, noteId);
        onDeleteListener(btn3, noteId);

    }

    private void onReturnListener(Button btn) {

        btn.setOnClickListener(v -> onFinish());

    }

    private void onEditListener(Button btn, String id) {

        btn.setOnClickListener(v -> editNoteActivity(id));

    }

    private void onDeleteListener(Button btn, String id) {

        btn.setOnClickListener(v -> deleteNoteActivity(id));

    }

    private void editNoteActivity(String id) {

        Intent intent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
        intent.putExtra("noteId",id);
        startActivity(intent);
        finish();

    }

    private void deleteNoteActivity(String id) {

        FirebaseDatabase.getInstance().getReference().child("Notes").child(id).removeValue().addOnCompleteListener(task -> onFinish());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode==RESULT_OK){
            Intent refresh = new Intent(this, ViewNoteActivity.class);
            startActivity(refresh);
            this.finish();
        }
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

    protected void onFinish() {
        this.finish();
    }

}