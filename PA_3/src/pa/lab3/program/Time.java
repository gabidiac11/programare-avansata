package pa.lab3.program;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Duration;
import java.util.Objects;

/**
 * NOTE: this is better if is replaced by timestamp
 */
@Getter
@Setter
public class Time {
    private int hours;
    private int minutes;
    private int seconds;

    public Time(int hours, int minutes, int seconds) {
        this.hours = hours;
        this.minutes = minutes;
        this.seconds = seconds;
    }

    /**
     * (useful for calculating time operations using java libraries)
     * @param time - Time instance
     * @return - a timestamp using only the hours, minutes, seconds
     */
    private static Timestamp timeToTimestamp(Time time) {
        return Timestamp.valueOf(String.format("1953-03-05 %s", time.toString()));
    }

    /**
     * compare 2 Time instances using the java Timestamp
     * @param time1
     * @param time2
     * @return - (-1 | 0 | 1)
     */
    public static int compareIntervals(Time time1, Time time2) {
        Timestamp timeStamp1 = timeToTimestamp(time1);
        Timestamp timeStamp2 = timeToTimestamp(time2);

        return timeStamp1.compareTo(timeStamp2);
    }
    /**
     * gets a Duration instance consisting of amount of time betwee 2 Time instances using the java Timestamp
     * @param time1
     * @param time2
     * @return - Duration instance
     */
    public static Duration getDurationBetween(Time time1, Time time2) {
        Timestamp timeStamp1 = timeToTimestamp(time1);
        Timestamp timeStamp2 = timeToTimestamp(time2);

        return Duration.between(timeStamp1.toLocalDateTime(), timeStamp1.toLocalDateTime());
    }

    /**
     * @return - a string of this format: hh:mm:ss
     *         - intended to work in generating Timestamp objects
     */
    @Override
    public String toString() {
        String formatHour = this.hours < 10 ? "0%d" : "%d";
        String formatMin = this.minutes < 10 ? "0%d" : "%d";
        String formatSeconds = this.seconds < 10 ? "0%d" : "%d";

        String formatFinal = String.format("%s:%s:%s", formatHour, formatMin, formatSeconds);

        return String.format(formatFinal, this.hours, this.minutes, this.seconds);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Time time = (Time) o;
        return hours == time.hours && minutes == time.minutes && seconds == time.seconds;
    }

    @Override
    public int hashCode() {
        return Objects.hash(hours, minutes, seconds);
    }
}
