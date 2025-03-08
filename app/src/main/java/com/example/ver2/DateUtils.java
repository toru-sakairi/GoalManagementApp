package com.example.ver2;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static String formatDate_Japanese(Date date) {
        if(date != null) {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日");
            return formatter.format(date);
        }else{
            return "null";
        }
    }

}
