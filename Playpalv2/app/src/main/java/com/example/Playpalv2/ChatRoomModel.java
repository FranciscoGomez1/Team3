package com.example.Playpalv2;

public class ChatRoomModel {
    private String firstName;
    //private String images;

    public ChatRoomModel() {}

    public ChatRoomModel(String firstName) { //String senderId, String message, String date, String time
        this.firstName = firstName;
        //this.images = images;

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
