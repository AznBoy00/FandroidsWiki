package org.wikipedia.notes;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.ArrayAdapter;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.Query;

import org.wikipedia.R;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Note> {

    //region Member attributes
    private AppCompatActivity mContext;
    private String mUserID;

    //endregion


    public NoteAdapter(Context context, int resource, List<Note> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);

        }
        //TextView noteTitleTextView = (TextView) convertView.findViewById(R.id.note_title_text_view);
        //TextView noteTitleTextView = (TextView) convertView.findViewById(R.id.note_content_text_view);
        return null;
    }

    //region old bugged
    //@NonNull
    //@Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return null;
    }
    //endregion

    //region Callbacks for view events.
    interface OnDeleteNoteListener {
        void OnNoteDelete(Note note, String userId);
    }
    private OnDeleteNoteListener mOnNoteDeleteClickedListener;

    interface OnDataChangedListener {
        void OnDataChanged();
    }
    private OnDataChangedListener mOnDataChangedListener;
    //endregion

    public static class NoteViewHolder extends RecyclerView.ViewHolder {
        public Toolbar toolbar;
        public TextView text;
        public LinearLayout noteContent;

        public NoteViewHolder(View itemView) {
            super(itemView);

            toolbar = (Toolbar) itemView.findViewById(R.id.note_toolbar);
            text = (TextView) itemView.findViewById(R.id.note_text);
            noteContent = (LinearLayout) itemView.findViewById(R.id.note_content);
        }
    }

//    public FirebaseNoteAdapter(AppCompatActivity context, String firebaseUserId, Query query){
//
//        super(Note.class, R.layout.note_view_layout, NoteViewHolder.class, query);
//        this.mContext = context;
//        this.mUserID = firebaseUserId;
//    }

//    @Override
//    protected void populateViewHolder(NoteViewHolder holder, final Note note, int position) {
//        note.setPushId(getRef(position).getKey());
//        holder.text.setText(note.getText());
//        holder.toolbar.setTitle(note.getName());
//
//        // Make sure menu hasn't been inflated yet.
//        int menuSize = holder.toolbar.getMenu().size();
//        if (menuSize == 0) {
//            holder.toolbar.inflateMenu(R.menu.note_view_menu);
//        }


//        holder.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                int id = item.getItemId();
//                switch (id){
//                    case(R.id.action_delete):{
//                        if(mOnNoteDeleteClickedListener != null) {
//                            mOnNoteDeleteClickedListener.OnNoteDelete(note, mUserID);
//                        }
//
//                    }break;
//                }
//                return false;
//            }
//        });

//        holder.noteContent.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                NoteEditorActivity.startActivity(mContext, mUserID, note);
//            }
//        });
//    }

//    @Override
//    public void onDataChanged() {
//        super.onDataChanged();
//        mOnDataChangedListener.OnDataChanged();
//    }
    //endregion

    public void setOnDataChangedListener(OnDataChangedListener listener){
        mOnDataChangedListener = listener;
    }

    public void setOnNoteDeleteListener(OnDeleteNoteListener listener){
        mOnNoteDeleteClickedListener = listener;
    }


}
