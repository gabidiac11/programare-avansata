package pa.lab3.program;

public class Initializer {
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
