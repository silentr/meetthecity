package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import models.Tour;

public class DateUtils {

    public static String getCurrentFormattedDate() {
        SimpleDateFormat formatter = new SimpleDateFormat(Tour.DATE_FORMAT);
        Date currentDate = new Date();
        return formatter.format(currentDate);
    }
}
