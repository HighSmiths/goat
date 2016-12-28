package com.example.android.goatchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserListActivity extends AppCompatActivity {
    private List<Friend> myFriends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "created list ACtivity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        //populateFriendList();
        populateListView();


        class HelperUserList implements GetAllUsersCallback{
            @Override
            public void execute(Map<String, User> users){
                Log.d(Constants.LOG_TAG,"executed called");
                for (String uid: users.keySet()){
                    Log.d(Constants.LOG_TAG, uid+"");
                    myFriends.add(new Friend(users.get(uid).getUid(), -99, R.drawable.blank_user, "-99"));
                }
                populateListView();
            }
        }

        Database.instance.getAllUsers(FirebaseAuth.getInstance().getCurrentUser().getUid(), new  HelperUserList());
    }


    /*
    private class
    //Database user querry
    private void populateUserList(ArrayList<User> friends){
        for(friend: friends){
            myFriends.add(new Friend(, 33, R.drawable.blank_user, "Needs more goats");
        }

    }
    */


    private void populateFriendList(){
        myFriends.add(new Friend("Max Highsmith", 33, R.drawable.blank_user, "Needs more goats"));
        myFriends.add(new Friend("Michael Highsmith", 12, R.drawable.blank_user, "Dude...More goats"));
        myFriends.add(new Friend("Michael Zhao", 600, R.drawable.blank_user, "Goattastic"));
        myFriends.add(new Friend("John Park", 200, R.drawable.blank_user, "Pretty Goat"));
        myFriends.add(new Friend("P dizzle", 12, R.drawable.blank_user, "Needs more goats"));
    }


    private void populateListView(){
        ArrayAdapter<Friend> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.carsListView);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Friend>{
        public MyListAdapter(){
            super(UserListActivity.this, R.layout.item_view, myFriends);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Find the car to work with
            Friend currentFriend = myFriends.get(position);

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

            return itemView;
        }

    }

}