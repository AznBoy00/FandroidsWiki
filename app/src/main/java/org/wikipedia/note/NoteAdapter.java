// Refer to app\src\main\java\org\wikipedia\chatactivity\MessageAdapter.java
// Credit to Li Sun

package org.wikipedia.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;
import org.wikipedia.main.MainActivity;

import java.util.List;

class NoteAdapter extends ArrayAdapter<Note> {

    private Context  context;
    NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context =context;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_notes, parent, false);
        }

        TextView note_title = view.findViewById(R.id.note_title);
        TextView note_time = view.findViewById(R.id.note_time);

        Note note = getItem(position);

        note_title.setText(note.getNoteTitle());
        note_time.setText(note.getCreatedTime());

        CardView noteCard = view.findViewById(R.id.note_card);
        onViewListener(noteCard, note.getNoteId());

        Button editBtn = view.findViewById(R.id.edit_note);
        onEditListener(editBtn,note.getNoteId());
        Button deleteBtn = view.findViewById(R.id.delete_note);
        onDeleteListener(deleteBtn, note.getNoteId());

        return view;
    }

    private void onViewListener(CardView card, String noteId) {

        card.setOnClickListener(v -> openViewNoteActivity(noteId));

    }

    private void openViewNoteActivity(String noteId) {

        Intent intent = new Intent(context, ViewNoteActivity.class);
        intent.putExtra("noteId", noteId);
        context.startActivity(intent);

    }

    private void openEditNoteActivity(String id) {

        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra("noteId",id);
        context.startActivity(intent);

    }

    private void onEditListener(Button btn, String id){

        btn.setOnClickListener(v -> openEditNoteActivity(id));
    }

    private void onDeleteListener(Button btn, String id) {

        btn.setOnClickListener(v -> deleteNoteActivity(id));

    }

    private void deleteNoteActivity(String id) {

        FirebaseDatabase.getInstance().getReference().child("Notes").child(id).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {

            @Override
            public void onComplete(@NonNull Task<Void> task) {
                notifyDataSetChanged();
            }
        });

    }

}