package com.example.android.goatchat.activity;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.goatchat.Constants;
import com.example.android.goatchat.Database;
import com.example.android.goatchat.models.Friend;
import com.example.android.goatchat.PhoneContacts;
import com.example.android.goatchat.R;
import com.example.android.goatchat.models.User;
import com.example.android.goatchat.callback.GetUsersCallback;
import com.google.firebase.auth.FirebaseAuth;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class AddFriendsActivity extends AppCompatActivity {
    private List<Friend> myUsers = new ArrayList<Friend>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(Constants.LOG_TAG, "Created User List Activity");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);
        createUserCallBackHelperObject();

        PhoneContacts.askContactsPermission(this);
    }
    //Creates an object to populate list which can be used as a callback
    private void createUserCallBackHelperObject(){
        class HelperUserList implements GetUsersCallback {
            @Override
            public void execute(Map<String, User> users){
                for (String uid: users.keySet()){
                    //TODO implements goat sent
                    myUsers.add(new Friend(users.get(uid).getUid(), -99, R.drawable.blank_user, "-99", "Add Friend"));
                }
                populateListView();
            }
        }

        Database.instance.getAllUsers(FirebaseAuth.getInstance().getCurrentUser().getUid(), new  HelperUserList());
    }

    private void populateListView(){
        ArrayAdapter<Friend> adapter = new MyListAdapter();
        ListView list = (ListView) findViewById(R.id.usersListView);
        list.setAdapter(adapter);

    }

    private class MyListAdapter extends ArrayAdapter<Friend>{
        public MyListAdapter(){
            super(AddFriendsActivity.this, R.layout.item_view, myUsers);
        }

        //this overrides ArrayAdapter's getView
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View itemView = convertView;
            if (itemView == null) {
                itemView = getLayoutInflater().inflate(R.layout.item_view, parent, false);
            }

            //Find the car to work with
            Friend currentFriend = myUsers.get(position);

            final String friendUid = currentFriend.getUser();

            //Fill the view
            ImageView imageView = (ImageView)itemView.findViewById(R.id.item_icon);
            imageView.setImageResource(currentFriend.getIconID());

            //Make:
            final TextView makeText = (TextView) itemView.findViewById(R.id.item_txtMake);
            makeText.setText(currentFriend.getUser());

            //Year:
            TextView yearText = (TextView) itemView.findViewById(R.id.item_txtYear);
            yearText.setText("" + currentFriend.getGoats_sent());

            //Condition:
            TextView conditionText = (TextView) itemView.findViewById(R.id.item_txtCondition);
            conditionText.setText(currentFriend.getCondition());

            Button button = (Button) itemView.findViewById(R.id.bfbutton);
            button.setOnClickListener(new View.OnClickListener(){
                public void onClick(View v){
                    Database.instance.addFriendForUserWithUID(FirebaseAuth.getInstance().getCurrentUser().getUid(), friendUid);
                }
            });
            return itemView;
        }

    }

    //  Callback when user grants Read Contacts permissions.
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == 1) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission is granted
                PhoneContacts.getContacts(this);
            } else {
//                Why isn't this working...
                Toast.makeText(getApplicationContext(), "Until you grant the permission, we canot display the names", Toast.LENGTH_SHORT).show();
            }
        }
    }

}