package com.example.android.goatchat.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.Database;
import com.example.android.goatchat.models.Message;
import com.example.android.goatchat.R;
import com.example.android.goatchat.callback.GetMessagesCallback;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MessageListActivity extends AppCompatActivity {
    private List<Message> myMessages = new ArrayList<Message>();

//TODO change to list mesages not friends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "created message List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

/*
        public void getReceivedMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
            final GetMessagesCallback callback = cb;

            // Query for current user, appending all friends to the input array `arr`.
            database.getReference().child("users").child(uid).child("receivedMessages").addListenerForSingleValueEvent(
                    new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Map<String, String> messages = (Map<String, String>)dataSnapshot.getValue();
                            callback.execute(messages);
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
        }
        */
        class HelperMessageList implements GetMessagesCallback {
            @Override
            public void execute(Map<String, String> messages){

                try {
                    myMessages = new ArrayList<>();

                    for (String mid : messages.values()) {
                        Log.d(Constants.LOG_TAG, mid + "");
                        myMessages.add(new Message(mid, "uid", "-99", true));  //// TODO: 12/28/16  fix true to change per goat datum
                    }
                    populateListView();
                }
                catch (Exception e)
                {
                    Log.d(Constants.LOG_TAG,"no one sent you goats");
                }
            }
        }

        Database.instance.getReceivedMessagesOfUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), new  HelperMessageList());
        
    }


    private void populateListView(){
        ArrayAdapter<Message> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.carsListView);
        list.setAdapter(adapter);
    }

    private class MyListAdapter extends ArrayAdapter<Message>{
        public MyListAdapter(){
            //Log.d(Constants.LOG_TAG,"big berhta");
            super(MessageListActivity.this, R.layout.item_view, myMessages);
            Log.d(Constants.LOG_TAG,"big berhta");
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(Constants.LOG_TAG, "view seen");
            View itemView = convertView;
            if (itemView == null) {
                Log.d(Constants.LOG_TAG, "view nnuul");
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Find the car to work with
            Log.d("this p","lease");
            final Message currentMessage = myMessages.get(position);
            final String messageId = currentMessage.getMessageId();

            //Fill the view
          //  ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
           // imageView.setImageResource();

            //Make:
            //TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            //makeText.setText(currentFriend.getUser());

            //Year:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + currentMessage.typeOGoat);

            //Condition:
            //TextView conditionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            //conditionText.setText(currentFriend.getCondition());

            Button button = (Button) itemView.findViewById(R.id.bfbutton);
            Log.d(Constants.LOG_TAG, "making button");
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(Constants.LOG_TAG,"message clicked");
                    Database.instance.setReceivedMessagetoSeen(messageId, currentMessage.fromUID);   //SEDNS HAPPY GOAT
                }
            });

            return itemView;
        }

    }

}
