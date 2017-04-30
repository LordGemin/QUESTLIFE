package main.java.com.questlife.questlife.util;

import java.time.LocalDateTime;

/**
 *
 * Created by Gemin on 29.04.2017.
 */
public class DateUtil {

    public static String getDateAsString(LocalDateTime date) {
        String day = (date.getDayOfMonth()<10) ? "0"+date.getDayOfMonth():""+date.getDayOfMonth();
        String month = (date.getMonthValue()<10) ? "0"+date.getMonthValue():""+date.getMonthValue();
        String year = ""+date.getYear();
        year = year.substring(2,4);
        return "("+date.getDayOfWeek().name().substring(0,2)+") "+day+"."+month+"."+year;
    }
}
