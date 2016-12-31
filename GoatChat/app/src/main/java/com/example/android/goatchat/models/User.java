package com.example.android.goatchat.models;

import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.example.android.goatchat.Constants;
import com.facebook.FacebookSdk;

import java.net.URL;
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
    public ImageView imageView;
    public Bitmap profPic;

    public User() {
        // Default constructor required for calls to DataSnapshot.getValue(User.class)
        // I have NO clue what that means.
    }

    public User(String uid, String email) {
        this.uid = uid;
        Log.d(Constants.LOG_TAG, "email:"+email);
        if(email != null) {
            this.username = email.split("@")[0];
            this.email = email;
        }
        else{
            Log.d(Constants.LOG_TAG, "NOEMAL");
            this.username = email;
        }
        this.sentMessages = new HashMap<>();
        this.receivedMessages = new HashMap<>();
        this.friends = new HashMap<>();
    }

    public User(String uid, String email, Bitmap im)
    {
        this.uid = uid;
        Log.d(Constants.LOG_TAG, "email:"+email);
        if(email != null) {
            this.username = email.split("@")[0];
            this.email = email;
        }
        else{
            Log.d(Constants.LOG_TAG, "NOEMAL");
            this.username = email;
        }
        this.sentMessages = new HashMap<>();
        this.receivedMessages = new HashMap<>();
        this.friends = new HashMap<>();
        this.profPic = im;
    }

    public ImageView getImageView() { return imageView;}
    public void setImageView(ImageView imageView){this.imageView = imageView;}
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
