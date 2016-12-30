package com.example.android.goatchat.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.android.goatchat.Database;
import com.example.android.goatchat.R;

public class GoatSelectionActivity extends AppCompatActivity {

    String receiver;
    String sender;
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
        receiver = bundle.getString("receiver");
        sender = bundle.getString("sender");
    }

    public void sendHappyGoats(View view){
        Database.instance.createMessage("TEMP",sender, receiver, 1);
        finish();

    }
    public void sendSadGoats(View view){
        Database.instance.createMessage("TEMP",sender,receiver,0);
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
