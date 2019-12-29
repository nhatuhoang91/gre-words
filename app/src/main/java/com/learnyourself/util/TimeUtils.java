package com.learnyourself.util;


import java.security.acl.Group;
import java.util.GregorianCalendar;

/**
 * Created by Nha on 12/20/2015.
 */
public class TimeUtils {
    public static String createDate(){
        String month;
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        StringBuilder timeFormated = new StringBuilder();
        switch (gregorianCalendar.get(GregorianCalendar.MONTH)){
            case 0:
                month = "Jan";
                break;
            case 1:
                month = "Feb";
                break;
            case 2:
                month = "Mar";
                break;
            case 3:
                month = "Apr";
                break;
            case 4:
                month = "May";
                break;
            case 5:
                month = "June";
                break;
            case 6:
                month = "July";
                break;
            case 7:
                month = "Aug";
                break;
            case 8:
                month = "Sept";
                break;
            case 9:
                month = "Oct";
                break;
            case 10:
                month = "Nov";
                break;
            case 11:
                month = "Dec";
                break;
            default:
                month = "Dec";
        }
        timeFormated.append(month)
                .append(" ")
                .append(gregorianCalendar.get(GregorianCalendar.DATE));

        return timeFormated.toString();
    }
}
