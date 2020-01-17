package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateUtils {

    public static String getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
    }

    public static Date getCurrentTimeOfTheDay() throws ParseException {
        Calendar calendar = Calendar.getInstance();
        DateFormat dateFormat = getDateFormatForTime();

        return dateFormat.parse(dateFormat.format(calendar.getTime()));
    }

    public static DateFormat getDateFormatForTime() {
        return new SimpleDateFormat("HH:mm");
    }
}
