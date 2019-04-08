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

        Message message = getItem(position);

        lastUID = currentUID;
        currentUID = message.getUID();

        if(currentUID.equals(FirebaseAuth.getInstance().getCurrentUser().getUid()) )
        {
            messageContainer.setGravity(Gravity.RIGHT);
            messageContainer.setPadding(0,0,12,0);
            //authorTextView.setGravity(Gravity.RIGHT);
        }else {

            messageContainer.setGravity(Gravity.LEFT);
        }

//        if(!lastUID.equals(currentUID)) {
//            authorTextView.setVisibility(View.VISIBLE);
//            authorTextView.setText(message.getName() + "\n" + currentUID);
//            //authorTextView.setText(message.getName() + getCurrentLastPositionString(position));
//        }else {
//            authorTextView.setVisibility(View.GONE);
//        }
        testCount++;
        authorTextView.setText(message.getName() + "\n" + testCount + " | " + position);

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

    public String getCurrentLastPositionString(int position){
        int atPosition;
        if(maxLoadLimit>this.getCount()) {
            atPosition = this.getCount() - position;
            return ""+ "\n at last position " + atPosition;
        }else {
            atPosition = this.maxLoadLimit - position;
            return ""+ "\n at last position " + atPosition;
        }
    }

//    public void setNameVisibility(boolean visibility){
//
//    }
}
