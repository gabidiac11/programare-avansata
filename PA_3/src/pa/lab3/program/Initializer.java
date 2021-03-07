package pa.lab3.program;

import java.util.Map;
import java.util.TreeMap;

public class Initializer {
    /**
     *
     * @return - a Program instance that has the following values for each day of the week:
         *       MONDAY:  [09:30:00 - 20:00:00]
         *      TUESDAY:  [10:30:00 - 15:00:00] [17:30:00 - 20:00:00]
         *    WEDNESDAY:  [10:30:00 - 12:00:00]
         *     THURSDAY:  [12:30:00 - 16:00:00]
         *       FRIDAY:  [14:30:00 - 21:00:00]
         *     SATURDAY:  [11:30:00 - 17:00:00]
         *       SUNDAY:  CLOSED
     *
     *     Format (class based):
     *     {WeekDay-enum}:  [Interval-{Time-{09:30:00} - Time-{20:00:00}}]
     */
    public static Program createProgram() {
        Map<WeekDay, Interval[]> weekDays = new TreeMap<>();

        //MONDAY
        weekDays.put(WeekDay.MONDAY,
                new Interval[]{ new Interval(new Time(9, 30, 0), new Time(20, 0, 0)) }
        );

        //TUESDAY - 2 intervals
        weekDays.put(WeekDay.TUESDAY,
                new Interval[]{ new Interval(new Time(10, 30, 0), new Time(15, 0, 0)),
                        new Interval(new Time(17, 30, 0), new Time(20, 0, 0))
                }
        );

        //WEDNESDAY
        weekDays.put(WeekDay.WEDNESDAY,
                new Interval[]{ new Interval(new Time(10, 30, 0), new Time(12, 0, 0))}
        );

        //THURSDAY
        weekDays.put(WeekDay.THURSDAY,
                new Interval[]{ new Interval(new Time(12, 30, 0), new Time(16, 0, 0)) }
        );

        //FRIDAY
        weekDays.put(WeekDay.FRIDAY,
                new Interval[]{ new Interval(new Time(14, 30, 0), new Time(21, 0, 0)) }
        );

        //SATURDAY
        weekDays.put(WeekDay.SATURDAY,
                new Interval[]{ new Interval( new Time(18, 30, 0), new Time(22, 0, 0)) }
        );

        //SUNDAY
        weekDays.put(WeekDay.SATURDAY,
                new Interval[]{ new Interval( new Time(11, 30, 0),
                        new Time(17, 0, 0)) }
        );

        return new Program(weekDays);
    }
}
