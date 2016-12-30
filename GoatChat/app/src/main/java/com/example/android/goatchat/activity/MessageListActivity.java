package com.example.android.goatchat.activity;

import android.content.Intent;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageListActivity extends AppCompatActivity {
    class ListEntity {
        public String fromUID;
        public List<Message> messages;
        public ListEntity(String fromUID) {
            this.fromUID = fromUID;
            messages = new ArrayList<>();
        }
    }

    private List<ListEntity> friendArr = new ArrayList<>();
//    Maps friend UID to corresponding ListEntity.
    private Map<String, ListEntity> friendMap = new HashMap<>();

//TODO change to list mesages not friends
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "created message List");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);

        class HelperMessageList implements GetMessagesCallback {
            @Override
            public void execute(Map<String, Message> messages){
                Log.d(Constants.LOG_TAG, "Messages: " + messages);

                try {
                    friendMap = new HashMap<>();
                    for (Message msg : messages.values()) {
                        String senderUID = msg.getFromUID();
//                      If sender is already stored, just update its messages.
                        if (friendMap.keySet().contains(senderUID))
                            friendMap.get(senderUID).messages.add(msg);
//                      Otherwise, create a new entity
                        else {
                            ListEntity item = new ListEntity(senderUID);
                            item.messages.add(msg);
                            friendMap.put(senderUID, item);
                        }

//                        String mid = msg.getMessageId();
//                        Log.d(Constants.LOG_TAG, mid + "");
//                        myMessages.add(msg);  //// TODO: 12/28/16  fix true to change per goat datum
                    }
//                    TODO: Need to order the items in friendArr based on timestamp of last message sent.
                    friendArr = new ArrayList<>();
                    for (ListEntity e : friendMap.values()) {
                        friendArr.add(e);
                    }
                    populateListView();
                }
                catch (Exception e)
                {
                    Log.d(Constants.LOG_TAG,"ERROR: " + e.getMessage());
                }
            }
        }

        Database.instance.getReceivedMessagesOfUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), new  HelperMessageList());
    }


    private void populateListView(){
        ArrayAdapter<ListEntity> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.message_list);
        list.setAdapter(adapter);
    }

    private void showGoat(int typeOfGoat) {
        switch (typeOfGoat){
            case 0:
                showHappyGoat();
                break;
            case 1:
                showSadGoat();

        }

    }

    private void showHappyGoat() {
        Intent intent = new Intent(this, HappyGoatActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }

    private void showSadGoat(){
        Intent intent = new Intent(this, SadGoatActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }

    private void showSexyGoat(){
        //TODO
    }


    private class MyListAdapter extends ArrayAdapter<ListEntity>{

        public MyListAdapter(){
            //Log.d(Constants.LOG_TAG,"big berhta");
            super(MessageListActivity.this, R.layout.message_list_item, friendArr);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Log.d(Constants.LOG_TAG, "view seen");
            View itemView = convertView;
            if (itemView == null) {
                Log.d(Constants.LOG_TAG, "view nnuul");
                itemView = getLayoutInflater().inflate(R.layout.message_list_item, parent, false);
            }

            String str = "FROM: " + friendArr.get(position).fromUID;
            TextView makeText = (TextView) itemView.findViewById(R.id.friend_name);
            makeText.setText(str);


            str = "Number of messages:" + friendArr.get(position).messages.size();
            TextView yearText = (TextView) itemView.findViewById(R.id.num_messages);
            yearText.setText(str);


            Button button = (Button) itemView.findViewById(R.id.view_messages_button);
            Log.d(Constants.LOG_TAG, "making button");
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(Constants.LOG_TAG,"message clicked");
                    //Database.instance.setReceivedMessagetoSeen(messageId, currentMessage.fromUID);   //shows message
                    showGoat(0);  //type of goat
                }
            });

            return itemView;
        }

    }

}
