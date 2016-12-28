package com.example.android.goatchat;

import android.support.annotation.Nullable;
import android.util.*;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
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


//             _  _  ____  ____  ____   __    ___  ____    ____  _  _  __ _   ___  ____  __  __   __ _  ____
//            ( \/ )(  __)/ ___)/ ___) / _\  / __)(  __)  (  __)/ )( \(  ( \ / __)(_  _)(  )/  \ (  ( \/ ___)
//            / \/ \ ) _) \___ \\___ \/    \( (_ \ ) _)    ) _) ) \/ (/    /( (__   )(   )((  O )/    /\___ \
//            \_)(_/(____)(____/(____/\_/\_/ \___/(____)  (__)  \____/\_)__) \___) (__) (__)\__/ \_)__)(____/

//    TODO: IN PROGRESS
    public void createMessage(String fromUID, String toUID, Boolean body) {
        Message msg = new Message(fromUID, toUID, body);

        String key = database.getReference().child("messages").push().getKey();
        Map<String, Object> updates = new HashMap<>();
        updates.put("/messages/" + key, msg);
        updates.put("/users/" + fromUID + "/sentMessages/" + key, msg);
        updates.put("/users/" + toUID + "/receivedMessages/" + key, msg);

        database.getReference().runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                Log.d(Constants.LOG_TAG, mutableData.toString());
//                if (mutableData) {
//
//                } else {
//                    return 1;
//                }
                return null;
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

            }
        });
//      Sample code to increment a value.
//        firebase.runTransaction(new Transaction.Handler() {
//            @Override
//            public Transaction.Result doTransaction(final MutableData currentData) {
//                if (currentData.getValue() == null) {
//                    currentData.setValue(1);
//                } else {
//                    currentData.setValue((Long) currentData.getValue() + 1);
//                }
//
//                return Transaction.success(currentData);
//            }
//
//            @Override
//            public void onComplete(FirebaseError firebaseError, boolean committed, DataSnapshot currentData) {
//                if (firebaseError != null) {
//                    Log.d("Firebase counter increment failed.");
//                } else {
//                    Log.d("Firebase counter increment succeeded.");
//                }
//            }
//        });
    }

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

    public void getSentMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
        final GetMessagesCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("sentMessages").addListenerForSingleValueEvent(
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


//             ____  ____  __  ____  __ _  ____    ____  _  _  __ _   ___  ____  __  __   __ _  ____
//            (  __)(  _ \(  )(  __)(  ( \(    \  (  __)/ )( \(  ( \ / __)(_  _)(  )/  \ (  ( \/ ___)
//             ) _) )    / )(  ) _) /    / ) D (   ) _) ) \/ (/    /( (__   )(   )((  O )/    /\___ \
//            (__)  (__\_)(__)(____)\_)__)(____/  (__)  \____/\_)__) \___) (__) (__)\__/ \_)__)(____/

    //    Make these two users friends.
    public void addFriendForUserWithUID(String userUID, String friendUID) {
        class Callback implements AddFriendCallback {
            @Override
            public void execute() {}
        }
        addFriendForUserWithUID(userUID, friendUID, new Callback());
    }

//  Overloaded function with additional callback parameter.
    public void addFriendForUserWithUID(String userUID, String friendUID, AddFriendCallback cb) {
        final AddFriendCallback callback = cb;
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.execute();
            }
        };

        // Method `.push()` creates a new node under a given path.
        // Create the 2-way friendship, so A becomes B's friend, and vice-versa.
        String key1 = database.getReference().child("users").child(userUID).child("friends").push().getKey();
        String key2 = database.getReference().child("users").child(friendUID).child("friends").push().getKey();

        // Use updateChildren for 1 batch atomic update. Either all updates succeed, or all updates fail
        Map<String, Object> childUpdates = new HashMap<>();
        childUpdates.put("/users/" + userUID + "/friends/" + key1, friendUID);
        childUpdates.put("/users/" + friendUID + "/friends/" + key2, userUID);
        database.getReference().updateChildren(childUpdates, listener);

//        Sample Code:
//        String key = mDatabase.child("posts").push().getKey();
//        Post post = new Post(userId, username, title, body);
//        Map<String, Object> postValues = post.toMap();
//
//        Map<String, Object> childUpdates = new HashMap<>();
//        childUpdates.put("/posts/" + key, postValues);
//        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);
//        mDatabase.updateChildren(childUpdates);

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
                        Map<String, String> friends = (Map<String, String>) dataSnapshot.getValue();
                        callback.execute(friends);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }

//              _  _  ____  ____  ____    ____  _  _  __ _   ___  ____  __  __   __ _  ____
//             / )( \/ ___)(  __)(  _ \  (  __)/ )( \(  ( \ / __)(_  _)(  )/  \ (  ( \/ ___)
//             ) \/ (\___ \ ) _)  )   /   ) _) ) \/ (/    /( (__   )(   )((  O )/    /\___ \
//             \____/(____/(____)(__\_)  (__)  \____/\_)__) \___) (__) (__)\__/ \_)__)(____/

    // Creates a new user record in Firebase with given userId and email.
    public void createNewUserRecordInFirebase(String userId, String email) {
        User user = new User(userId, email);
//        user.friends.put(userId);
        database.getReference().child("users").child(userId).setValue(user);
    }

    // Reads all users currently using the app
    public void getAllUsers(String uid, GetAllUsersCallback cb) {

        Log.d(Constants.LOG_TAG, "calling getAllUSers");
        final GetAllUsersCallback callback = cb;

        Log.d(Constants.LOG_TAG, database.getReference().child("users").child(uid).toString()+"");
        database.getReference().child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(Constants.LOG_TAG, "getALlUsers -> onDataChange");
                        GenericTypeIndicator<Map<String, User>> genericTypeIndicator = new GenericTypeIndicator<Map<String, User>>() {};
                        Map<String, User> users = dataSnapshot.getValue(genericTypeIndicator);
                        callback.execute(users);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(Constants.LOG_TAG, "DB Cancelled (ERROR): " + databaseError.getMessage());
                    }
                });
    }


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


}
