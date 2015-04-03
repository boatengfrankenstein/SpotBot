package com.boakye.daniel.spotbot.components;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.boakye.daniel.spotbot.R;


public class SearchFriendActivity extends  Activity {
    Button gobutton;
    EditText search_;
    public String userSearchId;
    public   String finalgetsearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_search_friend);

      gobutton= (Button) findViewById(R.id.ok_go);
        search_ = (EditText) findViewById(R.id.user_search);
        search_.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                userSearchId   =  search_.getText().toString().trim();

            }
        });


        gobutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             //   Log.i("String from Edit text",userSearchId);
                Intent intent = new Intent(SearchFriendActivity.this, EditFriendsActivity.class);
               startActivity(intent);
            }
        });


    }





    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_friend, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public  String getInputText(){
     finalgetsearch =userSearchId;
        return finalgetsearch;
    }

}
