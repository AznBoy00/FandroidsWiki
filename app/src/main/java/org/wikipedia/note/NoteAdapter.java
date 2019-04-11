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

    private List<Note> noteList;
    private NoteAdapter adapter;
    private Context context;

    NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.noteList = objects;
        this.context = context;
        this.adapter = this;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        // locate the card's layout file and create View
        if (view == null) {
            view = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_notes, parent, false);
        }

        TextView note_title = view.findViewById(R.id.note_title);
        TextView note_time = view.findViewById(R.id.note_time);

        // get the proper Note to perform following tasks
        Note note = noteList.get(position);

        note_title.setText(note.getNoteTitle());
        note_time.setText(note.getCreatedTime());

        // open and show the details of selected Note
        CardView noteCard = view.findViewById(R.id.note_card);
        onViewListener(noteCard, note.getNoteId());

        // open and allow modifying the Note
        Button editBtn = view.findViewById(R.id.edit_note);
        onEditListener(editBtn, note.getNoteId());

        // delete the Note from arrayList and Firebase
        Button deleteBtn = view.findViewById(R.id.delete_note);
        onDeleteListener(note, deleteBtn, note.getNoteId());

        return view;
    }

    private void onViewListener(CardView card, String noteId) {

        card.setOnClickListener(v -> {
            Intent intent = new Intent(context, ViewNoteActivity.class);
            intent.putExtra("noteId", noteId);
            context.startActivity(intent);
        });

    }

    private void onEditListener(Button btn, String id){

        btn.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditNoteActivity.class);
            intent.putExtra("noteId",id);
            context.startActivity(intent);
            adapter.notifyDataSetChanged();
        });

    }

    private void onDeleteListener(Note note,Button btn, String id) {

        btn.setOnClickListener(v -> {
            FirebaseDatabase.getInstance().getReference().child("Notes").child(id).removeValue();
            noteList.remove(note);
            adapter.notifyDataSetChanged();
        });

    }

}