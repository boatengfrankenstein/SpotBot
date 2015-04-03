package com.boakye.daniel.spotbot.LocationHandlerMain;

import android.app.Service;
import android.content.ContentValues;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;


import com.boakye.daniel.spotbot.FaceBookLogInAuthentication.SelectionFragment;
import com.parse.ParseObject;
import com.parse.ParseUser;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import com.boakye.daniel.spotbot.components.ParseConstants;
import com.boakye.daniel.spotbot.components.SginUpActivity;

public class UpdateLocation extends Service implements
        LocationListener {


    ParseObject currentUserLocation = new ParseObject(ParseConstants.USER_LOCATION);
    public static String locationAddress;
    SimpleDateFormat f = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");

  //  SginUpActivity getUser = new SginUpActivity();
  private Looper mServiceLooper;
  private ServiceHandler mServiceHandler;
  private final String DEBUG_TAG = "UpdateLocation::Service";
  private LocationManager mgr;
  private String best;
   private static String CURRENT_LOCATION ="Current Location";

   public static double longitude;
   public static double latitude;
   public static String time;
  // Handler that receives messages from the thread
  private final class ServiceHandler extends Handler {
      public ServiceHandler(Looper looper) {
          super(looper);
      }
      
      @Override
      public void handleMessage(Message msg) {
          Location location = mgr.getLastKnownLocation(best);
          mServiceHandler.post(new MakeToast(trackLocation(location)));
          // Stop the service using the startId, so that we don't stop
          // the service in the middle of handling another job
          stopSelf(msg.arg1);
      }
  }

  @Override
  public void onCreate() {
    // Start up the thread running the service.  Note that we create a
    // separate thread because the service normally runs in the process's
    // main thread, which we don't want to block.  We also make it
    // background priority so CPU-intensive work will not disrupt our UI.
    HandlerThread thread = new HandlerThread("ServiceStartArguments",
    		android.os.Process.THREAD_PRIORITY_BACKGROUND);
    thread.start();
    Log.d(DEBUG_TAG, ">>>onCreate()");
    // Get the HandlerThread's Looper and use it for our Handler 
    mServiceLooper = thread.getLooper();
    mServiceHandler = new ServiceHandler(mServiceLooper);
	mgr = (LocationManager) getSystemService(LOCATION_SERVICE);
    Criteria criteria = new Criteria();
    best = mgr.getBestProvider(criteria, true);
    mgr.requestLocationUpdates(best, 15000, 1, this);
  }

  @Override
  public int onStartCommand(Intent intent, int flags, int startId) {
//      Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();

      // For each start request, send a message to start a job and deliver the
      // start ID so we know which request we're stopping when we finish the job
      Message msg = mServiceHandler.obtainMessage();
      msg.arg1 = startId;
      mServiceHandler.sendMessage(msg);
      Log.d(DEBUG_TAG, ">>>onStartCommand()");
      // If we get killed, after returning from here, restart
      return START_STICKY;
  }

  @Override
  public IBinder onBind(Intent intent) {
      // We don't provide binding, so return null
      return null;
  }
  
  @Override
  public void onDestroy() {
//    Toast.makeText(this, "service done", Toast.LENGTH_SHORT).show(); 
	  Log.d(DEBUG_TAG, ">>>onDestroy()");
  }

  //obtain current location, insert into com.boakye.daniel.spotbot.database and make toast notification on screen
  private String trackLocation(Location location) {

	  String result = "Location currently unavailable.";
		
	  // Insert a new record into the Events data source.
	  // You would do something similar for delete and update. 
	  if (location != null)
	  {
  		longitude = location.getLongitude();
  		latitude = location.getLatitude();
  		time = parseTime(location.getTime());
  		ContentValues values = new ContentValues();
  		//values.put(LocTable.COLUMN_TIME, time);
  		//values.put(LocTable.COLUMN_LATITUDE, latitude);
  		//values.put(LocTable.COLUMN_LONGITUDE, longitude);
  		//getContentResolver().insert(LocContentProvider.CONTENT_URI, values);
  		result = "Locality: " + Double.toString(longitude)+", "+ Double.toString(latitude);
          Log.i("UPDATE LOCATION",result+" "+time);
          Toast.makeText(getBaseContext(),result+" "+time,Toast.LENGTH_SHORT);

          ReverseGeoCoding.getAddressFromLocation(latitude,longitude,getApplicationContext(),new GeocoderHandler());
	  }
		return result;
  }
	
	private String parseTime(long t) {
		DateFormat df = DateFormat.getTimeInstance(DateFormat.MEDIUM);
		df.setTimeZone(TimeZone.getTimeZone("GMT-4"));
		String gmtTime = df.format(t);
		return gmtTime;
	}
	
  private class MakeToast implements Runnable {
		String txt;
		
		public MakeToast(String text){
		    txt = text;
		}
		public void run(){
		     Toast.makeText(getApplicationContext(), txt, Toast.LENGTH_SHORT).show();
		}
  }
  
	@Override
	public void onLocationChanged(Location location) {
//		mHandler.post(new MakeToast(trackLocation(location)));
	}
	
	@Override
	public void onProviderDisabled(String provider) {
		Log.w(DEBUG_TAG, ">>>provider disabled: " + provider);
	}


	@Override
	public void onProviderEnabled(String provider) {
		Log.w(DEBUG_TAG, ">>>provider enabled: " + provider);
	}


	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		Log.w(DEBUG_TAG, ">>>provider status changed: " + provider);
	}

    private class GeocoderHandler extends Handler {
        @Override
        public void handleMessage(Message message) {

            switch (message.what) {
                case 1:
                    Bundle bundle = message.getData();
                    locationAddress = bundle.getString("address");
                    break;
                default:
                    locationAddress = null;
            }
            Toast.makeText(getBaseContext(), locationAddress, Toast.LENGTH_SHORT);
            Log.i("Real Location", locationAddress);
            if (ParseUser.getCurrentUser()!=null) {
                ParseUser.getCurrentUser().put(ParseConstants.user_is_here, locationAddress);
                ParseUser.getCurrentUser().put("ProfileID", SelectionFragment.facebook_username);
                ParseUser.getCurrentUser().saveEventually();


                f.setTimeZone(TimeZone.getTimeZone("EST"));
                ParseUser.getCurrentUser().put(ParseConstants.LastKnownLocation, f.format(new Date()));
                ParseUser.getCurrentUser().saveEventually();
                Log.i("Save sucess?", "yes");

            }
            // Place this as a global variable outside your onCreate method

// This should go in your switch statement when you receive the input as to the rating. This piece of code may not work perfectly, but should give you an idea of the structure. You want to get the actual item from the com.boakye.daniel.spotbot.database, update the value in the column, and then save it.

        }
    }
}