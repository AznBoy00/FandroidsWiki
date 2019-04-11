package org.wikipedia.beacon;

import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.altbeacon.beacon.Beacon;
import org.altbeacon.beacon.BeaconConsumer;
import org.altbeacon.beacon.BeaconManager;
import org.altbeacon.beacon.BeaconParser;
import org.altbeacon.beacon.MonitorNotifier;
import org.altbeacon.beacon.RangeNotifier;
import org.altbeacon.beacon.Region;
import org.wikipedia.R;
import org.wikipedia.main.MainActivity;

import java.util.ArrayList;
import java.util.Collection;

public class BeaconSearc extends Fragment implements BeaconConsumer {
    //Relative Layout
    RelativeLayout rl;
    //Recycler View
    private RecyclerView rv;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    //Beacon Manager
    private BeaconManager beaconManager;
    // Progress bar
    private ProgressBar pb;

    private DatabaseReference dbRef;

    private final String TAG = "BeaconSearc";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbRef = FirebaseDatabase.getInstance().getReference().child("beacons");
        Log.e(TAG, "onCreate");
        //getting beaconManager instance (object) for Beacon Activity class
        beaconManager = BeaconManager.getInstanceForApplication(getActivity());

        // To detect proprietary beacons, you must add a line like below corresponding to your beacon
        // type.  Do a web search for "setBeaconLayout" to get the proper expression.
        beaconManager.getBeaconParsers().add(new BeaconParser().
                setBeaconLayout("m:2-3=0215,i:4-19,i:20-21,i:22-23,p:24-24"));

        //Binding BeaconActivity to the BeaconService.
        beaconManager.bind(this);

