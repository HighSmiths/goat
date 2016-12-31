package com.satyrlabs.android.goatchat.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.callback.GetUsersCallback;
import com.satyrlabs.android.goatchat.models.User;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Map;

/**
 * Created by mz on 12/30/16.
 */

public class AddFriendsFragment extends Fragment {
    AppCompatActivity activity;


    //    TODO: Something about having activity implement an interface???
//    http://stackoverflow.com/questions/14354279/call-parents-activity-from-a-fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//      Retain a reference to the parent Activity.
        this.activity = (AppCompatActivity) activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "creating logout view fragment");
        View view = inflater.inflate(R.layout.activity_search_username, container, false);

        Toolbar toolbar = (Toolbar) activity.findViewById(R.id.toolbar);
        activity.setSupportActionBar(toolbar);

        final TextView textView = (TextView) view.findViewById(R.id.fID);
        final Button button = (Button) view.findViewById(R.id.addFriend);
        SearchView searchView = (SearchView) view.findViewById(R.id.searchbar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener(){
            @Override
            public boolean onQueryTextSubmit(String s) {
                Log.d(Constants.LOG_TAG, "onQueryTextSubmit ");

                class helper implements GetUsersCallback {
                    @Override
                    public void execute(Map<String, User> users, boolean success) {
                        if (success) {
                            Log.d(Constants.LOG_TAG,users.toString());
                            for (User user : users.values()) {
                                textView.setText(user.username);
                            }
                            for (User user : users.values()) {
                                textView.setText(user.username);
                            }
                            for (User user : users.values()) {
                                final User finUser = user;
                                button.setOnClickListener(new View.OnClickListener() {
                                    public void onClick(View v) {
                                        Log.d("clicked", "friendbutton");
                                        Log.d(Constants.LOG_TAG, FirebaseAuth.getInstance().getCurrentUser().getUid() + "," + finUser.uid);
                                        //TODO fix temp message id
                                        Database.instance.addFriendForUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), finUser.uid);
                                    }
                                });
                            }
                        } else {
                            Toast.makeText(activity.getBaseContext(), Constants.USER_DOESNT_EXIST, Toast.LENGTH_SHORT).show();

                        }

                    }
                }
                Database.instance.getUserWithUsername(s,new helper());
                return false;
            }


            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }

        });



        return view;
    }



}
