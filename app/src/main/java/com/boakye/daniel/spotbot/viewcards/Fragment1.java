package com.boakye.daniel.spotbot.viewcards;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.SimpleCursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.boakye.daniel.spotbot.LocationHandlerMain.AlarmReceiver;
import com.boakye.daniel.spotbot.R;
import com.boakye.daniel.spotbot.UserInterface.LazyAdapter;
import com.boakye.daniel.spotbot.UserInterface.RowItem;
import com.boakye.daniel.spotbot.database.LocContentProvider;
import com.boakye.daniel.spotbot.database.LocTable;
import com.facebook.widget.ProfilePictureView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the

 * to handle interaction events.
 * Use the {@link Fragment1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment1 extends Fragment implements  LoaderManager.LoaderCallbacks<Cursor> {
    private static final String ARG_POSITION = "position";
    private List<RowItem> rowItems;
    private List<RowItem> emptyrowItems;
    private PendingIntent tracking;
    private AlarmManager alarms;
    SimpleCursorAdapter adapter;
    private long UPDATE_INTERVAL = 30000;
    private int START_DELAY = 5;
    private String DEBUG_TAG = "LocationServiceActivity";
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date = new Date();

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


    @InjectView(R.id.textView)
    TextView textView;

    private int position;

    public static Fragment1 newInstance(int position) {
        Fragment1 f = new Fragment1();
        Bundle b = new Bundle();
        b.putInt(ARG_POSITION, position);
        f.setArguments(b);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        position = getArguments().getInt(ARG_POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_fragment1,container,false);
// ButterKnife.inject(this, rootView);
        ViewCompat.setElevation(rootView, 50);
        alarms = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);
        ListView lv = (ListView) rootView.findViewById(R.id.myList);
        rowItems = new ArrayList<RowItem>();
        emptyrowItems = new ArrayList<RowItem>();

        setRecurringAlarm(getActivity().getBaseContext());
        drawTable();
    String temp_last_seen   = (dateFormat.format(date));

        ///////////////////////////////////////////
        /* Array to populate  empty list view*/

        String [] empty_name_List = {"Hey!"};
        String [] empty_description = {"You have no friends in your list"};
        String[] empty_last_seen = {temp_last_seen};
        String [] emptyuserprofilephotofromparse = {""};


        /////////////////////////////////
        String[] titles = {"Movie1","Movie2","Movie3","Movie4","Movie5","Movie6","Movie7","Movie8"};
        String[] descriptions = {"First Movie","Second movie","Third Movie","Fourth Movie","Fifth Movie",
                "Sixth Movie","Seventh Movie","Eighth Movie"};
        String[] lastSeen = {"3:45", "4:30", "4:00","3:45", "4:30", "4:00", "4:30", "4:00"};
        Bitmap dummypic = BitmapFactory.decodeResource(getActivity().getResources(),
                R.drawable.rihanna);
        Bitmap[] emptyUserPhoto = {dummypic};
        Bitmap[] userphotofromParse = {dummypic,dummypic, dummypic,dummypic,dummypic, dummypic,dummypic,dummypic};
    //    profilePictureView.setProfileId(facebook_user.getId());
ProfilePictureView f = new ProfilePictureView(getActivity()) ;
       // ProfilePictureView[] userprofilephotofromparse ={f.setDefaultProfilePicture(dummypic)};
        String [] userprofilephotofromparse = {"", "", "","", "", "", "", ""};
        for (int i = 0; i < titles.length; i++) {
            RowItem item = new RowItem(userprofilephotofromparse[i], titles[i], descriptions[i],lastSeen[i]);
            rowItems.add(item);
        }
      ////////////////////////////////////////////////////////////////////

        //E,pty Items
        for (int i=0; i<empty_name_List.length;i++) {
            RowItem empty_items = new RowItem(emptyuserprofilephotofromparse[i],empty_name_List[i],empty_description[i],empty_last_seen[i]);
             emptyrowItems.add(empty_items);
        }


        // Set the adapter on the ListView
      //  LazyAdapter adapter = new LazyAdapter(getActivity().getApplicationContext(), R.layout.list_row, rowItems);
        LazyAdapter empty_adapter = new LazyAdapter(getActivity().getApplicationContext(), R.layout.list_row, emptyrowItems);
      //  lv.setAdapter(adapter);
        lv.setAdapter(empty_adapter);

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

       // textView.setText("CARD " + position);
        return rootView;
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
    }}