package com.example.android.findnewfriends;

/**
 * Created by Owner on 12/27/2016.
 */

public class Friend {
    private String user;
    private int goats_sent;
    private int iconID;
    private String condition;

    public Friend(String user, int goats_sent, int iconID, String condition) {
        super();
        this.user = user;
        this.goats_sent = goats_sent;
        this.iconID = iconID;
        this.condition = condition;
    }

    public String getUser() {
        return user;
    }
    public int getGoats_sent() {
        return goats_sent;
    }
    public int getIconID(){
        return iconID;
    }
    public String getCondition(){
        return condition;
    }

}
