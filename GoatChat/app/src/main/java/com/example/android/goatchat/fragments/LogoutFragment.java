package com.example.android.goatchat.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.R;
import com.example.android.goatchat.activity.MainActivity;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by mz on 12/29/16.
 */

public class LogoutFragment extends Fragment {
    Activity activity;


//    TODO: Something about having activity implement an interface???
//    http://stackoverflow.com/questions/14354279/call-parents-activity-from-a-fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//      Retain a reference to the parent Activity.
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "creating logout view fragment");
        View view = inflater.inflate(R.layout.logout_view, container, false);
        return view;
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(activity, MainActivity.class);
        if(isFacebookUser()){
            //TODo logout of fb
        }
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "Logging out");
    }

    public boolean isFacebookUser()
    {
        return false;  //TODO check if is fb uses
    }

}
