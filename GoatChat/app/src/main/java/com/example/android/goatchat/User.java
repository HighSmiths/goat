package com.example.android.goatchat;

import java.util.HashMap;

/**
 * Created by mz on 12/26/16.
 */

public class User {
    public String uid;
    public String email;
    public HashMap<String, String> sentMessages;
    public HashMap<String, String> receivedMessages;
    public HashMap<String, String> friends;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.sentMessages = new HashMap<>();
        this.receivedMessages = new HashMap<>();
        this.friends = new HashMap<>();

        sentMessages.put("message", "test message sent");
        receivedMessages.put("message", "test message received");
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public HashMap<String,String> getSentMessages() {
        return sentMessages;
    }

    public HashMap<String,String> getReceivedMessages() {
        return receivedMessages;
    }

    public HashMap<String,String> getFriends() {
        return friends;
    }

    public String toString() {
        return "User "
                + uid + ", "
                + email + ", "
                + sentMessages + ", "
                + receivedMessages + ", "
                + friends;

    }
}
