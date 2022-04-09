package com.example.Playpalv2;

import android.widget.LinearLayout;

public class ChatRoomModel {
    private String oppoMSG;
    private String myMSG;

    public ChatRoomModel() {}

    public ChatRoomModel(String oppoMSG, String myMSG) { //String senderId, String message, String date, String time
        this.oppoMSG = oppoMSG;
        this.myMSG = myMSG;

    }

    public String getOppoMSG() {
        return oppoMSG;
    }

    public void setOppoMSG(String oppoMSG) {
        this.oppoMSG = oppoMSG;
    }

    public String getMyMSG() {
        return myMSG;
    }

    public void setMyMSG(String myMSG) {
        this.myMSG = myMSG;
    }
}
