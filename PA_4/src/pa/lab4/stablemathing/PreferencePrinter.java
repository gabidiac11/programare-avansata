package pa.lab4.stablemathing;

import pa.lab4.didactic.School;
import pa.lab4.didactic.Student;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PreferencePrinter <T extends PreferencePrintable, M extends PreferencePrintable> {

    /**
     * generates a visual representation of preferences:
     * -----STUDENTS-PREFERENCES----
     * S0:(H0,H1,H2)
     * S3:(H0,H2)
     * S1:(H0,H1,H2)
     * S2:(H0,H1)
     * -----STUDENTS-PREFERENCES----end
     *
     * @return - string
     */
    public String preferenceToString(Map<T, Set<M>> preferences, String title) {
        String output = "\n-----" + title + "----\n";

        for(Map.Entry<T, Set<M>> entry : preferences.entrySet()) {
            T student = entry.getKey();

            output = output + String.format("%s:(", student.getName());
            output += entry.getValue()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            output += ")\n";
        }

        output = output + "-----" + title + "----end\n";

        return output;
    }
}
