package com.example.android.goatchat;

import android.util.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/26/16.
 */

public class Database {


    public static Database instance = new Database();

    private FirebaseDatabase database;

    private Database() {
        database = FirebaseDatabase.getInstance();
    }

    public void setData(String data) {
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue(data);
    }

    // Reads all of user's friends from Firebase, and stores them in `arr`.
    // Accepts an String array `arr` and a String user ID `uid`.
    public void getFriendsOfUserWithUID(String uid, GetFriendsCallback cb) {
        final GetFriendsCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("friends").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> friends= dataSnapshot.getValue(Constants.Friends.class);
                        callback.execute(friends);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }
    

    public void getReceivedMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
        final GetMessagesCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("receivedMessages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> messages = dataSnapshot.getValue(Constants.Messages.class);
                        callback.execute(messages);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

    public void getSentMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
        final GetMessagesCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("sentMessages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        List<String> messages = dataSnapshot.getValue(Constants.Messages.class);
                        callback.execute(messages);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }


    // Read User functions

    // Reads all users currently using the app
    public void getAllUsers(String uid, GetAllUsersCallback cb) {

        Log.d(Constants.LOG_TAG, "calling getAllUSers");
        final GetAllUsersCallback callback = cb;

        Log.d(Constants.LOG_TAG, database.getReference().child("users").child(uid).toString()+"");
        database.getReference().child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(Constants.LOG_TAG, "dtat changeds");
                        GenericTypeIndicator<Map<String, User>> genericTypeIndicator = new GenericTypeIndicator<Map<String, User>>() {};
                        Map<String, User> users = dataSnapshot.getValue(genericTypeIndicator);
                        Log.d("SDFSDFSD", users.toString());
                        callback.execute(users);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(Constants.LOG_TAG, "DB Cancelled (ERROR): " + databaseError.getMessage());
                    }
                });
    }

//      Callback usage:
    
//    class Callback implements GetAllUsersCallback {
//        public void execute(HashMap<String, User> users) {
//            Manipulate `users` here
//        }
//    }



    // Reads the user with given userId.
    // Accepts a String user Id `uid` and a reference to a User object, and stores the retrieved User in `user`.
    public void getUserWithUID(String userId, GetUserCallback cb) {
        final GetUserCallback callback = cb;

        database.getReference().child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                callback.execute(user);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d(Constants.LOG_TAG, "DB Cancelled (ERROR): " + databaseError.getMessage());
            }
        });
    }

    // Creates a new user record in Firebase with given userId and email.
    public void createNewUserRecordInFirebase(String userId, String email) {
        User user = new User(userId, email);
        user.friends.add(userId);
        database.getReference().child("users").child(userId).setValue(user);
    }

    // Test function to execute some database functions
    public void execute() {
        Log.d(Constants.LOG_TAG, "Executing DB methods");

        FirebaseUser curUser = FirebaseAuth.getInstance().getCurrentUser();

        String uid = curUser.getUid();
        String email = curUser.getEmail();
        Log.d(Constants.LOG_TAG, uid);
        Log.d(Constants.LOG_TAG, email);

        createNewUserRecordInFirebase(uid, email);
    }
}
