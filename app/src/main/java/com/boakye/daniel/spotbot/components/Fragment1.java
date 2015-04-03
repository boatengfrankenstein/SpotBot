package com.boakye.daniel.spotbot.components;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;


import com.boakye.daniel.spotbot.R;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.boakye.daniel.spotbot.LocationHandlerMain.AlarmReceiver;
import com.boakye.daniel.spotbot.UserInterface.LazyAdapter;
import com.boakye.daniel.spotbot.UserInterface.RowItem;
import com.boakye.daniel.spotbot.database.LocContentProvider;
import com.boakye.daniel.spotbot.database.LocTable;
import com.facebook.widget.ProfilePictureView;


public class Fragment1 extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor>{

    private PendingIntent tracking;
    private AlarmManager alarms;
    SimpleCursorAdapter adapter;
    private long UPDATE_INTERVAL = 30000;
    private int START_DELAY = 5;
    private String DEBUG_TAG = "LocationServiceActivity";

    private List<RowItem> rowItems;

    private static Integer[] images = {
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,
    };

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_1, container, false);
        // Intialize and set the Action Bar to Holo Blue
        alarms = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        setHasOptionsMenu(true);
        ActionBar actionBar = getActivity().getActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
     //Components.VMainActivity caller = new Components.VMainActivity();

      //  caller.setRecurringAlarm(getActivity().getBaseContext());
        ListView lv = (ListView) v.findViewById(R.id.myList);
        rowItems = new ArrayList<RowItem>();

        setRecurringAlarm(getActivity().getBaseContext());
        drawTable();
      /*  String[] titles = {"Movie1","Movie2","Movie3","Movie4","Movie5","Movie6","Movie7","Movie8"};
        String[] descriptions = {"First Movie","Second movie","Third Movie","Fourth Movie","Fifth Movie",
                "Sixth Movie","Seventh Movie","Eighth Movie"};
        String[] lastSeen = {"3:45", "4:30", "4:00","3:45", "4:30", "4:00", "4:30", "4:00"};
        Bitmap dummypic = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.rihanna);
     Bitmap[] userphotofromParse = {dummypic,dummypic, dummypic,dummypic,dummypic, dummypic,dummypic,dummypic};
        //Populate the List
        ProfilePictureView[] userprofilephotofromparse ={};
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(userprofilephotofromparse[1], titles[i], descriptions[i],lastSeen[i]);
            rowItems.add(item);
        }

        // Set the adapter on the ListView
        LazyAdapter adapter = new LazyAdapter(getActivity().getApplicationContext(), R.layout.list_row, rowItems);
        lv.setAdapter(adapter);

        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                // TODO Auto-generated method stub

            }
        });
*/
        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
    }

    public void setRecurringAlarm(Context context) {

        // get a Calendar object with current time
        Calendar cal = Calendar.getInstance();
        // add 5 minutes to the calendar object
        cal.add(Calendar.SECOND, START_DELAY);

        Intent intent = new Intent(context, AlarmReceiver.class);

        tracking = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        alarms.setInexactRepeating(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), UPDATE_INTERVAL, tracking);
    }

    public void stop(View view){
        // tv = (TextView) findViewById(R.id.tv1);
        // tv.setText("Stop tracking");
        Intent intent = new Intent(getActivity().getBaseContext(), AlarmReceiver.class);
        tracking = PendingIntent.getBroadcast(getActivity().getBaseContext(), 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
        alarms = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        alarms.cancel(tracking);
        Log.d(DEBUG_TAG, ">>>Stop tracking()");
    }

    private void drawTable() {
        // Fields from the com.boakye.daniel.spotbot.database (projection)
        // Must include the _id column for the adapter to work
        String[] from = new String[] { LocTable.COLUMN_ID, LocTable.COLUMN_TIME,
                LocTable.COLUMN_LONGITUDE, LocTable.COLUMN_LATITUDE };
        // Fields on the UI to which we map
        //  int[] to = new int[] { R.id.rowid, R.id.time, R.id.longitude, R.id.latitude };
        getActivity().getSupportLoaderManager().initLoader(0, null, this);
        // adapter = new SimpleCursorAdapter(this, R.layout.row, null, from,
        //          to, 0);
        //   ListView listview = (ListView) findViewById(R.id.list);
        //   listview.setAdapter(adapter);
        Log.i("DRAWTABLE","Draw table called");
        Log.i("LOcation",from.toString());
      //  Toast.makeText(getActivity().getBaseContext(), "Lc:" + from.toString() + " ", Toast.LENGTH_SHORT).show();
    }

    // Creates a new loader after the initLoader () call
    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] projection = { LocTable.COLUMN_ID, LocTable.COLUMN_TIME,
                LocTable.COLUMN_LONGITUDE, LocTable.COLUMN_LATITUDE };
        CursorLoader cursorLoader = new CursorLoader(getActivity(),
                LocContentProvider.CONTENT_URI, projection, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        // adapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        // data is not available anymore, delete reference
        //   adapter.swapCursor(null);
    }
}