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
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.satyrlabs.android.goatchat.Constants;
import com.satyrlabs.android.goatchat.Database;
import com.satyrlabs.android.goatchat.R;
import com.satyrlabs.android.goatchat.activity.GoatSelectionActivity;
import com.satyrlabs.android.goatchat.callback.GetFriendsCallback;
import com.satyrlabs.android.goatchat.callback.GetUserCallback;
import com.satyrlabs.android.goatchat.callback.GetUsernameCallback;
import com.satyrlabs.android.goatchat.callback.GetUsersCallback;
import com.satyrlabs.android.goatchat.models.Friend;
import com.google.firebase.auth.FirebaseAuth;
import com.satyrlabs.android.goatchat.models.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.satyrlabs.android.goatchat.fragments.LogoutFragment.decodeBase64;

/**
 * Created by mz on 12/30/16.
 */

public class FriendListFragment extends Fragment {
    Activity activity;
    View view;
    private List<Friend> myFriends = new ArrayList<Friend>();
    private ArrayList<String> senders;
    private ArrayList<String> receivers;

    //    TODO: Something about having activity implement an interface???
//    http://stackoverflow.com/questions/14354279/call-parents-activity-from-a-fragment
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

//      Retain a reference to the parent Activity.
        this.activity = activity;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "creating logout view fragment");
        this.view = inflater.inflate(R.layout.friend_list_view, container, false);

        class HelperFriendList implements GetFriendsCallback {
            @Override
            public void execute(Map<String, String> users) {
                Log.d(Constants.LOG_TAG, "executed friendly call of the wild");
                try {
                    myFriends = new ArrayList<>();
                    for (String uid : users.values()) {
                        Log.d(Constants.LOG_TAG, "os toj jkf+"+uid+"");
                        myFriends.add(new Friend(uid, -99, R.drawable.blank_user, "-99", "button"));
                    }

                    populateListView();
                } catch (Exception e) {
                    Log.d(Constants.LOG_TAG, "Uh oh, looks like you have no friends");
                }
            }

        }

        Database.instance.getFriendsOfUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), new HelperFriendList());

        return view;
    }

    private void populateListView() {
        ArrayAdapter<Friend> adapter = new FriendListFragment.MyListAdapter();
        ListView list = (ListView) view.findViewById(R.id.content_friend_list);
        list.setAdapter(adapter);

        senders= new ArrayList<String>();
        receivers = new ArrayList<String>();

        Button sendButton = (Button) view.findViewById(R.id.SendGoat);
        sendButton.setText("Send Goat");
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Log.d("clicked", "seriend button");
                launchSelectGoat(senders, receivers);
            }
        });

    }

    private class MyListAdapter extends ArrayAdapter<Friend> {
        public MyListAdapter() {
            super(activity, R.layout.friend_list_item, myFriends);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = activity.getLayoutInflater().inflate(R.layout.friend_list_item, parent, false);
            }

            Log.d(Constants.LOG_TAG, "friends array adapter");
            //Find the car to work with
            Friend currentFriend = myFriends.get(position);
            final String friendUid = currentFriend.getUser();

            //Fill the view
           final ImageView imageView = (ImageView) itemView.findViewById(R.id.item_icon);

          //  imageView.setImageBitmap(decodeBase64(myFriends.get(position).getImage()));


            class HelperGetUser implements GetUserCallback{
                @Override
                public void execute(User user) {
                    imageView.setImageBitmap(decodeBase64(user.getProfPic()));

                }
            }
            Database.instance.getUserWithUID(friendUid, new HelperGetUser());



            //Make:
           final TextView nameText = (TextView) itemView.findViewById(R.id.item_name);
            nameText.setText(currentFriend.getUser());

             String username;

            class HelperGetUserName implements GetUserCallback {
                @Override
                public void execute(User user) {


                            nameText.setText(user.getUsername());

                }
            }

            Database.instance.getUserWithUID(friendUid, new HelperGetUserName());

            final CheckBox checkBox = (CheckBox) itemView.findViewById(R.id.checkBox);
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(checkBox.isChecked()){
                        Log.d(Constants.LOG_TAG, "checked");
                        receivers.add(friendUid);
                        senders.add(FirebaseAuth.getInstance().getCurrentUser().getUid()) ;
                    }
                    else{
                        Log.d(Constants.LOG_TAG, "unched");
                        receivers.remove(friendUid);
                        senders.remove(FirebaseAuth.getInstance().getCurrentUser().getUid());
                    }
                }
            });



          //  Button button = (Button) itemView.findViewById(R.id.send_msg_button);
          //  button.setOnClickListener(new View.OnClickListener() {
          //      public void onClick(View v) {
          //          Log.d("clicked", "friend button");
          //          selectGoat(FirebaseAuth.getInstance().getCurrentUser().getUid(), friendUid);

                    //          Database.instance.createMessage("TEMP",FirebaseAuth.getInstance().getCurrentUser().getUid(), friendUid, 0);   //SEDNS HAPPY GOAT
          //      }
         //   });
            return itemView;
        }

    }

    public void launchSelectGoat(ArrayList<String> senders, ArrayList<String> receivers)
    {
        Log.d(Constants.LOG_TAG, senders.size()+"");
        Intent intent = new Intent(FriendListFragment.this.getActivity(), GoatSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putStringArrayList("senders", senders);
        bundle.putStringArrayList("receivers", receivers);
        intent.putExtras(bundle);
        startActivity(intent);
    }
    public void selectGoat(String sender, String receiver){

        Intent intent = new Intent(FriendListFragment.this.getActivity(), GoatSelectionActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("sender", sender);
        bundle.putString("receiver", receiver);
        intent.putExtras(bundle);
        startActivity(intent);
    }

}
