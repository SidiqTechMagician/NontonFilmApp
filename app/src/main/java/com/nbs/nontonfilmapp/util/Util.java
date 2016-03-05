package com.nbs.nontonfilmapp.util;

import android.text.format.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Sidiq on 05/03/2016.
 */
public class Util {
    public static String getFormattedDate(String date){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate;
        try {
            Date dateRelease = sdf.parse(date);
            formattedDate = DateFormat.format("dd MMM yyyy", dateRelease).toString();
        } catch (ParseException e) {
            e.printStackTrace();
            formattedDate = date;
        }

        return formattedDate;
    }
}
