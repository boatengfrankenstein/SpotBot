package com.boakye.daniel.spotbot.viewcards;

import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.boakye.daniel.spotbot.R;
import com.boakye.daniel.spotbot.components.MyDialogFragment;

import butterknife.ButterKnife;


/**
 * Created by Daniel on 2/26/2015.
 */
public class FriendQuickFragment extends Fragment {

    private int position;
    private static final String ARG_POSITION = "position";


    public static FriendQuickFragment newInstance(int position) {
        FriendQuickFragment f = new FriendQuickFragment();
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
       final View rootView = inflater.inflate(R.layout.friendsquickfragment, container, false);
 ButterKnife.inject(this, rootView);
       // ViewCompat.setElevation(rootView, 50);

        return  rootView;
    }


}