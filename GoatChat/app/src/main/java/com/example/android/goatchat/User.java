package com.example.android.goatchat;

import java.util.HashMap;

/**
 * Created by mz on 12/26/16.
 */

public class User {

    public String email;
    public HashMap<String, String> messages_sent;
    public HashMap<String, String> messages_received;
    public HashMap<String, String> friends;

    public User(String email) {
        this.email = email;
        this.messages_sent = new HashMap<>();
        this.messages_received = new HashMap<>();
        this.friends = new HashMap<>();

        messages_sent.put("message", "test message sent");
        messages_received.put("message", "test message received");
    }


}
