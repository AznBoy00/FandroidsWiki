package org.wikipedia.notes;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.wikipedia.R;

public class NoteEditorActivity extends AppCompatActivity {

    public static final int RC_NOTE = 0x100;
    String DELETE_NOTE_TAG = "confirm-deletion";
    public static final String NOTE_KEY = "NOTE";
    private static final String USER_ID_KEY = "USER_ID";

    //region Member attributes
    private EditText mNoteField;
    private EditText mNoteTitle;
    private Note mNote = new Note();
    private String mUserID;

    FirebaseDatabase mFireDatabase;
    private DatabaseReference mFireNotesRef;
    //endregion

    //region Methods responsible for handling the activity's lifecycle
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note_editor);

        mNoteField = (EditText) findViewById(R.id.note_field);
        mNoteTitle = (EditText) findViewById(R.id.note_title);
        mNoteField.requestFocus();

        Intent data = getIntent();
        mUserID = data.getStringExtra(USER_ID_KEY);

        if (data.hasExtra(NOTE_KEY)) {
            mNote = (Note) data.getSerializableExtra(NOTE_KEY);
            mNoteField.setText(mNote.getText());
            mNoteTitle.setText(mNote.getName());
        }

        mFireDatabase = FirebaseUtils.getDatabase();
        mFireNotesRef = mFireDatabase.getReference().child("users/" + mUserID + "/notes");

        ConfirmationDialog.resetOnYesClickListener(this, DELETE_NOTE_TAG, onYesDeleteNote);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.note_editor_menu, menu);
        return true;
    }
    //endregion -- end --

    //region Methods responsible for handling events.
    ConfirmationDialog.MyClickListener onYesDeleteNote = new ConfirmationDialog.MyClickListener() {
        @Override
        public void onClick(Bundle extras) {
            mFireDatabase.getReference("users/" + mUserID + "/notes/" + mNote.getPushId())
                    .removeValue()
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            //Toast.makeText(NoteEditorActivity.this, R.string.deletion_success, Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case (R.id.action_delete): {
                if (mNote.getPushId() != null) {
//                    new ConfirmationDialog()
//                            .setTitle(R.string.note_deletion_dialog_title)
//                            .setMessage(R.string.action_warning)
//                            .setOnYesClickListener(onYesDeleteNote)
//                            .show(getSupportFragmentManager(), DELETE_NOTE_TAG);

                }
            }
            break;

            case (R.id.action_save): {
                if (mNote.getPushId() == null) {
                    // If note isn't in database yet, add it
                    DatabaseReference ref = mFireNotesRef.push();
                    mNote.setPushId(ref.getKey());
                    ref.setValue(getNote())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(NoteEditorActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else {
                    // If note is in the database already, update it
                    mFireNotesRef.child(mNote.getPushId())
                            .setValue(getNote())
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                   // Toast.makeText(NoteEditorActivity.this, R.string.save_success, Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
            break;

            case (R.id.action_send): {
                Note.sendNote(this, getNote());
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
    //endregion -- end --

    public Note getNote() {
        String name = mNoteTitle.getText().toString();
        String text = mNoteField.getText().toString();
        mNote.setName(name);
        mNote.setText(text);
        return mNote;
    }

    public static void startActivity(AppCompatActivity context, String userId) {
        Intent launchIntent = new Intent(context, NoteEditorActivity.class);
        launchIntent.putExtra(USER_ID_KEY, userId);
        context.startActivityForResult(launchIntent, RC_NOTE);
    }

    public static void startActivity(AppCompatActivity context, String userId, Note note) {
        Intent launchIntent = new Intent(context, NoteEditorActivity.class);
        launchIntent.putExtra(NOTE_KEY, note);
        launchIntent.putExtra(USER_ID_KEY, userId);
        context.startActivityForResult(launchIntent, RC_NOTE);
    }

}
