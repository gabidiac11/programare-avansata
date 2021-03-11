package pa.lab3.location.interfaces;

import pa.lab3.graph.NodeComparator;
import pa.lab3.program.Interval;
import pa.lab3.program.Program;
import pa.lab3.program.Time;
import pa.lab3.program.WeekDay;

import java.time.Duration;
import java.util.Map;
import java.util.TreeMap;

/**
 * make this implement priority queue based on preference
 * make this implement NodeComparator in order to get the shortest path between 2 visitable locations
 */
public interface Visitable extends NodeComparator, Comparable<Visitable> {
    /**
     * required classes to have a program
     * @return - Program - intervals (start, end) for each day (WeekDay enum)
     */
    Program getProgram();
    void setProgram(Program program);

    String getName();

    /**
     * specify what kind of locations is: is a museum? a restaurant, a hotel
     * @return - string - as it only used for printing none much else
     */
    String getSpecialization();

    /**
     * require classes to use priorities (refers to the preference of a order in which to traverse a list of locations)
     * is important for Visitable priority queue and shortest path
     * @return - a number measuring the preference (NOTE: the lower the number the bigger the priority)
     */
    int getPriority();
    void setPriority(int priority);

    int compare(Visitable v1, Visitable v2);

    /**
     * @param location - Visitable
     * @param dayOfWeek - enum (MONDAY, TUESDAY, etc.)
     * @return - Duration based on time a visitable location is opened during a week day
     */
    static Duration getVisitingDuration(Visitable location, WeekDay dayOfWeek) {
        return location.getProgram().getDayOpenDuration(dayOfWeek);
    }

    /**
     * compares 2 visitable locations by who's the first that opens during a week
     * @param loc1
     * @param loc2
     * @return - (-1 | 0 | 1)
     */
    static int compareByOpeningHour(Visitable loc1, Visitable loc2) {
        WeekDay weekDay1 = loc1.getProgram().getFirstDayWithOpenIntervals();
        WeekDay weekDay2 = loc2.getProgram().getFirstDayWithOpenIntervals();

        /*
         * see if one (or both) are not never open
         * (weekday is null)
         */
        if(weekDay1 == null && weekDay2 == null) {
            return 0;
        }
        if(weekDay1 == null) {
            return -1;
        }
        if(weekDay2 == null) {
            return 1;
        }

        /* compare the 2 intervals using the first start time of each one */
        Interval firstInterval1 = loc1.getProgram().getFirstDayWihOpenIntervals()[0];
        Interval firstInterval2 = loc2.getProgram().getFirstDayWihOpenIntervals()[0];

        return Time.compareIntervals(firstInterval1.getStartTime(), firstInterval2.getStartTime());
    }

    /**
     * This should work as in the following requirement
     * - Create default methods in the interface Visitable,
     *   with the opening hour 09:30 and the closing hour 20:00.
     * @return - a program with with the opening hour 09:30 and the closing hour 20:00 for each day
     */
    default Program createDefaultProgram() {
        Map<WeekDay, Interval[]> weekDays = new TreeMap<>();

        Interval[] defaultIntervals = new Interval[]{ new Interval(new Time(9, 30, 0), new Time(20, 0, 0)) };

        for(WeekDay weekDay : WeekDay.values()) {
            weekDays.put(WeekDay.MONDAY,
                    defaultIntervals
            );
        }
        return new Program(weekDays);
    }

}
