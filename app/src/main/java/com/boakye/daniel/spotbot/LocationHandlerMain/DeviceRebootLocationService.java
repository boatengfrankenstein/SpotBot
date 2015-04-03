package com.boakye.daniel.spotbot.LocationHandlerMain;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Daniel on 12/30/2014.
 */
public class DeviceRebootLocationService  extends BroadcastReceiver{
    String DEBUG_TAG ="Device Rebooted";
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            Log.d(DEBUG_TAG, "Recurring alarm; requesting location tracking.");
            // start the service
            Intent tracking = new Intent(context, UpdateLocation.class);
            context.startService(tracking);
        }
    }
}
