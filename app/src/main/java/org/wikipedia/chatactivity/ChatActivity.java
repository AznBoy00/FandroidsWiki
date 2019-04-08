package org.wikipedia.chatactivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import org.wikipedia.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class ChatActivity extends AppCompatActivity {

    private static final String TAG = "ChatActivity";

    public static final int DEFAULT_MSG_LENGTH_LIMIT = 1000;

    private ListView mMessageListView;
    private MessageAdapter mMessageAdapter;
    private ProgressBar mProgressBar;
    private ImageButton mPhotoPickerButton;
    private ImageButton discardImgSelectionButton;
    private EditText mMessageEditText;
    private Button mSendButton;
    private ImageView messageImgPreView;

    private Uri filePath = null;

    private String mUsername;
    private FirebaseUser user;

    //Firebase db
    private FirebaseDatabase database;
    private DatabaseReference myDBRef;
    private ChildEventListener childEventListener;

    private Query maxLoadLimitQuery;
    private int maxLoadLimit = 100; //default 100

    private final int IMAGE_PICK_REQUEST_CODE = 39001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mUsername = FirebaseAuth.getInstance().getCurrentUser().getDisplayName();
        user = FirebaseAuth.getInstance().getCurrentUser();

        //firebase db
        database = FirebaseDatabase.getInstance();
        myDBRef = database.getReference().child("messages");
        setMaxLoadLimit(100);

        // Initialize references to views
        mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMessageListView = (ListView) findViewById(R.id.messageListView);
        mPhotoPickerButton = (ImageButton) findViewById(R.id.photoPickerButton);
        mMessageEditText = (EditText) findViewById(R.id.messageEditText);
        mSendButton = (Button) findViewById(R.id.sendButton);
        messageImgPreView = (ImageView) findViewById(R.id.messageImgPreView);

        discardImgSelectionButton = (ImageButton) findViewById(R.id.discardImgSelectionButton);

        if(filePath == null){
            discardImgSelectionButton.setVisibility(View.GONE);
        }

        initialDiscardImgSelectionButtonListener();

        // Initialize message ListView and its adapter
        List<Message> friendlyMessages = new ArrayList<>();
        mMessageAdapter = new MessageAdapter(this, R.layout.item_message, friendlyMessages);
        mMessageAdapter.setMaxLoadLimit(this.maxLoadLimit);
        mMessageListView.setAdapter(mMessageAdapter);

        // Initialize progress bar
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);

        // ImagePickerButton shows an image picker to upload a image for a message
        mPhotoPickerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: Fire an intent to show an image picker
                // pick a picture
                pickIMG();
            }
        });

        // Enable Send button when there's text to send
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        mMessageEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(DEFAULT_MSG_LENGTH_LIMIT)});

        // Send button sends a message and clears the EditText
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // firebase db
                Message Message = new Message(mMessageEditText.getText().toString(), mUsername, null, user.getUid());
                myDBRef.push().setValue(Message);
                // Clear input box after press send
                mMessageEditText.setText("");
            }
        });

        attachDatabaseReadListener();

    }


    //Help function to attachDB
    private void attachDatabaseReadListener() {
        if (childEventListener == null) {
            childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    Message message = dataSnapshot.getValue(Message.class);
                    mMessageAdapter.add(message);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            };
            //myDBRef.addChildEventListener(childEventListener);
            maxLoadLimitQuery.addChildEventListener(childEventListener);
        }
    }

    private void detachDataReadListener() {
        if (childEventListener != null) {
            //myDBRef.removeEventListener(childEventListener);
            maxLoadLimitQuery.removeEventListener(childEventListener);
            childEventListener = null;
        }
    }

    @Override
    protected void onResume() {
        attachDatabaseReadListener();
        super.onResume();
    }


    @Override
    protected void onPause() {
        super.onPause();
        detachDataReadListener();
        mMessageAdapter.clear();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }

    public void setMaxLoadLimit(int max){
        this.maxLoadLimit = max;
        maxLoadLimitQuery = myDBRef.orderByKey().limitToLast(maxLoadLimit);
    }

    public int getMaxLoadLimit(){
        return this.maxLoadLimit;
    }

    //region image
    private void pickIMG(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent,"Images"), 0 + IMAGE_PICK_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(0 + requestCode, 0 + resultCode, data);
        if(requestCode == IMAGE_PICK_REQUEST_CODE && resultCode == RESULT_OK)
        {
            filePath = data.getData();
            discardImgSelectionButton.setVisibility(View.VISIBLE);
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                messageImgPreView.setImageBitmap(bitmap);
            }catch (IOException e)
            {
                e.printStackTrace();
            }
            Log.e(""+TAG," file path: "+ filePath);
            //final String photoUrl = "images/" + user.getUid() + "/";
        }
    }

    private void initialDiscardImgSelectionButtonListener(){

        discardImgSelectionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filePath =null;
                discardImgSelectionButton.setVisibility(View.GONE);
                messageImgPreView.setImageBitmap(null);
            }

        });
    }
    //endregion

}
