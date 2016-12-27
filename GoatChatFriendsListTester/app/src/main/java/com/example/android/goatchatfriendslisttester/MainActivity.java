package com.example.android.goatchatfriendslisttester;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends Activity {

    private ListView lv;

    public void onCreate(Bundle saveInstanceState) {
        setContentView(R.layout.activity_main);
        super.onCreate(saveInstanceState);

        lv = (ListView) findViewById(R.id.friends_list);

        // Instanciating an array list (you don't need to do this,
        // you already have yours).
        List<String> friends_array_list = new ArrayList<String>();
        friends_array_list.add("Max");
        friends_array_list.add("Jake");
        friends_array_list.add("Matt");
        friends_array_list.add("Mike");
        friends_array_list.add("John");
        friends_array_list.add("Samuel");
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

    }
}