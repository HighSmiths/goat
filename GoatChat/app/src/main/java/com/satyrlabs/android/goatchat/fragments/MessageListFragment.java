package com.satyrlabs.android.goatchat.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.activity.HappyGoatActivity;
import com.satyrlabs.android.goatchat.activity.SadGoatActivity;
import com.satyrlabs.android.goatchat.callback.GetMessagesCallback;
import com.satyrlabs.android.goatchat.callback.GetUsernameCallback;
import com.satyrlabs.android.goatchat.models.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/29/16.
 */

public class MessageListFragment extends Fragment{
    View view;
//    Store this fragment's parent activity.
    Activity activity;

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

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//      Retain a reference to the parent Activity.
        this.activity = activity;
    }
    
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.activity_message_list, container, false);
        Log.d(Constants.LOG_TAG, "created message List");

        class HelperMessageList implements GetMessagesCallback {
            @Override
            public void execute(Map<String, Message> messages){
                Log.d(Constants.LOG_TAG, "Messages: " + messages);

                try {
                    friendMap = new HashMap<>();
                    for (Message msg : messages.values()) {
                        if(msg.getOpened() == true){
                            continue;
                        }


                        String senderUID = msg.getFromUID();
//                      If sender is already stored, just update its messages.
                        if (friendMap.keySet().contains(senderUID))
                            friendMap.get(senderUID).messages.add(msg);
//                      Otherwise, create a new entity
                        else {
                            MessageListFragment.ListEntity item = new MessageListFragment.ListEntity(senderUID);
                            item.messages.add(msg);
                            friendMap.put(senderUID, item);
                        }

//                        String mid = msg.getMessageId();
//                        Log.d(Constants.LOG_TAG, mid + "");
//                        myMessages.add(msg);  //// TODO: 12/28/16  fix true to change per goat datum
                    }
//                    TODO: Need to order the items in friendArr based on timestamp of last message sent.
                    friendArr = new ArrayList<>();
                    for (MessageListFragment.ListEntity e : friendMap.values()) {
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

        return view;
    }


    private void populateListView(){
        ArrayAdapter<MessageListFragment.ListEntity> adapter = new MessageListFragment.MyListAdapter();
        ListView list = (ListView) view.findViewById(R.id.message_list);
        list.setAdapter(adapter);
    }

    private void showGoat(int typeOfGoat) {
        switch (typeOfGoat){
            case 0:
                showSadGoat();
                break;
            case 1:
                showHappyGoat();
                break;
            case 2:
                showSexyGoat();
                break;
            case 3:
                break;
            case 4:
                break;
            case 5:
                break;

        }

    }

    private void showHappyGoat() {
        Intent intent = new Intent(activity, HappyGoatActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        // Returns a referenc to the current activity?
        ((Activity)getActivity()).startActivity(intent);
    }

    private void showSadGoat(){
        Intent intent = new Intent(activity, SadGoatActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }

    private void showSexyGoat(){
        //TODO
    }


    private class MyListAdapter extends ArrayAdapter<MessageListFragment.ListEntity>{

        public MyListAdapter(){
            //Log.d(Constants.LOG_TAG,"big berhta");
            super(activity.getApplicationContext(), R.layout.message_list_item, friendArr);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            Log.d(Constants.LOG_TAG, "view seen");
            View itemView = convertView;
            if (itemView == null) {
                Log.d(Constants.LOG_TAG, "view nnuul");
                itemView = activity.getLayoutInflater().inflate(R.layout.message_list_item, parent, false);
            }







            final TextView makeText = (TextView) itemView.findViewById(R.id.friend_name);
            //makeText.setText(str);


            class HelperGetUserName implements GetUsernameCallback {
                @Override
                public void execute(String s) {
                    makeText.setText(s);

                }
            }

            Database.instance.getUsernameWithUID(friendArr.get(position).fromUID, new HelperGetUserName());







            String str = "Number of messages:" + friendArr.get(position).messages.size();
            TextView yearText = (TextView) itemView.findViewById(R.id.num_messages);
            yearText.setText(str);


            Button button = (Button) itemView.findViewById(R.id.view_messages_button);
            Log.d(Constants.LOG_TAG, "making button");
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Log.d(Constants.LOG_TAG,"message clicked");
                    //Database.instance.setReceivedMessagetoSeen(messageId, currentMessage.fromUID);   //shows message
                    Database.instance.setReceivedMessagetoSeen(friendArr.get(position).messages.get(0).messageId, "unusued field?",
                            friendArr.get(position).messages.get(0).getToUID(), friendArr.get(position).messages.get(0).getFromUID());
                    int typeOGoat = friendArr.get(position).messages.get(0).typeOGoat;
                    friendArr.get(position).messages.remove(0);
                    showGoat(typeOGoat);  //type of goat
                    populateListView();
                    activity.getLayoutInflater();

                }
            });

            return itemView;
        }

    }

}