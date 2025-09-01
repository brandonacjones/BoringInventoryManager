package util;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TimeUtil {
    public static String getLocalTime() {
        LocalTime lt = LocalTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        return lt.format(dtf);
    }
}
