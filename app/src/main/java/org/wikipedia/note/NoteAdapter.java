// Refer to app\src\main\java\org\wikipedia\chatactivity\MessageAdapter.java
// Credit to Li Sun

package org.wikipedia.note;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.wikipedia.R;

import java.util.List;

class NoteAdapter extends ArrayAdapter<Note> {

    public NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
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

        return convertView;
    }
}