package com.satyrlabs.android.goatchat;

import android.graphics.Bitmap;
import android.util.*;

import com.satyrlabs.android.goatchat.callback.AddFriendCallback;
import com.satyrlabs.android.goatchat.callback.GetUsernameCallback;
import com.satyrlabs.android.goatchat.callback.GetUsersCallback;
import com.satyrlabs.android.goatchat.callback.GetFriendsCallback;
import com.satyrlabs.android.goatchat.callback.GetMessagesCallback;
import com.satyrlabs.android.goatchat.callback.GetUserCallback;
import com.satyrlabs.android.goatchat.callback.SetMessageSeenCallback;
import com.satyrlabs.android.goatchat.models.Message;
import com.satyrlabs.android.goatchat.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

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
    public void createMessage(String mid, String fromUID, String toUID, int body) {
//      This is the msgID
        String msgKey = database.getReference().child("messages").push().getKey();
        Message msg = new Message(msgKey,fromUID, toUID, body);


//        String key1 = database.getReference().child("users").child(fromUID).child("sentMessages").push().getKey();
//        String key2 = database.getReference().child("users").child(toUID).child("receivedMessages").push().getKey();

        database.getReference().child("messages").child(msgKey).setValue(msg);
        database.getReference().child("users").child(fromUID).child("sentMessages").child(msgKey).setValue(msg);
        database.getReference().child("users").child(toUID).child("receivedMessages").child(msgKey).setValue(msg);

        Log.d(Constants.LOG_TAG, "In create message function");
        Log.d(Constants.LOG_TAG, "Path: " + "users/" + toUID );

//        incrementMsgCount(fromUID, "sent");
//        incrementMsgCount(toUID, "received");
    }

