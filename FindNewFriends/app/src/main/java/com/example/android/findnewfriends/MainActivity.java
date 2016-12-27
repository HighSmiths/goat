package com.example.android.findnewfriends;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private List<Friend> myFriends = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        populateFriendList();
        populateListView();
    }

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
            super(MainActivity.this, R.layout.item_view, myFriends);
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
