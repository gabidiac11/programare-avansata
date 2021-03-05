package pa.lab3.program;

public class WeekDayProgram<T> {
    Interval[] intervals;
    private WeekDay weekDay;

    public WeekDayProgram(Interval[] intervals, WeekDay weekDay) {
        this.intervals = intervals;
        this.weekDay = weekDay;
    }

    public Interval[] getIntervals() {
        return intervals;
    }
}
