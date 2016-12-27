package com.example.android.goatchat;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ListOfFriends extends AppCompatActivity {

    private ListView lv;


    @Override
    public void onCreate(Bundle saveInstanceState) {
        // String username = getIntent().getStringExtra("username");
        // Log.d(Constants.LOG_TAG, username);
        Log.d(Constants.LOG_TAG, "UID: " + getIntent().getStringExtra("uid"));

        super.onCreate(saveInstanceState);

        setContentView(R.layout.list_of_friends);
        lv = (ListView) findViewById(R.id.friends_list);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        final List<String> friends_array_list = new ArrayList<String>();
        friends_array_list.add("Max");
        friends_array_list.add("Jake");
        friends_array_list.add("Matt");
        friends_array_list.add("Mike");
        friends_array_list.add("John");
        friends_array_list.add("Samuel");

        // This is the array adapter, it takes the context of the activity as a
        // first parameter, the type of list view as a second parameter and your
        // array as a third parameter.
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                friends_array_list );

        lv.setAdapter(arrayAdapter);

        class Callback implements GetFriendsCallback {
            public void execute(User user) {
                for (String friend : user.friends.values()) {
                    friends_array_list.add(friend);
                }
            }
        }

        // Get UID passed in from MainActivity.
        String uid = getIntent().getStringExtra("uid");
        // Read from database to get friends
        Database.instance.getFriendsOfUserWithUID(uid, new Callback());
    }
}