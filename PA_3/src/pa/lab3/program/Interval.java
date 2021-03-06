package pa.lab3.program;

import java.time.Duration;
import java.util.Date;

public class Interval {
    private Time startTime;
    private Time endTime;

    public Interval(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    public void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

//    public Duration getDuration() {
//
//        return new Duration(this.endTime. - this.startTime);
//    }
}
