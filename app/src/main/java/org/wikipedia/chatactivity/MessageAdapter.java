package org.wikipedia.chatactivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;
import android.sax.Element;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import org.wikipedia.chatactivity.ChatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.wikipedia.R;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {
    private int maxLoadLimit = 100;
    private String lastUID ="";
    private String currentUID ="";
    private int testCount =0;
    private StorageReference storageRef =  FirebaseStorage.getInstance().getReference();
//    private StorageReference storageRef =  FirebaseStorage.getInstance().getReferenceFromUrl("gs://soen390teamfandroidswiki-383a4.appspot.com/");
    private final String TAG = "MessageAdapter: ";
    ImageView photoImageView;

    public MessageAdapter(Context context, int resource, List<Message> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = ((Activity) getContext()).getLayoutInflater().inflate(R.layout.item_message, parent, false);
        }

        LinearLayout messageContainer = (LinearLayout) convertView.findViewById(R.id.messageViewContainer);
        photoImageView = (ImageView) convertView.findViewById(R.id.photoImageView);
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

            //storageRef.child(message.getPhotoUrl());
            photoImageView.setVisibility(View.VISIBLE);
            String key = message.getPhotoUrl().replace("images/group_chat/"+message.getUID()+"/","");
            String text = message.getText();
            String path = message.getPhotoUrl();
            Log.e(""+TAG,"key "+ key);
            Log.e(""+TAG,"path "+ path);
            Log.e(""+TAG,"ref "+ storageRef.toString());
            //loadImage(""+key,""+message.getPhotoUrl(), photoImageView);
            try{
                Log.e("" + TAG, " start - try ");
                final File imgFile = File.createTempFile(""+key, "jpg");
                Log.e("" + TAG, " try - done create temp imgFile ");
                storageRef.child("images/").child("group_chat/").child(message.getUID()+"/").child(key+".jpg" )
                        .getFile(imgFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                       Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
//                       Bitmap bitmap = MediaStore.Images.Media.getBitmap(imgFile.getAbsolutePath());
//                        if(bitmap ==null) {
//                            Log.e("" + TAG, "bitmap null ");
//                        }else {
//
//                            Log.e("" + TAG, "bitmap  \n" + bitmap.getConfig());
//                        }
//                        Log.e("" + TAG, " photoImageView "+ photoImageView.getId());

                        photoImageView.setImageBitmap(bitmap);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });

            }catch (IOException e){
                e.printStackTrace();
            }
//            if(text.length()>0) {
//                messageTextView.setVisibility(View.VISIBLE);
//                messageTextView.setText(text);
//            }
//            Glide.with(photoImageView.getContext())
//                    .load(message.getPhotoUrl()+".jpg")
//                    .into(photoImageView);
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
            return ""+ "at the last position " + atPosition;
        }else {
            atPosition = this.maxLoadLimit - position;
            return ""+ "at the last position " + atPosition;
        }
    }

    public void loadImage(String fileName, String path, ImageView imageView){
        try{
            final File imgFile = File.createTempFile(""+ fileName, "jpg");
            storageRef.child(path+".jpg" ).getFile(imgFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                    imageView.setImageBitmap(bitmap);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }
            });

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
