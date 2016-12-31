package com.satyrlabs.android.goatchat.models;

public class Message {
    public String fromUID;
    public String toUID;
    public String messageId;
    public long timestamp;
    public boolean opened;
    public int typeOGoat;

    public Message() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    //1 for happy 0 for sad
    public Message(String mid, String fr, String t, int tp){
        this.fromUID = fr;
        this.toUID =t;
        this.typeOGoat = tp;
        timestamp = 1;
        messageId = mid;
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


//    Keep this as getTypeOGoat, not isTypeOGoat, so Firebase can convert data into a Message when reading from DB.
    public int getTypeOGoat() {

        return typeOGoat;
    }
    public void setTypeOGoat(int typeOGoat) {
        this.typeOGoat = typeOGoat;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    //    Keep this as getOpened, not isOpened, so Firebase can convert data into a Message when reading from DB.
    public boolean getOpened() {
        return opened;
    }
    public void setOpened(boolean opened) {
        this.opened = opened;
    }




}
