package com.example.android.goatchat.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.Database;
import com.example.android.goatchat.R;
import com.example.android.goatchat.activity.FriendListActivity;
import com.example.android.goatchat.callback.GetFriendsCallback;
import com.example.android.goatchat.models.Friend;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/30/16.
 */

public class FriendListFragment extends Fragment {
    Activity activity;
    View view;
    private List<Friend> myFriends = new ArrayList<Friend>();



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
        this.view = inflater.inflate(R.layout.activity_friend_list, container, false);

        class HelperFriendList implements GetFriendsCallback {
            @Override
            public void execute(Map<String, String> users){
                Log.d(Constants.LOG_TAG,"executed friendly call of the wild");
                try {
                    for (String uid : users.values()) {
                        // Log.d(Constants.LOG_TAG, uid+"");
                        myFriends.add(new Friend(uid, -99, R.drawable.blank_user, "-99", "button"));
                    }

                    populateListView();
                }
                catch(Exception e)
                {
                    Log.d(Constants.LOG_TAG, "Uh oh, looks like you have no friends");
                }
            }
        }

        Database.instance.getFriendsOfUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), new  HelperFriendList());

        return view;
    }

    private void populateListView(){
        ArrayAdapter<Friend> adapter = new FriendListFragment.MyListAdapter();
        ListView list = (ListView) view.findViewById(R.id.friend_list_view);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Friend>{
        public MyListAdapter(){
            super(activity, R.layout.item_view, myFriends);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = activity.getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            Log.d(Constants.LOG_TAG,"friends array adapter");
            //Find the car to work with
            Friend currentFriend = myFriends.get(position);
            final String friendUid = currentFriend.getUser();

            //Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentFriend.getIconID());

            //Make:
            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentFriend.getUser());

            //Year:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + currentFriend.getGoats_sent());

            //Condition:
            TextView conditionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            conditionText.setText(currentFriend.getCondition());

            Button button = (Button) itemView.findViewById(R.id.bfbutton);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d("clicked","friendbutton");
                    //TODO fix temp message id
                    Database.instance.createMessage("TEMP",FirebaseAuth.getInstance().getCurrentUser().getUid(), friendUid, true);   //SEDNS HAPPY GOAT
                }
            });
            return itemView;
        }

    }
}
