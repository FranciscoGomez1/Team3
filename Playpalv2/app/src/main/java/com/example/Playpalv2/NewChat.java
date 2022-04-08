package com.example.Playpalv2;

public class NewChat {

    private String firstName;
    //private String images;

    public NewChat() {}

    public NewChat(String firstName, String images) { //String senderId, String message, String date, String time
        this.firstName = firstName;
        //this.images = images;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

/*    public String getImage() {
        return images;
    }

    public void setImage(String images) {
        this.images = images;
    }*/

    /*public String getSenderId() {
        return senderId;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }
    */

}
