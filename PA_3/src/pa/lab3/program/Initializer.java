package pa.lab3.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
        //MONDAY
        Time monStart = new Time(9, 30, 0);
        Time monEnd = new Time(20, 0, 0);
        WeekDayProgram mondayProgram = new WeekDayProgram(
                new Interval[]{ new Interval(monStart, monEnd) },
                WeekDay.MONDAY
        );

        //TUESDAY
        Time tueStart = new Time(10, 30, 0);
        Time tueEnd = new Time(15, 0, 0);
        Time tueStart2 = new Time(17, 30, 0);
        Time tueEnd2 = new Time(20, 0, 0);
        WeekDayProgram tueProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(tueStart2, tueEnd2) },
                WeekDay.TUESDAY
        );

        //WEDNESDAY
        Time wedStart = new Time(10, 30, 0);
        Time wedEnd = new Time(12, 0, 0);
        WeekDayProgram wedProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(wedStart, wedEnd) },
                WeekDay.WEDNESDAY
        );

        //THURSDAY
        Time thuStart = new Time(12, 30, 0);
        Time thuEnd = new Time(16, 0, 0);
        WeekDayProgram thuProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(thuStart, thuEnd) },
                WeekDay.WEDNESDAY
        );

        //FRIDAY
        Time friStart = new Time(14, 30, 0);
        Time friEnd = new Time(21, 0, 0);
        WeekDayProgram friProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(friStart, friEnd) },
                WeekDay.WEDNESDAY
        );

        //SATURDAY
        Time satStart = new Time(18, 30, 0);
        Time satEnd = new Time(22, 0, 0);
        WeekDayProgram satProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(satStart, satEnd) },
                WeekDay.WEDNESDAY
        );

        //SUNDAY
        Time sunStart = new Time(11, 30, 0);
        Time sunEnd = new Time(17, 0, 0);
        WeekDayProgram sunProgram = new WeekDayProgram(
                new Interval[]{ new Interval(tueStart, tueEnd), new Interval(sunStart, sunEnd) },
                WeekDay.WEDNESDAY
        );

        return new Program(new WeekDayProgram[] {
                mondayProgram,
                tueProgram,
                wedProgram,
                thuProgram,
                friProgram,
                satProgram,
                sunProgram
        });
    }
}