//  Helper function to increment message count.
    private void incrementMsgCount(String uid, String type) {
        final String t = type;
        database.getReference("users/" + uid).runTransaction(new Transaction.Handler() {
            @Override
            public Transaction.Result doTransaction(MutableData mutableData) {
                User user = (User) mutableData.getValue(User.class);
                if (t.toLowerCase().equals("received")) {
                    user.numMsgRec++;
                } else if (t.toLowerCase().equals("sent")) {
                    user.numMsgSent++;
                }
                mutableData.setValue(user);
                return Transaction.success(mutableData);
            }

            @Override
            public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
            }
        });
    }

    //    Make these two users friends.
    public void setReceivedMessagetoSeen(String messageID, String friendUID, String toUID, String fromUID) {
        class Callback implements SetMessageSeenCallback {
            @Override
            public void execute() {}
        }
        Log.d(Constants.LOG_TAG, "2 parts");
        setReceivedMessagetoSeen(messageID, new Callback(),toUID,fromUID);
    }


    public void setReceivedMessagetoSeen(String messageId, SetMessageSeenCallback cb, String toUID, String fromUID){
        final SetMessageSeenCallback callback = cb;
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.execute();
            }
        };

        Log.d(Constants.LOG_TAG, "3 parts");
        database.getReference().child("messages").child(messageId).child("opened").setValue(true);
        database.getReference().child("users").child(fromUID).child("sentMessages").child(messageId).child("opened").setValue(true);
        database.getReference().child("users").child(toUID).child("receivedMessages").child(messageId).child("opened").setValue(true);

       // database.getReference().child("users").child(messageId).child("")
    }

   /* public void deleteMessage(String messageId, DeleteMessage cb){
        final DeleteMessage callback = cb;
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener(){
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.execute();
            }
        };

        Log.d(Constants.LOG_TAG, "3 parts");
       // database.getReference().child("messages").child(messageId).
    }
    */

    public void getReceivedMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
        final GetMessagesCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("receivedMessages").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        GenericTypeIndicator<Map<String, Message>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Message>>() {};


                      //  Log.d(Constants.LOG_TAG, dataSnapshot.getValue(genericTypeIndicator).toString());


                        Map<String, Message> messages = dataSnapshot.getValue(genericTypeIndicator);
                        callback.execute(messages);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }



    public void getUsernameWithUID(String uid, GetUsernameCallback cb){
        final GetUsernameCallback callback = cb;

        database.getReference().child("users").child(uid).child("username").addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(Constants.LOG_TAG,"PLEASE GIVE ME THIS");
                        String username = (String) dataSnapshot.getValue();
                        callback.execute(username);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                    }
                });
    }



    public void getSentMessagesOfUserWithUID(String uid, GetMessagesCallback cb) {
        final GetMessagesCallback callback = cb;

        database.getReference().child("users").child(uid).child("sentMessages").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(Constants.LOG_TAG,"datachange");
                        GenericTypeIndicator<Map<String, Message>> genericTypeIndicator = new GenericTypeIndicator<Map<String, Message>>() {};

                        Map<String, Message> messages = dataSnapshot.getValue(genericTypeIndicator);
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
    public void addFriendForUserWithUID(final String userUID, final String friendUID) {
        Log.d(Constants.LOG_TAG, "Adding friends");
        class Callback implements AddFriendCallback {
            @Override
            public void execute() {}
        }
        addFriendForUserWithUID(userUID, friendUID, new Callback());
    }
/*
    public void addFriendForUserWithUID(final String userUID, final String friendUID, URL profpic){
        Log.d(Constants.LOG_TAG, "Adding friends with image");
        class Callback implements AddFriendCallback {
            @Override
            public void execute() {}
        }
        addFriendForUserWithUID(userUID, friendUID, profpic, new Callback());
    }


    //  Overloaded function with additional callback parameter.
    public void addFriendForUserWithUID(final String userUID, final String friendUID, URL profpic, AddFriendCallback cb) {
        final AddFriendCallback callback = cb;
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.execute();
            }
        };

        class Callback implements GetFriendsCallback {
            @Override
            public void execute(Map<String, String> friends) {
                if (friends == null || !friends.containsValue(friendUID)) {
                    if (friends == null)
                        Log.d(Constants.LOG_TAG, "friends is null");

                    database.getReference().child("users").child(userUID + "/friends").push().setValue(friendUID);

                    database.getReference().child("users").child(userUID).runTransaction(new Transaction.Handler() {
                        @Override
                        public Transaction.Result doTransaction(MutableData mutableData) {
                            User user = mutableData.getValue(User.class);
                            // TODO: This isn't safe if user adds friends from multiple clients simultaneously.
                            user.numFriends = user.getFriends().size();
                            mutableData.setValue(user);
                            return Transaction.success(mutableData);
                        }

                        @Override
                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
                        }
                    });
                }
            }
        }
        getFriendsOfUserWithUID(userUID, new Callback());
    }


*/

//  Overloaded function with additional callback parameter.
    public void addFriendForUserWithUID(final String userUID, final String friendUID, AddFriendCallback cb) {
        final AddFriendCallback callback = cb;
        DatabaseReference.CompletionListener listener = new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                callback.execute();
            }
        };

        class Callback implements GetFriendsCallback {
            @Override
            public void execute(Map<String, String> friends) {
                if (friends == null || !friends.containsValue(friendUID)) {
                    if (friends == null)
                        Log.d(Constants.LOG_TAG, "friends is null");

                    database.getReference().child("users").child(userUID + "/friends").push().setValue(friendUID);

//                    database.getReference().child("users").child(userUID).runTransaction(new Transaction.Handler() {
//                        @Override
//                        public Transaction.Result doTransaction(MutableData mutableData) {
//                            User user = mutableData.getValue(User.class);
//                            // TODO: This isn't safe if user adds friends from multiple clients simultaneously.
//                            user.numFriends = user.getFriends().size();
//                            mutableData.setValue(user);
//                            return Transaction.success(mutableData);
//                        }
//
//                        @Override
//                        public void onComplete(DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {
//                        }
//                    });
                }
            }
        }
        getFriendsOfUserWithUID(userUID, new Callback());
    }


    // Reads all of user's friends from Firebase, and stores them in `arr`.
    // Accepts an String array `arr` and a String user ID `uid`.
    public void getFriendsOfUserWithUID(String uid, GetFriendsCallback cb) {
        final GetFriendsCallback callback = cb;

        // Query for current user, appending all friends to the input array `arr`.
        database.getReference().child("users").child(uid).child("friends").addValueEventListener(
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
    public void createNewUser(String userId, String email) {
        //User user = new User(userId, email);
        User user = new User(userId, email);
//        user.friends.put(userId);
        database.getReference().child("users").child(userId).setValue(user);
    }

    public void createNewUser(String userId, String email, Bitmap profpic) {
        //User user = new User(userId, email);
        User user = new User(userId, email, profpic);
//        user.friends.put(userId);
        database.getReference().child("users").child(userId).setValue(user);
    }



    // Retrieves the User with the given username from Firebase.
    public void getUserWithUsername(final String username, final GetUsersCallback callback) {
        database.getReference().child("users").orderByChild("username").equalTo(username).addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        GenericTypeIndicator<Map<String, User>> genericTypeIndicator = new GenericTypeIndicator<Map<String, User>>() {};
                        Log.d(Constants.LOG_TAG, genericTypeIndicator.toString());
                        Map<String, User> users = dataSnapshot.getValue(genericTypeIndicator);
                        boolean success = false;
                        if (users != null)
                            success = true;

                        callback.execute(users, success);
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d(Constants.LOG_TAG, "DB Cancelled (ERROR): " + databaseError.getMessage());
                    }
                });
    }

    // Reads all users currently using the app
    public void getAllUsers(String uid, GetUsersCallback cb) {

        Log.d(Constants.LOG_TAG, "calling getAllUSers");
        final GetUsersCallback callback = cb;

        Log.d(Constants.LOG_TAG, database.getReference().child("users").child(uid).toString()+"");
        database.getReference().child("users").addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Log.d(Constants.LOG_TAG, "getALlUsers -> onDataChange");
                        GenericTypeIndicator<Map<String, User>> genericTypeIndicator = new GenericTypeIndicator<Map<String, User>>() {};
                        Map<String, User> users = dataSnapshot.getValue(genericTypeIndicator);
                        boolean success = false;
                        if (users != null)
                            success = true;
                        callback.execute(users, success);
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
