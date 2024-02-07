package util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Date;

public class DateTimeUtils {

    public static Timestamp currentTimestamp() {
        Instant instant = Instant.now();
        return Timestamp.from(instant);
    }

    public static String convertDateToString(Date date, String format) {
        String result = "";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.format(date);
        } catch (DateTimeParseException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }

    public static Date convertStringToDate(String dateString, String format) {
        Date result = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        try {
            result = sdf.parse(dateString);
        } catch (ParseException e) {
            System.err.println(e.getMessage());
        }
        return result;
    }
}
