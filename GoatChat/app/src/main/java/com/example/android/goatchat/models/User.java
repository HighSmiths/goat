package com.example.android.goatchat.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/26/16.
 */

public class User {
    public String uid;
    public String username;
    public String email;
    public int numMsgSent;
    public int numMsgRec;
    public int numFriends;
    public String phoneNumber;
    public Map<String, Message> sentMessages;
    public Map<String, Message> receivedMessages;
    public Map<String, String> friends;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.username = email.split("@")[0];
        this.email = email;
        this.sentMessages = new HashMap<>();
        this.receivedMessages = new HashMap<>();
        this.friends = new HashMap<>();
    }

    public String getUid() {
        return uid;
    }
    public String getEmail() {
        return email;
    }
    public Map<String, Message> getSentMessages() {
        return sentMessages;
    }
    public Map<String, Message> getReceivedMessages() {
        return receivedMessages;
    }
    public Map<String, String> getFriends() {
        return friends;
    }
    public int getNumMsgSent() {
        return numMsgSent;
    }
    public int getNumMsgRec() {
        return numMsgRec;
    }
    public int getNumFriends() {
        return numFriends;
    }
    public String getPhoneNumber() {return phoneNumber;}
    public String getUsername() {return username;}

    public String toString() {
        return "User "
                + uid + "\n"
                + email + "\n"
                + sentMessages + "\n"
                + receivedMessages + "\n"
                + friends;

    }
}
