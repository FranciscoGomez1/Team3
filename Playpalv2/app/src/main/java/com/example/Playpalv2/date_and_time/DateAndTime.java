package com.example.Playpalv2.date_and_time;


import android.util.Log;

import java.util.Calendar;
import java.util.Date;

public class DateAndTime {

    Date currenTime = Calendar.getInstance().getTime();
    public Date getCurrenTime() {
        return currenTime;
    }

}
