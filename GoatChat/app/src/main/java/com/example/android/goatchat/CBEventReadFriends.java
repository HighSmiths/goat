package com.example.android.goatchat;

import android.util.Log;

import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by mz on 12/27/16.
 */

public class CBEventReadFriends {
    private List<String> friends;
    public CBEventReadFriends(List<String> friends) {
        this.friends = friends;
    }

    public void readFriends(User user) {
        for (String i : user.friends.keySet()) {
            friends.add(user.friends.get(i));
        }
        Log.d(Constants.LOG_TAG, user.toString());
    }
}
