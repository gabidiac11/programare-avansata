package pa.lab3.location.interfaces;

import pa.lab3.graph.NodeComparator;
import pa.lab3.program.*;

import java.time.Duration;
import java.util.Comparator;

public interface Visitable extends NodeComparator {
    Program getProgram();

    void setProgram(Program program);
    String getName();
    String getSpecialization();
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
         * see if one of both are not never opening
         * (the first week day with open program is null)
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

}
