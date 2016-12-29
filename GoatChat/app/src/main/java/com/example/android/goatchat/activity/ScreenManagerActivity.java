package com.example.android.goatchat.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.R;
import com.google.firebase.auth.FirebaseAuth;

public class ScreenManagerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_manager);
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

    public void openUsers(View view){
        Intent intent = new Intent(this, AddFriendsActivity.class);
        intent.putExtra("uid", "c");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "open Users");
    }

    public void listCurrentFriends(View view){
        Intent intent = new Intent(this, FriendListActivity.class);
        intent.putExtra("uid", "c");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "open CurrentFriends");

    }

    public void listCurrentMessages(View view){
        Intent intent = new Intent(this, MessageListActivity.class);
        intent.putExtra("uid", "c");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "open Messages");
    }

    public boolean isFacebookUser()
    {
        return false;  //TODO check if is fb uses
    }

    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(this, MainActivity.class);
        if(isFacebookUser()){
            //TODo logout of fb
        }
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "Logging out");
    }

    public void aboutus(View view){
        Intent intent = new Intent(this, AboutUsActivity.class);
        intent.putExtra("uid","c");
        startActivity(intent);
        Log.d(Constants.LOG_TAG, "open AboutUsActivity");
    }

}
