package org.wikipedia.note;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.wikipedia.R;

public class MyNoteViewHolder extends RecyclerView.ViewHolder {

    View myView;
    CardView noteCard;
    TextView noteTitle;
    TextView noteTime;


    public MyNoteViewHolder(View itemsView) {
        super(itemsView);

        myView = itemsView;

        noteCard = myView.findViewById(R.id.note_card);
        noteTitle = myView.findViewById(R.id.note_title);
        noteTime = myView.findViewById(R.id.note_time);

    }

    public View getMyView() {
        return myView;
    }

    public void setMyView(View myView) {
        this.myView = myView;
    }

    public CardView getNoteCard() {
        return noteCard;
    }

    public void setNoteCard(CardView noteCard) {
        this.noteCard = noteCard;
    }

    public TextView getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(TextView noteTitle) {
        this.noteTitle = noteTitle;
    }

    public TextView getNoteTime() {
        return noteTime;
    }

    public void setNoteTime(TextView noteTime) {
        this.noteTime = noteTime;
    }
}