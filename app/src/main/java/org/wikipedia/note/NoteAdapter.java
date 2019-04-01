// Refer to app\src\main\java\org\wikipedia\chatactivity\MessageAdapter.java
// Credit to Li Sun

package org.wikipedia.note;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import org.wikipedia.R;

import java.util.List;

class NoteAdapter extends ArrayAdapter<Note> {

    private Context  context;
    public NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
        this.context =context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_notes, parent, false);
        }

        TextView note_title = convertView.findViewById(R.id.note_title);
        TextView note_time = convertView.findViewById(R.id.note_time);

        Note note = getItem(position);

        note_title.setText(note.getNoteTitle());
        note_time.setText(note.getCreatedTime());

        Button editBtn = convertView.findViewById(R.id.edit_note);
        onEditListener(editBtn,note.getNoteId());
        Button deleteBtn = convertView.findViewById(R.id.delete_note);

        return convertView;
    }

    private void openEditNoteActivity(String id) {

        Intent intent = new Intent(context, EditNoteActivity.class);
        intent.putExtra("noteId",id);
        context.startActivity(intent);

    }

    public void onEditListener(Button btn ,String id){

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEditNoteActivity(id);

            }
        } );
    }

}