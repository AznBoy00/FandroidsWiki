package org.wikipedia.note;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.wikipedia.R;

public class ViewNoteActivity extends AppCompatActivity {

    //private Context context;
    private Note note;
    private String userName;
    private String noteId;
    private TextView noteIdShow;
    private TextView userId;
    private TextView notebookId;
    private TextView createdAt;
    private TextView lastModified;
    private TextView noteTitle;
    private TextView noteContent;

    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ChildEventListener childEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_view);

        userName = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();

        firebaseDatabase = FirebaseDatabase.getInstance();
        noteId = getIntent().getStringExtra("noteId");
        databaseReference = firebaseDatabase.getReference().child("Notes").child(noteId);

        note = new Note();

        /** try {
            noteId = getIntent().getStringExtra("noteId");
        } catch (Exception e) {
            e.printStackTrace();
        } **/

        noteIdShow  = findViewById(R.id.textView2);
        userId  = findViewById(R.id.textView4);
        notebookId  = findViewById(R.id.textView6);
        createdAt  = findViewById(R.id.textView8);
        lastModified = findViewById(R.id.textView10);
        noteTitle = findViewById(R.id.textView12);
        noteContent = findViewById(R.id.textView14);

        attachDatabaseReadListener();

        //Log.e("get Note from db? ", note.getNoteContent());

        Button btn1 = findViewById(R.id.button_return_note);
        Button btn2 = findViewById(R.id.button_edit_note);
        Button btn3 = findViewById(R.id.button_delete_note);

        onReturnListener(btn1);
        onEditListener(btn2, noteId);
        onDeleteListener(btn3, noteId);


        //btn1.setVisibility(View.GONE);
        //setVisibility(View.GONE);

    }

    private void onReturnListener(Button btn) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFinish();
            }
        });

    }

    private void onEditListener(Button btn, String id) {

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editNoteActivity(id);
            }
        });
    }

    private void onDeleteListener(Button btn, String id) {

        btn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                deleteNoteActivity(id);
            }

        });

    }

    private void editNoteActivity(String id) {

        Intent intent = new Intent(ViewNoteActivity.this, EditNoteActivity.class);
        intent.putExtra("noteId",id);
        startActivity(intent);

    }

    private void deleteNoteActivity(String id) {

        FirebaseDatabase.getInstance().getReference().child("Notes").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                onFinish();
            }
        });

    }

    public void attachDatabaseReadListener(){
        /**childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
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

        databaseReference.addChildEventListener(childEventListener); **/

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                note = dataSnapshot.getValue(Note.class);
                noteIdShow.setText(note.getNoteId());
                userId.setText(note.getUserId());
                notebookId.setText(note.getNoteBookId());
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
    private void detachDataReadListener() {
        if (childEventListener != null) {
            databaseReference.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    protected void onFinish() {
        this.finish();
    }

}