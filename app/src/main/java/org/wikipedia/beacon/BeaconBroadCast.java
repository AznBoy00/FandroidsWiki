//Reference:
//1.https://github.com/anmolduainter/BeaconPlay
//2.https://medium.com/@anmoldua/starting-beacons-in-android-d23c8b388d35

package org.wikipedia.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


public class BeaconBroadCast extends BroadcastReceiver {

    private final String TAG = "BeaconBroadCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        //Log.e(TAG,"BeaconBroad!!!");
        // Start service again
        context.startService(new Intent(context, BeaconService.class));
    }
}
