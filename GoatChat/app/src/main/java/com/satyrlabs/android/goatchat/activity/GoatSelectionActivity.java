package com.satyrlabs.android.goatchat.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;

import java.util.ArrayList;

public class GoatSelectionActivity extends AppCompatActivity {

    String receiver;
    String sender;
    ArrayList<String> senders;
    ArrayList<String> receivers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goat_selection);
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

        Bundle bundle = getIntent().getExtras();
      //  receiver = bundle.getString("receiver");
       // sender = bundle.getString("sender");
        try {
            senders = bundle.getStringArrayList("senders");
            receivers = bundle.getStringArrayList("receivers");
        }
        catch (Exception e)
        {

        }
    }

    public void sendHappyGoats(View view){
        Log.d("ca","calling");
       // Database.instance.createMessage("TEMP",sender, receiver, 1);
     //   finish();
        Log.d(Constants.LOG_TAG, ""+senders.size());
        for(int i=0; i<senders.size(); i++){
              Database.instance.createMessage("TEmp",senders.get(i),receivers.get(i),1);
        }
        finish();

    }
    public void sendSadGoats(View view){
      //  Database.instance.createMessage("TEMP",sender,receiver,0);
     //   finish();
        for(int i=0; i<senders.size(); i++){
            Database.instance.createMessage("TEmp",senders.get(i),receivers.get(i),0);
        }
        finish();
    }
    public void sendSexyGoats(){

    }
    public void sendObsequiousGoats(){

    }
    public void sendNostalgicGoats(){

    }
    public void sendFratGoats(){

    }

}
