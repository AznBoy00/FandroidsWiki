package org.wikipedia.note;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import org.wikipedia.R;


public class NoteActivity extends AppCompatActivity {

    private Toolbar toolbar;

    private FloatingActionButton fbBtn;


    //Firebase

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        toolbar=findViewById(R.id.toolbar_note);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Note Taker");


        mAuth= FirebaseAuth.getInstance();
        FirebaseUser mUser=mAuth.getCurrentUser();
        String uid=mUser.getUid();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("Note").child(uid);

        mDatabase.keepSynced(true);

        mRecyclerView=findViewById(R.id.recyclerview);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);

        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        fbBtn=findViewById(R.id.flot_btn);

        fbBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),AddNoteActivity.class));
               overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerOptions<Note> options =
                new FirebaseRecyclerOptions.Builder<Note>()
                        .setQuery(mDatabase, Note.class)
                        .build();

        FirebaseRecyclerAdapter firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Note, MyViewHolder>(options) {
            @Override
            public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_data, parent, false);

                return new MyViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder viewHolder, int position, @NonNull final Note model) {
                // Bind the image_details object to the BlogViewHolder
                final String post_key=getRef(position).getKey();

                viewHolder.setTitle(model.getNoteTitle());
                viewHolder.setDescription(model.getNoteContent() );
                viewHolder.setDate(model.getCreatedTime());

                viewHolder.myview.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        Intent intent=new Intent(getApplicationContext(),DetailsActivity.class);

                        intent.putExtra("key",post_key);
                        intent.putExtra("date",model.getCreatedTime());
                        intent.putExtra("title",model.getNoteTitle());
                        intent.putExtra("description",model.getNoteContent() );
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);


                    }
                });
            }
        };



        firebaseRecyclerAdapter.startListening();
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);

    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        View myview;

        public MyViewHolder(View itemView) {
            super(itemView);
            myview=itemView;
        }

        public void setTitle(String title){
            TextView mtitle=myview.findViewById(R.id.title_xml);
            mtitle.setText(title);
        }


        public void setDescription(String description){

            TextView mdescription=myview.findViewById(R.id.description);
            mdescription.setText(description);

        }

        public void setDate(String date){
            TextView mDate=myview.findViewById(R.id.date_xml);
            mDate.setText(date);

        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:

                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                break;

        }
        return super.onOptionsItemSelected(item);
    }



}
