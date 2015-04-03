package com.boakye.daniel.spotbot.FaceBookLogInAuthentication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.boakye.daniel.spotbot.R;
import com.boakye.daniel.spotbot.components.MainActivity;
import com.boakye.daniel.spotbot.viewcards.ViewCardMainActivity;
import com.facebook.Request;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.ProfilePictureView;

import com.boakye.daniel.spotbot.components.VMainActivity;
import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseUser;


/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * Activities that contain this fragment must implement the
 *  interface
 * to handle interaction events.

 * create an instance of this fragment.
 */
public class SelectionFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private ProfilePictureView profilePictureView;
    private TextView userNameView;
    private UiLifecycleHelper uiHelper;
    private static final int REAUTH_ACTIVITY_CODE = 100;
    private static final String TAG = "SelectionFragment";
    public static String facebook_username = "";


    private Session.StatusCallback callback = new Session.StatusCallback() {
        @Override
        public void call(final Session session, final SessionState state, final Exception exception) {
            onSessionStateChange(session, state, exception);
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        Log.i("Oncreatue", "uiHelper");

        Log.i("Suhu","Intent called");
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            Log.i("Sion","sehun is not null from ONcreate");
            makeMeRequest(session);

        }
        uiHelper.onCreate(savedInstanceState);

     //   Intent intent = new Intent(getActivity(),TestActivity.class);
     //   startActivity(intent);
    }
    ////////////////////////////////////

    @Override
    public void onResume() {
        super.onResume();
        uiHelper.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_selection,
                container, false);

        uiHelper = new UiLifecycleHelper(getActivity(), callback);
        uiHelper.onCreate(savedInstanceState);
        // Check for an open session
/*
        Session session = Session.getActiveSession();
        if (session != null && session.isOpened()) {
            Log.i("Session","session is not null");
            makeMeRequest(session);

        }

        */
        // Find the user's profile picture custom view
        profilePictureView = (ProfilePictureView) view.findViewById(R.id.selection_profile_pic);
        profilePictureView.setCropped(true);


// Find the user's name view
        userNameView = (TextView) view.findViewById(R.id.selection_user_name);
        return view;
    }

    private void makeMeRequest(final Session session) {
        // Make an API call to get user data and define a
        // new callback to handle the response.
        Request request = Request.newMeRequest(session,
                new Request.GraphUserCallback() {
                    @Override
                    public void onCompleted(final GraphUser facebook_user, Response response) {
                        // If the response is successful
                        if (session == Session.getActiveSession()) {
                            if (facebook_user != null) {
                                // Set the id for the ProfilePictureView
                                // view that in turn displays the profile picture.
                                profilePictureView.setProfileId(facebook_user.getId());
                                facebook_username = facebook_user.getId();
                                // Set the Textview's text to the user's name.
                                userNameView.setText(facebook_user.getName());


                                ParseFacebookUtils.logIn(getActivity(), new LogInCallback() {
                                    @Override
                                    public void done(ParseUser user, ParseException err) {
                                        if (user == null) {

                                            Log.d("MyApp", "Uh oh. The user cancelled the Facebook login.");
                                        } else if (user.isNew()) {
                                            user.setUsername(facebook_user.getName().toString());


                                            user.saveInBackground();
                                            Log.d("MyApp", "User signed up and logged in through Facebook!");
                                        } else {
                                            user.put("facebookusername",facebook_user.getFirstName());
                                            user.saveInBackground();
                                            Log.d("MyApp", "User logged in through Facebook!");
                                        }
                                    }
                                });
                            }
                        }
                        if (response.getError() != null) {
                            // Handle errors, will do so later.
                        }
                    }
                });
        request.executeAsync();
    }

    private void onSessionStateChange(final Session session, SessionState state, Exception exception) {
        if (session != null && session.isOpened()) {
            // Get the user's data.
            makeMeRequest(session);
            Log.i("OnSessionState","on session state called");
           // Intent intent = new Intent(getActivity(), TestActivity.class);
           // startActivity(intent);

            splash();
        }
    }




    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REAUTH_ACTIVITY_CODE) {
            uiHelper.onActivityResult(requestCode, resultCode, data);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        uiHelper.onSaveInstanceState(bundle);
    }

    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }

   public void splash(){
       Thread background = new Thread() {
           public void run() {

               try {
                   // Thread will sleep for 5 seconds
                   sleep(10*1000);

                   // After 5 seconds redirect to another intent
                   Intent i=new Intent(getActivity(), ViewCardMainActivity.class);

                   startActivity(i);
                   Log.i("Obomo","MainActivitty Called");


               } catch (Exception e) {

               }
           }
       };

       // start thread
       background.start();


   }


    public void ParseLogin(){

    }
   }

