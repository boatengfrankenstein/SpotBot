package com.boakye.daniel.spotbot.components;

import android.app.ActionBar;
import android.app.Activity;


import android.app.FragmentManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.boakye.daniel.spotbot.R;

import com.parse.ParseUser;


import java.util.List;

import com.boakye.daniel.spotbot.UserInterface.RowItem;


public class EditFriendsActivity extends Activity {
    Button buttonOpenDialog;

    String KEY_TEXTPSS = "TEXTPSS";
    static final int CUSTOM_DIALOG_ID = 0;

    ListView dialog_ListView;

    String[] listContent = {
            "January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private List<RowItem> rowItems;
    EditText userEntersSearchName;
  public static  String searchNameGottenFromUser;

    public static final String TAG = EditFriendsActivity.class.getSimpleName();


    protected List<ParseUser> mUsers;


    private static Integer[] images = {
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_friends);



        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#33b5e5")));
        }

        userEntersSearchName = (EditText) findViewById(R.id.otherFriendsId);
        userEntersSearchName.setSingleLine();


        userEntersSearchName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                searchNameGottenFromUser = userEntersSearchName.getText().toString();
                Log.i("Usertext",searchNameGottenFromUser);
                if(actionId == EditorInfo.IME_ACTION_DONE){
                //querryParse(searchNameGottenFromUser);
                }
                return false;
            }
        });

        buttonOpenDialog = (Button)findViewById(R.id.show_dialog);
        buttonOpenDialog.setOnClickListener(new Button.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                searchNameGottenFromUser = userEntersSearchName.getText().toString();
                Log.i("Usertext",searchNameGottenFromUser);

                  showDialog();
                Log.i("showdialog","called");
                //onCreateDialog(CUSTOM_DIALOG_ID);
            }});



        // Set the adapter on the ListView

    }
    @Override
    protected void onResume() {
        super.onResume();




                }


   /* public void querryParse (String searchNameGottenFromUser){
        ///////////////////////////////////////////////////////


        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending(Components.ParseConstants.KEY_USERNAME);
        Log.i("Text before execution of querry", searchNameGottenFromUser);
        query.whereEqualTo(Components.ParseConstants.KEY_USERNAME,searchNameGottenFromUser );
//        Log.i("Word",word);
        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                setProgressBarIndeterminateVisibility(false);

                if (e == null) {

                    mUsers = parseUsers;
                    rowItems = new ArrayList<RowItem>();
                    String[] usernames = new String[mUsers.size()];
                    String[] titles = {"Movie1","Movie2","Movie3"};
                    String[] descriptions = {"First Movie","Second movie","Third Movie"};
                    //Populate the List
                    int j = 0;
                    for(ParseUser user : mUsers) {
                        usernames[j] = user.getUsername();

                        RowItem item = new RowItem(images[j], titles[j], usernames[j]);
                        rowItems.add(item);

                        j++;
                    }
                    // Success





                    ListView lv = (ListView) findViewById(R.id.edit_myfriendsList);
                    LazyAdapter adapter = new LazyAdapter(getApplicationContext(), R.layout.list_row, rowItems);
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
                }
                else {
                    Log.e(TAG, e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(Components.EditFriendsActivity.this);
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.errorTitle)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });


        */






    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_friends, menu);
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


    public void showDialog() {

        FragmentManager manager = getFragmentManager();

        MyDialogFragment dialog = new MyDialogFragment();
        dialog.show(manager, "dialog");

    }
}
