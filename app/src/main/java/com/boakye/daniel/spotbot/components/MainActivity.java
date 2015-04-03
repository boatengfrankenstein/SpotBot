package com.boakye.daniel.spotbot.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.boakye.daniel.spotbot.R;
import com.parse.Parse;
import com.parse.ParseAnalytics;
import com.parse.ParseUser;


public class MainActivity extends Activity {
    String TAG = "Who is Logged In";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.face_activity_main);
        Parse.initialize(this, "aV4oLiG745IHpmjdGotoYpfkrcidpMegxvhs6PAx",
                "K2VUItIYGQMK73RLhys2t1tymlirLeFaig16nrYG");



        ParseAnalytics.trackAppOpenedInBackground(getIntent());

        ParseUser currentUser = ParseUser.getCurrentUser();
        if (currentUser == null) {
            navigateToLogIn();
        }
        else {
            Log.i(TAG, currentUser.getUsername());
            Intent intent = new Intent(MainActivity.this, VMainActivity.class);
            startActivity(intent);
        }



    }

    private void navigateToLogIn() {
        Intent intent = new Intent(this, LogInActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_LogOut) {
            ParseUser.logOut();
            navigateToLogIn();

        }
        return super.onOptionsItemSelected(item);
    }



}
