package com.boakye.daniel.spotbot.components;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.boakye.daniel.spotbot.R;


import com.boakye.daniel.spotbot.mapview.MapActivity;
import com.boakye.daniel.spotbot.viewcards.*;
import com.boakye.daniel.spotbot.viewcards.Fragment1;
import com.facebook.widget.ProfilePictureView;
import com.parse.FindCallback;
import com.parse.GetDataCallback;
import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.boakye.daniel.spotbot.UserInterface.LazyAdapter;
import com.boakye.daniel.spotbot.UserInterface.RowItem;

public class MyDialogFragment extends DialogFragment implements
        OnItemClickListener {
    String KEY_TEXTPSS = "TEXTPSS";
    protected List<ParseUser> mUsers;
    public static  String  querriedUserLocation;


    private static Integer[] images = {
            R.drawable.rihanna,
            R.drawable.rihanna,
            R.drawable.rihanna,

    };
    //private static Bitmap[] userphotofromParse = {};
    static final int CUSTOM_DIALOG_ID = 0;

    //ListView dialog_ListView;
    ListView lv;

    String[] listContent = {
            "January", "February", "March", "April",
            "May", "June", "July", "August", "September",
            "October", "November", "December"};
    private List<RowItem> rowItems;

    Bitmap bitmap;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_fragment, container, false);
        lv= (ListView) view.findViewById(R.id.list);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);

        ParseQuery<ParseUser> query = ParseUser.getQuery();
        query.orderByAscending(ParseConstants.KEY_USERNAME);
        Log.i("Text before execu", QuickFindFriendsMenu.searchNameGottenFromUser);
        query.whereEqualTo(ParseConstants.FACEBOOKUSERNAME, QuickFindFriendsMenu.searchNameGottenFromUser);

        query.setLimit(10);
        query.findInBackground(new FindCallback<ParseUser>() {
            @Override
            public void done(List<ParseUser> parseUsers, ParseException e) {
                if (e == null) {

                    mUsers = parseUsers;
                    Log.i("parseUsers",parseUsers.toString());
                    rowItems = new ArrayList<RowItem>();
                    String[] usernames = new String[mUsers.size()];
                    String[] titles = {"Movie1", "Movie2", "Movie3"};
                    String[] descriptions = {"First Movie", "Second movie", "Third Movie"};
                    String[] facebookprofileId ={"","",""};
                  //  ParseUser userg = new ParseUser();
                 //   ParseFile[] imagefromparse = {userg.getParseFile(Components.ParseConstants.Current_USER_PHOTO),userg.getParseFile(Components.ParseConstants.Current_USER_PHOTO)};
                    Bitmap dummypic = BitmapFactory.decodeResource(getActivity().getResources(),
                            R.drawable.rihanna);
                    String[] timelastseen = {"3:45","4:00","3:00"};
                  Bitmap[] userphotofromParse = {dummypic,dummypic, dummypic,dummypic,dummypic, dummypic,dummypic,dummypic};
                    //Populate the List
                    int j = 0;
                    for (ParseUser user : mUsers) {
                        usernames[j] = user.getString(ParseConstants.FACEBOOKUSERNAME);
                        titles[j] = user.getString("CurrentLocation");
                        timelastseen[j]= user.getString("LastSeen");
                        facebookprofileId[j]= user.getString("ProfileID");


                     //   Log.i("user.getString", user.getString("CurrentLocation").toString());

                       // Log.i("usernams from parse",user.getUsername());
                        String[] userprofilephotofromparse ={""};

                        RowItem item = new RowItem( facebookprofileId[j], usernames[j],titles[j],timelastseen[j]);
                        rowItems.add(item);

                        j++;
                    }
                    // Success
                    if (usernames.length == 0){
                      // Toast.makeText(getActivity().getBaseContext(), "No User Found",Toast.LENGTH_SHORT).show();
                        //Log.e("Error from dialog", e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("No User exist with that ID")
                                .setTitle(R.string.errorTitle)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();
                    }

                    else {
                        Log.i("usernames", "contains something" + usernames);


                        LazyAdapter adapter = new LazyAdapter(getActivity().getApplicationContext(), R.layout.list_row, rowItems);

                        lv.setAdapter(adapter);
                      lv.setOnItemClickListener(new OnItemClickListener() {
                          @Override
                          public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                              Log.i("Frytol", "List view clicked");
                          Intent i = new Intent(getActivity(), MapActivity.class);
                              startActivity(i);
                              dismiss();
                          }
                      });


                        lv.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {


                            @Override
                            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                                       int arg2, long arg3) {
                                // TODO Auto-generated method stub
                                Log.i("Zumba", "List Item Called");

                                com.boakye.daniel.spotbot.viewcards.Fragment1.newInstance(1);

                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> arg0) {
                                // TODO Auto-generated method stub

                            }
                        });

                    }

                }
                else {
                    Log.e("Error from dialog", e.getMessage());
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage(e.getMessage())
                            .setTitle(R.string.errorTitle)
                            .setPositiveButton(android.R.string.ok, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

    }

    private Bitmap resize(Bitmap bp, int witdh, int height){
        return Bitmap.createScaledBitmap(bp, witdh, height, false);
    }

    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}