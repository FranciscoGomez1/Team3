package com.example.Playpalv2.franciscoClassesForRegistrationVersion;

import java.util.Calendar;

public class DOB {
    private Calendar cal;
    private  int year;
    private int month;
    private int day;

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public DOB() {
        todaysDate();
    }
    public void selectDOB(int day, int month, int year){
        this.day = day;
        this.month = month;
        this.year = year;
    }
    public void todaysDate(){
        cal = Calendar.getInstance();
        year = cal.get(Calendar.YEAR);
        month = cal.get(Calendar.MONTH);
        day = cal.get(Calendar.DAY_OF_MONTH);
    }
    public String getTodaysDate() {
        todaysDate();
        return makeDateString(day, month, year);
    }

    private String makeDateString(int day, int month, int year) {
        return getMonthFormat(month) + " " + day + " " + year;
    }

    private String getMonthFormat(int month) {
        if(month == 0){
            return "JAN";
        }else if(month == 1){
            return "FEB";
        }else if(month == 2){
            return "MAR";
        }else if(month == 3){
            return "APR";
        }else if(month == 4){
            return "MAY";
        }else if(month == 5){
            return "JUN";
        }else if(month == 6){
            return "JUL";
        }else if(month == 7){
            return "AUG";
        }else if(month == 8){
            return "SEP";
        }else if(month == 9){
            return "OCT";
        }else if(month == 10){
            return "NOV";
        }else if(month == 11){
            return "DEC";
        }
        return "No valid input"; /// if something when wrong
    }

    public String getDate() {
        return makeDateString(day, month, year);
    }
}
