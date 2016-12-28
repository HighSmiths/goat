package com.example.android.goatchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mz on 12/26/16.
 */

public class User {
    public String uid;
    public String email;
    public int numMsgSent;
    public int numMsgRec;
    public int numFriends;
    public Map<String, String> sentMessages;
    public Map<String, String> receivedMessages;
    public Map<String, String> friends;

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

        sentMessages.put("messageID", "messageText");
        receivedMessages.put("messageID", "messageText");
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public Map<String, String> getSentMessages() {
        return sentMessages;
    }

    public Map<String, String> getReceivedMessages() {
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

    public String toString() {
        return "User "
                + uid + "\n"
                + email + "\n"
                + sentMessages + "\n"
                + receivedMessages + "\n"
                + friends;

    }
}
