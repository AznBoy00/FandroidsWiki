package org.wikipedia.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Date;

public class EditActivity extends AppCompatActivity {

    private EditText mtitle;
    private EditText mdescription;

    private FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private String title;
    private String desc;
    private String post_key;

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        toolbar=findViewById(R.id.edt_note);
        setSupportActionBar(toolbar);

        mtitle=findViewById(R.id.edit_title);
        mdescription=findViewById(R.id.edit_description);

        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("Note").child(uid);


        Intent intent=getIntent();

        title=intent.getStringExtra("title");
        desc=intent.getStringExtra("description");
        post_key=intent.getStringExtra("key");

        mtitle.setText(title);
        mtitle.setSelection(title.length());

        mdescription.setText(desc);
        mdescription.setSelection(desc.length());


    }

    private void saveEditNote(){

        title=mtitle.getText().toString().trim();
        desc=mdescription.getText().toString().trim();

        String date= DateFormat.getDateInstance().format(new Date());
        Note data=new Note(title,desc,date,post_key);
        mDatabase.child(post_key).setValue(data);

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(getApplicationContext(),NoteActivity.class));
        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.done_Edt:
                saveEditNote();
                startActivity(new Intent(getApplicationContext(),NoteActivity.class));
                Toast.makeText(getApplicationContext(),"Note Edited", Toast.LENGTH_SHORT).show();
                overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
