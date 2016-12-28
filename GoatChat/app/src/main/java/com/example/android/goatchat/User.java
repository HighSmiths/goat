package com.example.android.goatchat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by mz on 12/26/16.
 */

public class User {
    public String uid;
    public String email;
    public int numMsgSent;
    public int numMsgRec;
    public int numFriends;
    public List<String> sentMessages;
    public List<String> receivedMessages;
    public List<String> friends;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    public User(String uid, String email) {
        this.uid = uid;
        this.email = email;
        this.sentMessages = new ArrayList<>();
        this.receivedMessages = new ArrayList<>();
        this.friends = new ArrayList<>();

        sentMessages.add("messageID");
        receivedMessages.add("messageID");
    }

    public String getUid() {
        return uid;
    }

    public String getEmail() {
        return email;
    }

    public List<String> getSentMessages() {
        return sentMessages;
    }

    public List<String> getReceivedMessages() {
        return receivedMessages;
    }

    public List<String> getFriends() {
        return friends;
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
