package com.example.kico.botnet.utils;

import android.os.Environment;
//import android.util.Log;

import java.io.File;
import java.util.Calendar;

public class CommonMethods {

    static final String TAGCM = "Inside Service";

    public static String getDate() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DATE);
        String date = String.valueOf(day) + "/" + String.valueOf(month) + "/" + String.valueOf(year);

        //Log.d(TAGCM, "Date " + date);
        return date;
    }


    public static String getTime() {
        Calendar cal = Calendar.getInstance();
        int sec = cal.get(Calendar.SECOND);
        int min = cal.get(Calendar.MINUTE);
        int hr = cal.get(Calendar.HOUR_OF_DAY);
        int milisec = cal.get(Calendar.MILLISECOND);

        String time = String.valueOf(hr) + String.valueOf(min) + String.valueOf(sec) + String.valueOf(milisec);

        //Log.d(TAGCM, "Date " + time);
        return time;
    }

    public static String getClearTime() {
        Calendar cal = Calendar.getInstance();
        int sec = cal.get(Calendar.SECOND);
        int min = cal.get(Calendar.MINUTE);
        int hr = cal.get(Calendar.HOUR_OF_DAY);

        String time = String.valueOf(hr) + ":" + String.valueOf(min) + ":" + String.valueOf(sec);

        //Log.d(TAGCM, "Date " + time);
        return time;
    }

    public static String getPath() {
        File file = new File(Environment.getExternalStorageDirectory() + "/Teardroid/");
        if (!file.exists()) {
            file.mkdir();
        }
        String path = file.getAbsolutePath();
        //Log.d(TAGCM, "Path " + path);

        return path;
    }

    public static String removeSpaceInPhoneNo(String phoneNo) {
        return phoneNo.replace(" ", "");
    }

}
