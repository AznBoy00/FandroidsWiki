package org.wikipedia.beacon;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


public class BeaconBroadCast extends BroadcastReceiver {

    private final String TAG = "BeaconBroadCast";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.e(TAG,"BeaconBroad!!!");
        // Start service again
        context.startService(new Intent(context, BeaconService.class));
    }
}
