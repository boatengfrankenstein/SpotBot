package com.boakye.daniel.spotbot.LocationHandlerMain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by Daniel on 12/30/2014.
 */
public class LocationTracker extends BroadcastReceiver {
    String TAG = "Reciever";
    @Override
    public void onReceive(Context context, Intent intent) {
   Toast toast = Toast.makeText(context,"Location changed",Toast.LENGTH_SHORT);
        Log.i(TAG, "yes");
        toast.show();

    }
}
