package org.wikipedia.note;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.wikipedia.R;

import java.text.DateFormat;
import java.util.Date;


public class AddNoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private EditText mTitle;
    private EditText mDescription;

    //Database..

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;


    private ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        toolbar=findViewById(R.id.notes_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Add Note");


        mAuth= FirebaseAuth.getInstance();

        FirebaseUser mUser=mAuth.getCurrentUser();

        String uid=mUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Note").child(uid);

        mDialog=new ProgressDialog(this);

        addNote();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),NoteActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }


    private void addNote(){


        mTitle=findViewById(R.id.title);
        mDescription=findViewById(R.id.description);

        String title=mTitle.getText().toString().trim();
        String description=mDescription.getText().toString().trim();

        if (TextUtils.isEmpty(title)){
            mTitle.setError("Required Field..");
            return;
        }

        String id=mDatabase.push().getKey();

        String date= DateFormat.getDateInstance().format(new Date());
        //TODO adapt to constructor
        Note data=new Note(Integer.parseInt(id),title,description,date);
        mDatabase.child(id).setValue(data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){

            case R.id.done:

                mDialog.setMessage("Processing");
                mDialog.show();

                addNote();
                startActivity(new Intent(getApplicationContext(),NoteActivity.class));
                Toast.makeText(getApplicationContext(),"Note Added", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                break;

        }

        return super.onOptionsItemSelected(item);
    }


}
