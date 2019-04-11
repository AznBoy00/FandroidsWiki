package org.wikipedia.beacon;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.wikipedia.R;

import java.util.ArrayList;

public class RecyclerAdapter  extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {
    private final String TAG = "RECYCLER_ADAPTER";
    ArrayList<ArrayList<String>> arr;
    private DatabaseReference dbRef;

    // Constructor
    public RecyclerAdapter(ArrayList<ArrayList<String>> arr)
    {
        this.arr = arr;
        dbRef = FirebaseDatabase.getInstance().getReference().child("beacons");
    }

    /*
       View Holder class to instantiate views
     */
    class ViewHolder extends RecyclerView.ViewHolder{

        //Wiki spot
        private TextView wikiSpot;

        //UUID
        private TextView uuid;

        //Major
        private TextView major;

        //Minor
        private TextView minor;

        //Distance
        private TextView distance;

        //View Holder Class Constructor
        public ViewHolder(View itemView)
        {
            super(itemView);

            //Initializing views
            wikiSpot = itemView.findViewById(R.id.wiki_spot_name);
            uuid = itemView.findViewById(R.id.uuid);
//            major = itemView.findViewById(R.id.major);
//            minor = itemView.findViewById(R.id.minor);
            distance = itemView.findViewById(R.id.distance);
        }
    }


    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_card_search,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

        // Getting Array List within respective position
        ArrayList<String> arrayList = arr.get(position);

        Log.e(""+TAG," array_size " + arrayList.size());
        // Checking if arrayList size > 0
        if (arrayList.size()>0){

            String _uuid = arrayList.get(0);
            // Displaying UUID
            holder.uuid.setText(_uuid);
            Log.e(""+TAG,"" + _uuid);
//            dbRef.addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if (dataSnapshot.hasChild(""+_uuid)) {
//                        final String wikiSpotName = dataSnapshot.child(""+_uuid).child("name").getValue().toString();
//                        holder.wikiSpot.setText(wikiSpotName);
//                    }else {
//                        holder.wikiSpot.setText("Non-Wiki Spot");
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                }
//            });

            //Displaying distance
            holder.distance.setText(arrayList.get(3));

            holder.wikiSpot.setText(arrayList.get(4));
            Log.e(""+TAG,"" + holder.wikiSpot.getText().toString());
        }
    }
    @Override
    public int getItemCount()
    {
        return arr.size();
    }
}
