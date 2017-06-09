package com.example.adriaalvarez.android_boilerplate.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by adria.alvarez on 09/06/2017.
 */

public class Utils {

    public Date convertToDate(String jsonDate) {
        try {
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Date date = format.parse(jsonDate);
            return date;
        } catch (Exception e) {
            return null;
        }
    }

    public String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }
}
