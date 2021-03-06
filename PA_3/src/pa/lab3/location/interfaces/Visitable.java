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


//    static int sortByOpeningHour(Object loc1, Object loc2) {
//        if(!(loc1 instanceof Visitable)) {
//            return -1;
//        }
//        if(!(loc2 instanceof Visitable)) {
//            return 1;
//        }
//
//        Time time1 = ((Visitable) loc1).getProgram();
//        Time time2 = ((Visitable) loc2).getProgram();
//    }
}
