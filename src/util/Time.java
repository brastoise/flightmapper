package util;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.ArrayList;
public class Time {
    private int hours;
    private int minutes;

    public Time(String time) {
        String[] input = time.split(":");
        if (time.length() == 4) {
            hours = Integer.parseInt(input[0]);
            minutes = Integer.parseInt(input[1]);
        } else {
            hours = Integer.parseInt(input[0]);
            minutes = Integer.parseInt(input[1].substring(0,1));
        }
    }

    public int compareTo(Time t) {
        Comparator<Time> compare = new TimeComparator();
        return compare.compare(this, t);
    }

    public static class TimeComparator implements Comparator<Time> {
        public int compare(Time t1, Time t2) {
            return t1.getValue() - t2.getValue();
        }
    }

    public int getHours() {
        return hours;
    }

    public int getMinutes() {
        return minutes;
    }

    public int getValue() {
        return hours*60 + minutes;
    }

    public String toString() {
        if (minutes == 0) {
            return hours + ":00";
        }
        return hours + ":" + minutes;
    }

}
