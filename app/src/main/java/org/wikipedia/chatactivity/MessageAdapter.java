package org.wikipedia.chatactivity;

import android.app.Activity;
import android.content.Context;
import android.sax.Element;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.wikipedia.chatactivity.ChatActivity;
import com.google.firebase.auth.FirebaseAuth;

import org.wikipedia.R;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int maxLoadLimit = 100;
    private String lastUID ="";
    private String currentUID ="";
    private int testCount =0;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        LinearLayout messageContainer = (LinearLayout) convertView.findViewById(R.id.messageViewContainer);
        ImageView photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
        TextView messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);
        TextView authorTextView = (TextView) convertView.findViewById(R.id.nameTextView);
        TextView positionTextView = (TextView) convertView.findViewById(R.id.lastPositionTextView);

        Message message = getItem(position);
        if(position>0) {
            lastUID = getItem(position-1).getUID();
        }else {
            lastUID = "-1";
        }
        currentUID = message.getUID();

        if(currentUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) )
        {
            messageContainer.setGravity(Gravity.RIGHT);
            messageContainer.setPadding(0,0,12,0);
            messageTextView.setBackgroundResource(R.drawable.edit_improve_tag_selected_dark);
        }else {

            messageContainer.setGravity(Gravity.LEFT);
            messageTextView.setBackgroundResource(R.drawable.chip_background);
        }

        if(!lastUID.equals(currentUID)) {
            authorTextView.setVisibility(View.VISIBLE);
            authorTextView.setText(message.getName());
            positionTextView.setVisibility(View.VISIBLE);
            positionTextView.setText(getCurrentLastPositionString(position));
        }else {
            authorTextView.setVisibility(View.GONE);
            positionTextView.setVisibility(View.GONE);
        }

//        authorTextView.setText(message.getName() + "\n" + " | " + position);

        boolean isPhoto = message.getPhotoUrl() != null;
        if (isPhoto) {
            messageTextView.setVisibility(View.GONE);
            photoImageView.setVisibility(View.VISIBLE);
            Glide.with(photoImageView.getContext())
                    .load(message.getPhotoUrl())
                    .into(photoImageView);
        } else {
            messageTextView.setVisibility(View.VISIBLE);
            photoImageView.setVisibility(View.GONE);
            messageTextView.setText(message.getText());
        }


        return convertView;
    }


    public void setMaxLoadLimit(int maxLoadLimit){
       this.maxLoadLimit = maxLoadLimit;
    }
    public int getMaxLoadLimit(){
        return maxLoadLimit;
    }

    public String getCurrentLastPositionString(int position){
        int atPosition;
        if(maxLoadLimit>this.getCount()) {
            atPosition = this.getCount() - position;
            return ""+ "at the last position " + atPosition;
        }else {
            atPosition = this.maxLoadLimit - position;
            return ""+ "at the last position " + atPosition;
        }
    }

}
