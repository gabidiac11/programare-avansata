package pa.lab3.program;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

public class Initializer {
    public Program createProgram() {
        return new Program(new WeekDayProgram[] {
                //MONDAY
                new WeekDayProgram(
                        new Interval[]{ new Interval(new Time(9, 30, 0), new Time(20, 0, 0)) },
                        WeekDay.MONDAY
                ),
                //TUESDAY - 2 intervals
                new WeekDayProgram(
                        new Interval[]{ new Interval(new Time(10, 30, 0), new Time(15, 0, 0)),
                                        new Interval(new Time(17, 30, 0), new Time(20, 0, 0))
                        },
                        WeekDay.TUESDAY
                ),
                //WEDNESDAY
                new WeekDayProgram(
                        new Interval[]{ new Interval(new Time(10, 30, 0), new Time(12, 0, 0))},
                        WeekDay.WEDNESDAY
                ),
                //THURSDAY
                new WeekDayProgram(
                        new Interval[]{ new Interval(new Time(12, 30, 0), new Time(16, 0, 0)) },
                        WeekDay.THURSDAY
                ),
                //FRIDAY
                new WeekDayProgram(
                        new Interval[]{ new Interval(new Time(14, 30, 0), new Time(21, 0, 0)) },
                        WeekDay.FRIDAY
                ),
                //SATURDAY
                new WeekDayProgram(
                        new Interval[]{ new Interval( new Time(18, 30, 0), new Time(22, 0, 0)) },
                        WeekDay.SATURDAY
                ),
                //SUNDAY
                new WeekDayProgram(
                    new Interval[]{ new Interval( new Time(11, 30, 0),
                                                  new Time(17, 0, 0)) },
                    WeekDay.SATURDAY
                )
        });
    }
}
