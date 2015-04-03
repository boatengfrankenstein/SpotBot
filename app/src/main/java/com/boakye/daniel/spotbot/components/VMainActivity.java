package com.boakye.daniel.spotbot.components;

import android.app.ActionBar;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import com.boakye.daniel.spotbot.R;
import com.parse.ParseUser;


public class VMainActivity extends FragmentActivity {
    private ViewPager pager;
    private TestFragmentAdapter mTabsAdapter;
    private TextView tv;
    private PendingIntent tracking;
    private AlarmManager alarms;
    SimpleCursorAdapter adapter;
    private long UPDATE_INTERVAL = 30000;
    private int START_DELAY = 5;
    private String DEBUG_TAG = "LocationServiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pager = new ViewPager(this);
        pager.setId(R.id.pager);
        setContentView(pager);

        final ActionBar bar = getActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        mTabsAdapter = new TestFragmentAdapter(this, pager);
        mTabsAdapter.addTab(bar.newTab().setText("Friends"), Fragment1.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("List Fragment 2"), Fragment2.class, null);
        mTabsAdapter.addTab(bar.newTab().setText("List Fragment 3"), Fragment3.class, null);

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_LogOut){
            ParseUser.logOut();
            Intent intent = new Intent(this,LogInActivity.class );
            startActivity(intent);
        }


        if (itemId== R.id.edit_friends){
        Intent intent = new Intent(this, SearchFriendActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }


}
