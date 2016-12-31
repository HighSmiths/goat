package com.example.android.goatchat.activity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.Database;
import com.example.android.goatchat.models.Friend;
import com.example.android.goatchat.R;
import com.example.android.goatchat.callback.GetFriendsCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FriendListActivity extends AppCompatActivity {
    private List<Friend> myFriends = new ArrayList<Friend>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "created Friend List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friend_list_view);


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
    }

/*
    private void populateFriendList(){
        myFriends.add(new Friend("Max Highsmith", 33, R.drawable.blank_user, "Needs more goats"));
        myFriends.add(new Friend("Michael Highsmith", 12, R.drawable.blank_user, "Dude...More goats"));
        myFriends.add(new Friend("Michael Zhao", 600, R.drawable.blank_user, "Goattastic"));
        myFriends.add(new Friend("John Park", 200, R.drawable.blank_user, "Pretty Goat"));
        myFriends.add(new Friend("P dizzle", 12, R.drawable.blank_user, "Needs more goats"));
    }
*/

    private void populateListView(){
        ArrayAdapter<Friend> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.friend_list_view);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Friend>{
        public MyListAdapter(){
            super(FriendListActivity.this, R.layout.friend_list_item, myFriends);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.friend_list_item, parent, false);
            }

            Log.d(Constants.LOG_TAG,"friends array adapter");
            //Find the car to work with
            Friend currentFriend = myFriends.get(position);
            final String friendUid = currentFriend.getUser();
           // Uri imag = FirebaseAuth.getInstance().getCurrentUser().getProviderData().get(0).getPhotoUrl();


            //Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentFriend.getIconID());
           // imageView.setImageResource();


            //Make:
//            TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
//            makeText.setText(currentFriend.getUser());
//
//            //Year:
//            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
//            yearText.setText("" + currentFriend.getGoats_sent());
//
//            //Condition:
//            TextView conditionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
//            conditionText.setText(currentFriend.getCondition());
//
//            Button button = (Button) itemView.findViewById(R.id.bfbutton);
//            button.setOnClickListener(new View.OnClickListener(){
//                public void onClick(View v){
//                    Log.d("clicked","friendbutton");
//                    //TODO fix temp message id
//                    Database.instance.createMessage("TEMP",FirebaseAuth.getInstance().getCurrentUser().getUid(), friendUid, 0);   //SEDNS HAPPY GOAT
//                }
//            });
            return itemView;
        }

    }
}
