package com.example.android.goatchat.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.R;
import com.facebook.AccessToken;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import bolts.AppLinks;

public class FacebookFriendsActivity extends AppCompatActivity {

    String appLinkUrl, previewImageUrl;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG,"ffa called");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook_friends);
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


        appLinkUrl = "https://www.mydowmain.com/myapplink";
        previewImageUrl = "https://s-media-cache-ak0.pinimg.com/originals/8c/d0/6a/8cd06a1e9863595ba76ee9932fc4a164.jpg";


        //facebook app link intent filter
        FacebookSdk.sdkInitialize(getApplicationContext());
        Uri targetUrl  = AppLinks.getTargetUrlFromInboundIntent(this, getIntent());
        if (targetUrl != null){
            Log.i(Constants.LOG_TAG, "app link target URLL" + targetUrl.toString());
        }



        /*
        new GraphRequest(
                AccessToken.getCurrentAccessToken(),
                "/{user-id}/friendlists",
                null,
                HttpMethod.GET,
                new GraphRequest.Callback() {
                    public void onCompleted(GraphResponse response) {
            /
                    }
                }
        ).executeAsync();    */

    }

}
