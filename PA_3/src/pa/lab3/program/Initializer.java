package pa.lab3.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Initializer {

    private static Date dateFromShortHour(String hourString) throws ParseException {
        String dateString = String.format("31-Dec-1998 %s:00", hourString);
        return new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(dateString);
    }

    /**
     *
     * @param timeString - a string in this format "hh:mm" (Ex. 18:30)
     * @return - Time object based on string
     */
    private static Time stringToTime(String timeString) throws ParseException {
        Date date = dateFromShortHour(timeString);
        return new Time(date.getHours(), date.getMinutes(), date.getSeconds());
    }

    private static WeekDayProgram createWeekProgramForDay(String[][] hours, WeekDay weekDay) throws ParseException {
        Interval[] intervals = new Interval[hours.length];

        for(int i = 0; i < hours.length; i++) {
            intervals[i] = new Interval(stringToTime(hours[i][0]), stringToTime((hours[i][1])));
        }

        return new WeekDayProgram(
                intervals,
                weekDay
        );
    }

    public static Program createProgram() {

        TreeMap<WeekDay, Interval[]> weekDaysIntervals= new TreeMap<>();

        //MONDAY
        weekDaysIntervals.put(WeekDay.MONDAY, new Interval[]{
                new Interval(new Time(9, 30, 0), new Time(20, 0, 0))
        });

        //TUESDAY
        weekDaysIntervals.put(WeekDay.TUESDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0)),
                new Interval(new Time(17, 30, 0), new Time(20, 0, 0))
        });

        //WEDNESDAY
        weekDaysIntervals.put(WeekDay.WEDNESDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0))
        });

        //THURSDAY
        weekDaysIntervals.put(WeekDay.THURSDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0))
        });

        //FRIDAY
        weekDaysIntervals.put(WeekDay.FRIDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0))
        });

        //SATURDAY
        weekDaysIntervals.put(WeekDay.SATURDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0))
        });

        //SUNDAY
        weekDaysIntervals.put(WeekDay.SUNDAY, new Interval[]{
                new Interval(new Time(10, 30, 0), new Time(15, 0, 0))
        });

        return new Program(weekDaysIntervals);
    }
}
