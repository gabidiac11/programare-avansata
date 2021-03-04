package pa.lab3.location.interfaces;

import pa.lab3.program.Initializer;
import pa.lab3.program.Program;
import pa.lab3.program.WeekDay;

import java.time.Duration;
import java.time.LocalTime;

public interface Visitable {
    void setProgram(Program program);
    Program getProgram();
    static Duration getVisitingDuration(Visitable location, WeekDay dayOfWeek) {
        final Duration duration;
        duration = Duration.between(LocalTime.NOON,LocalTime.MAX);
        return duration;
    }
}
