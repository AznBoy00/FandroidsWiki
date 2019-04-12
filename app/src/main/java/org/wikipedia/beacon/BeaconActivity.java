//Reference:
//1.https://github.com/anmolduainter/BeaconPlay
//2.https://medium.com/@anmoldua/starting-beacons-in-android-d23c8b388d35

package org.wikipedia.beacon;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import org.wikipedia.R;

import java.util.ArrayList;
import java.util.List;

public class BeaconActivity extends AppCompatActivity {

    // ToolBar
    private Toolbar toolbar;

    // TabLayout
    private TabLayout tabLayout;

    // ViewPager
    private ViewPager viewPager;

    private final String TAG = "BeaconActivity";


    private static final int PERMISSION_REQUEST_COARSE_LOCATION = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Checking Permission whether ACCESS_COARSE_LOCATION permssion is granted or not
        checkPermission();

        // Setting up of view
        setContentView(R.layout.activity_beacon);

        // Intializing of Layout Views
        initializedLayout();

    }


    //Intializing of Layout Views
    public void initializedLayout(){
        //Log.e(TAG,"initializedLayout");
        // Setting up of customized toolbar
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Setting up of View Pager
        viewPager = findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        //Setting up of TabLayout
        tabLayout = findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }


    // Checking Permission whether ACCESS_COARSE_LOCATION permssion is granted or not
    public void checkPermission(){
        //Log.e(TAG,"checkPermission");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (this.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION)!= PackageManager.PERMISSION_GRANTED){
                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("Location Permission");
                builder.setPositiveButton("OK",null);
                builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @TargetApi(Build.VERSION_CODES.M)
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION},PERMISSION_REQUEST_COARSE_LOCATION);
                    }
                });
                builder.show();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_COARSE_LOCATION: {

                // If Permission is Granted than its ok
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                }

                // If not Granted then alert the user by the message
                else {
                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setTitle("Functionality limited");
                    builder.setMessage("Since location access has not been granted, this app will not be able to discover beacons when in the background.");
                    builder.setPositiveButton(android.R.string.ok, null);
                    builder.setOnDismissListener(new DialogInterface.OnDismissListener() {
                        @Override
                        public void onDismiss(DialogInterface dialog) {
                            checkPermission();
                        }
                    });
                    builder.show();
                }
                return;
            }
        }
    }

    /*
       Setting up of View Pager
       2 Fragments :
       -> BeaconSearc() == Search
       -> BeaconSimu() == Simulator
     */
    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BeaconSearc() , "Search");
        // can add simulator for friend add feature
        viewPager.setAdapter(adapter);
    }


    // Inner class Setting up the ViewPagerAdapter
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}
