package main.java.com.company;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtil {

    public static String NowFlat() {
        return getDateFormat("UTC", "yyyy-MM-dd-HH-mm-ss").format(new Date());
    }

    public static String NowBrazil() {
        return getDateFormat("America/Sao_Paulo", "dd/MM/yyyy HH:mm").format(new Date());
    }

    private static DateFormat getDateFormat(String timezone, String format) {
        TimeZone tz = TimeZone.getTimeZone(timezone);
        DateFormat df = new SimpleDateFormat(format);
        df.setTimeZone(tz);
        return df;
    }

}
