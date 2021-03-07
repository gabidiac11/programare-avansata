package pa.lab3.program;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Map;

public class Program {
    private static final ArrayList<WeekDay> ALL_WEEK_DAYS = new ArrayList<>();
    {
        ALL_WEEK_DAYS.add(WeekDay.MONDAY);
        ALL_WEEK_DAYS.add(WeekDay.TUESDAY);
        ALL_WEEK_DAYS.add(WeekDay.WEDNESDAY);
        ALL_WEEK_DAYS.add(WeekDay.THURSDAY);
        ALL_WEEK_DAYS.add(WeekDay.FRIDAY);
        ALL_WEEK_DAYS.add(WeekDay.SATURDAY);
        ALL_WEEK_DAYS.add(WeekDay.SUNDAY);
    }

    private Map<WeekDay, Interval[]> programPerDay;

    public Program(Map<WeekDay, Interval[]> programPerDay) {
        this.programPerDay = programPerDay;
    }

    /**
     * @return - the intervals while the first day of the week that is opened
     */
    public Interval[] getFirstDayWihOpenIntervals() {
        for(WeekDay weekDay : ALL_WEEK_DAYS) {
            if(this.programPerDay.containsKey(weekDay) && this.programPerDay.get(weekDay).length > 0) {
                return this.programPerDay.get(weekDay);
            }
        }
        return new Interval[]{};
    }

    /**
     * @return - the first day of the week that is NOT closed
     */
    public WeekDay getFirstDayWithOpenIntervals() {
        for(WeekDay weekDay : ALL_WEEK_DAYS) {
            if(this.programPerDay.containsKey(weekDay) && this.programPerDay.get(weekDay).length > 0) {
                return weekDay;
            }
        }
        return null;
    }

    /**
     * calculate the days duration during which is opened
     * (each day of the week has open intervals-{start:Time, end:Time})
     * @param weekDay - day of the week select (enums: MONDAY, TUESDAY, etc.)
     * @return - Duration instance detailing the week day's duration while it's opened
     */
    public Duration getDayOpenDuration(WeekDay weekDay) {
        if(this.programPerDay.containsKey(weekDay)) {
            Duration sum = Duration.ofSeconds(0);
            for(Interval interval : this.programPerDay.get(weekDay)) {
                sum.plus(Time.getDurationBetween(interval.getStartTime(), interval.getEndTime()));
            }
            return sum;
        }
        return Duration.ofSeconds(0);
    }

    /**
     *
     * @param weekDay - the enum for each week day
     * @return - a string of this format: ${week-day}: [hh:mm:ss - hh:mm:ss]
     * Example:
        * TUESDAY:  [10:30:00 - 15:00:00] [17:30:00 - 20:00:00
     */
    public String dayProgramToString(WeekDay weekDay) {
        if(this.programPerDay.containsKey(weekDay)) {
            String resultString = String.format("%12s: ",  weekDay);

            for(Interval interval : this.programPerDay.get(weekDay)) {
                resultString = String.format("%s [%s - %s]", resultString, interval.getStartTime(), interval.getEndTime().toString());
            }

            return String.format("%12s\n", resultString);
        }
        return String.format("%12s:  CLOSED\n",  weekDay);
    }

    /**
     *
     * @return - each week day format line -> see this.dayProgramToString
     */
    @Override
    public String toString() {
        String resultString = "PROGRAM: \n";
        for(WeekDay weekDay : ALL_WEEK_DAYS) {
            resultString = String.format("%s%s", resultString, this.dayProgramToString(weekDay));
        }
        return resultString;
    }
}
