package com.satyrlabs.android.goatchat.models;

/**
 * Created by Michael Highsmith on 12/27/16.
 */

public class Friend {
    private String user;
    private int goats_sent;
    private int iconID;
    private String condition;
    private String button;
    private String image;
   // private ImageView imageView;

    public Friend(){

    }
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

    public Friend(String user, int goats_sent, int iconID, String condition, String button, String image) {
        super();
        this.user = user;
        this.goats_sent = goats_sent;
        this.iconID = iconID;
        this.condition = condition;
        this.button = button;
        this.image = image;
    }



    //  public ImageView getImageView() { return imageView;}
   // public void setImageView(ImageView imageView){this.imageView = imageView;}
    public String getUser() {
        return user;
    }
    public String getImage()
    {
        return image;
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