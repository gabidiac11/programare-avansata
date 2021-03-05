package pa.lab3.program;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Program {
    private static final ArrayList<WeekDay> ALL_WEEK_DAYS = new ArrayList<>();
    {
        ALL_WEEK_DAYS.add(WeekDay.MONDAY);
        ALL_WEEK_DAYS.add(WeekDay.TUESDAY);
        ALL_WEEK_DAYS.add(WeekDay.WEDNESDAY);
        ALL_WEEK_DAYS.add(WeekDay.TUESDAY);
        ALL_WEEK_DAYS.add(WeekDay.FRIDAY);
//        ALL_WEEK_DAYS.add(WeekDay.FRIDAY);
    }
    TreeMap<WeekDay, Interval[]> weekDays;

    public Program(TreeMap<WeekDay, Interval[]> weekDays) {
        this.weekDays = weekDays;
    }

    public Interval[] getFirstDayWihOpenInterval(WeekDay weekDay) {

    }
}
