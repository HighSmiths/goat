package com.example.android.goatchat.models;

/**
 * Created by Michael Highsmith on 12/27/16.
 */

public class Friend {
    private String user;
    private int goats_sent;
    private int iconID;
    private String condition;
    private String button;

    public Friend(String user, int goats_sent, int iconID, String condition) {
        super();
        this.user = user;
        this.goats_sent = goats_sent;
        this.iconID = iconID;
        this.condition = condition;
    }

    public Friend(String user, int goats_sent, int iconID, String condition, String button) {
        super();
        this.user = user;
        this.goats_sent = goats_sent;
        this.iconID = iconID;
        this.condition = condition;
        this.button = button;
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