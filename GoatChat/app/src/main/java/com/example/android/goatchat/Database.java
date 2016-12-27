package com.example.android.goatchat;

import android.util.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

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
    public void readFriendsAndAddToList(String uid, ReadFriendsCallback cb) {
        final ReadFriendsCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getInstance().getReference("users/" + uid).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        User user = dataSnapshot.getValue(User.class);
                        callback.execute(user);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }



    // Reads the user with given userId.
    // Accepts a String user Id `uid` and a reference to a User object, and stores the retrieved User in `user`.

    public void getUserWithUID(String userId, ReadUserCallback cb) {
        final ReadUserCallback callback = cb;

        database.getReference().child("users").child(userId).addListenerForSingleValueEvent(
                new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User user = dataSnapshot.getValue(User.class);
                Log.d(Constants.LOG_TAG, user.getEmail());
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
        user.friends.put("id", userId);
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