        Intent tempintent = new Intent(getActivity(),BeaconService.class);
        getContext().startService(tempintent);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.beacon_search, container, false);

        // Intializing the Layout

        //Relative Layout
        rl = v.findViewById(R.id.Relative_One);

        // Recycler View
        rv = v.findViewById(R.id.search_recycler);

        //Progress Bar
        pb = v.findViewById(R.id.pb);
        return v;
    }


    @Override
    public void onBeaconServiceConnect() {

        //Constructing a new Region object to be used for Ranging or Monitoring
        final Region region = new Region("myBeaons", null, null, null);

        //Specifies a class that should be called each time the BeaconService sees or stops seeing a Region of beacons.
        beaconManager.addMonitorNotifier(new MonitorNotifier() {

            /*
                This override method is runned when some beacon will come under the range of device.
            */
            @Override
            public void didEnterRegion(Region region) {
                Log.e(TAG, "didEnterRegion");
                try {

                    //Tells the BeaconService to start looking for beacons that match the passed Region object
                    // , and providing updates on the estimated mDistance every seconds while beacons in the Region
                    // are visible.
                    beaconManager.startRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            /*
                 This override method is runned when beacon that comes in the range of device
                 ,now been exited from the range of device.
             */
            @Override
            public void didExitRegion(Region region) {
                Log.e(TAG, "didExitRegion");
                try {

                    //Tells the BeaconService to stop looking for beacons
                    // that match the passed Region object and providing mDistance
                    // information for them.
                    beaconManager.stopRangingBeaconsInRegion(region);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }


            /*
                 This override method will Determine the state for the device , whether device is in range
               of beacon or not , if yes then i = 1 and if no then i = 0
            */
            @Override
            public void didDetermineStateForRegion(int state, Region region) {
                Log.e(TAG, "I have just switched from seeing/not seeing beacons: " + state);
            }
        });


        //Specifies a class that should be called each time the BeaconService gets ranging data,
        // which is nominally once per second when beacons are detected.
        beaconManager.addRangeNotifier(new RangeNotifier() {

            /*
               This Override method tells us all the collections of beacons and their details that
               are detected within the range by device
             */
            @Override
            public void didRangeBeaconsInRegion(Collection<Beacon> beacons, Region region) {

                // Checking if the Beacon inside the collection (ex. list) is there or not
                Log.e(TAG, "size : " + beacons.size());
                // if Beacon is detected then size of collection is > 0
                if (beacons.size() > 0) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Make ProgressBar Invisible
                                pb.setVisibility(View.INVISIBLE);

                                // Make Relative Layout to be Gone
                                rl.setVisibility(View.GONE);

                                //Make RecyclerView to be visible
                                rv.setVisibility(View.VISIBLE);

                                // Setting up the layout manager to be linear
                                layoutManager = new LinearLayoutManager(getActivity());
                                rv.setLayoutManager(layoutManager);
                            }
                        });
                    } catch (Exception e) {

                    }
                    final ArrayList<ArrayList<String>> arrayList = new ArrayList<ArrayList<String>>();

                    // Iterating through all Beacons from Collection of Beacons
                    for (Beacon b : beacons) {

                        //UUID
                        String uuid = String.valueOf(b.getId1());

                        //Major
                        String major = String.valueOf(b.getId2());

                        //Minor
                        String minor = String.valueOf(b.getId3());

                        //Distance
                        double distance1 = b.getDistance();
                        String distance = String.valueOf(Math.round(distance1 * 100.0) / 100.0);

                        ArrayList<String> arr = new ArrayList<String>();
                        arr.add(uuid);
                        arr.add(major);
                        arr.add(minor);
                        arr.add(distance + " meters");

                        String wikiSpotName = "Non-Wiki Spot";
                        arr.add(wikiSpotName);
                        ValueEventListener eventListener = new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.hasChild(""+ uuid)) {
                                    String log = "beacon inside searc ";
                                    final String _wikiSpotName = dataSnapshot.child(""+ uuid).child("name").getValue().toString();
                                    Log.e(""+log," arr size "+arr.size());
                                    arr.set(4,_wikiSpotName);
                                    Log.e(""+log," arr at 4  "+arr.get(4));
                                }
//                                else {
//                                    final String wikiSpotName = "Non-Wiki Spot";
//                                    arr.add(wikiSpotName);
//                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        };
                        dbRef.addListenerForSingleValueEvent(eventListener);
                        arrayList.add(arr);
                        dbRef.removeEventListener(eventListener);

                    }
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Setting Up the Adapter for Recycler View
                                adapter = new RecyclerAdapter(arrayList);
                                ((RecyclerAdapter) adapter).setContext(getContext());
                                rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        });
                    } catch (Exception e) {

                    }
                }


                // if Beacon is not detected then size of collection is = 0
                else if (beacons.size() == 0) {
                    try {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                // Setting Progress Bar InVisible
                                pb.setVisibility(View.INVISIBLE);

                                // Setting RelativeLayout to be Visible
                                rl.setVisibility(View.VISIBLE);

                                // Setting RecyclerView to be Gone
                                rv.setVisibility(View.GONE);
                            }
                        });
                    } catch (Exception e) {

                    }
                }
            }
        });
        try {

            //Tells the BeaconService to start looking for beacons that match the passed Region object.
            beaconManager.startMonitoringBeaconsInRegion(region);
        } catch (RemoteException e) {
        }
    }


    /*
         If we are implementing the BeaconConsumer interface in a Fragment
        (and not an Activity, Service or Application instance),
         we need to chain all of the methods.
     */
    @Override
    public Context getApplicationContext() {
        return getActivity().getApplicationContext();
    }

    @Override
    public void unbindService(ServiceConnection serviceConnection) {
        getActivity().unbindService(serviceConnection);
    }

    @Override
    public boolean bindService(Intent intent, ServiceConnection serviceConnection, int i) {
        return getActivity().bindService(intent, serviceConnection, i);
    }


    // Override onDestroy Method
    @Override
    public void onDestroy() {
        super.onDestroy();
        //Unbinds an Android Activity or Service to the BeaconService to avoid leak.
        beaconManager.unbind(this);
    }



}
