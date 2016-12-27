package com.example.android.goatchat;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class inboxActivity extends AppCompatActivity {

    public void logOut(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("username", "garbage");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "Logging out");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        class Callback implements ReadUserCallback{
            @Override
            public void execute(User user) {
                Log.d(Constants.LOG_TAG, "USER: " + user.toString());
                TextView userDetails = (TextView) findViewById(R.id.userDetailsText);
                userDetails.setText(user.toString());
            }
        }

        // Get UID passed in from MainActivity.
        String uid = getIntent().getStringExtra("uid");
//        Log.d(Constants.LOG_TAG, uid);
        Database.instance.getUserWithUID(uid, new Callback());


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inbox);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
