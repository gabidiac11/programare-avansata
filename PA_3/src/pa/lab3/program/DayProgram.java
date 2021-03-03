package pa.lab3.program;

public class DayProgram {
    OpeningHour[] openHours;

    public DayProgram(int[] hours, int[] minutes, int[] seconds) {
        final int length = Math.min(hours.length, Math.min(minutes.length, seconds.length));

        OpeningHour[] openingHours = new OpeningHour[hours.length];
    }
}
