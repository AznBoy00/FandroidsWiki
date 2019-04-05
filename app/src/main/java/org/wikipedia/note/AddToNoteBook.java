package org.wikipedia.note;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddToNoteBook {

    private FirebaseUser user;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference dbReferenceNoteBook;
    private ChildEventListener NoteBookChildEventListener;
    private NoteBook noteBook;

    String noteId;
    ArrayList<String> noteBookList;



     public void NoteBookDbReadListener() {
         NoteBookChildEventListener = new ChildEventListener() {
             @Override
             public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                 NoteBook temp = dataSnapshot.getValue(NoteBook.class);
                 detachDataReadListener();
             }

             // Methods coming from the ChildEventListener interface that have to be implemented
             @Override
             public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

             @Override
             public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {}

             @Override
             public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {}

             @Override
             public void onCancelled(@NonNull DatabaseError databaseError) {}
         };
         dbReferenceNoteBook.addChildEventListener(NoteBookChildEventListener);
     }

    public void saveToNoteBook(String noteId, String userId){
        noteBookList.add(noteId);
        noteBook.setNoteBookId(userId);
        noteBook.setNoteBook(noteBookList);
    }

    private void detachDataReadListener() {
        if (NoteBookChildEventListener != null) {
            dbReferenceNoteBook.removeEventListener(NoteBookChildEventListener);
            NoteBookChildEventListener = null;
        }
    }
}
