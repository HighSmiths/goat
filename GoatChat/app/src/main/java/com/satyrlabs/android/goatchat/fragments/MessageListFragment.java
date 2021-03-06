package com.satyrlabs.android.goatchat.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.GoatActivity.SexyGoatActivity;
import com.satyrlabs.android.goatchat.ImageConverter;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.GoatActivity.HappyGoatActivity;
import com.satyrlabs.android.goatchat.GoatActivity.SadGoatActivity;
import com.satyrlabs.android.goatchat.callback.GetMessagesCallback;
import com.satyrlabs.android.goatchat.callback.GetUserCallback;
import com.satyrlabs.android.goatchat.callback.GetUsernameCallback;
import com.satyrlabs.android.goatchat.models.Message;
import com.google.firebase.auth.FirebaseAuth;
import com.satyrlabs.android.goatchat.models.User;

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
        Intent intent = new Intent(activity, SexyGoatActivity.class);
        intent.putExtra("uid", FirebaseAuth.getInstance().getCurrentUser().getUid());
        startActivity(intent);
    }
    private void showObsequiousGoat(){

    }

    private void showNostalgicGoat(){

    }
    private void showFratGoat(){

    }


    private class MyListAdapter extends ArrayAdapter<MessageListFragment.ListEntity>{

        public MyListAdapter(){
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

//          Configure the on click event to view messages
            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    for (Message message: friendArr.get(position).messages){
                        Database.instance.setReceivedMessagetoSeen(message.messageId, "unusued field?",
                                message.getToUID(), message.getFromUID());
                        int typeOGoat=message.getTypeOGoat();
                        showGoat(typeOGoat);
                    }
                }
            });

//            Set the friend's profile image
            final ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);
            class HelperGetUser implements GetUserCallback {
                @Override
                public void execute(User user) {
                    if (user.getProfPic() == null) {
                        Log.d(Constants.LOG_TAG, "Prof pic: " + user.getProfPic());
//                        imageView.setImageResource(R.drawable.profile_default);
                    } else {
                        Bitmap bm = Constants.decodeBase64(user.getProfPic());
                        imageView.setImageBitmap(ImageConverter.getRoundedCornerBitmap(bm, 30));
                    }


                }
            }
            Database.instance.getUserWithUID(friendArr.get(position).fromUID, new HelperGetUser());

//          Set the friend's name
            final TextView nameText = (TextView) itemView.findViewById(R.id.friend_name);
            class HelperGetUserName implements GetUsernameCallback {
                @Override
                public void execute(String s) {
                    nameText.setText(s);
                    nameText.setTypeface(Constants.tf(activity));
                }
            }
            Database.instance.getUsernameWithUID(friendArr.get(position).fromUID, new HelperGetUserName());

//            Set the number of messages from this friend.
            String str = "Number of messages:" + friendArr.get(position).messages.size();
            TextView numMsgText = (TextView) itemView.findViewById(R.id.num_messages);
            numMsgText.setText(str);
            numMsgText.setTypeface(Constants.tf(activity));


            return itemView;
        }

    }

}