package com.example.android.goatchat;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class Message {


    public String fromUID;
    public String toUID;
    public int messageId;
    public int timestamp;
    public boolean opened;
    public boolean typeOGoat;
    


    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    //1 for happy 0 for sad
    public Message(String fr, String t, boolean tp){
        this.fromUID = fr;
        this.toUID =t;
        this.typeOGoat = tp;
        timestamp = 1;
        messageId = -99;
        opened = false;

    }

    public String getFromUID() {
        return fromUID;
    }
    public void setFromUID(String fromUID) {
        this.fromUID = fromUID;
    }
    public String getToUID() {
        return toUID;
    }
    public void setToUID(String toUID) {
        this.toUID = toUID;
    }
    public boolean isTypeOGoat() {
        return typeOGoat;
    }
    public void setTypeOGoat(boolean typeOGoat) {
        this.typeOGoat = typeOGoat;
    }
    public int getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(int timestamp) {
        this.timestamp = timestamp;
    }
    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }
    public boolean isOpened() {
        return opened;
    }
    public void setOpened(boolean opened) {
        this.opened = opened;
    }




}
