package pa.lab3.program;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Interval {
    private Time startTime;
    private Time endTime;

    public Interval(Time startTime, Time endTime) {
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
