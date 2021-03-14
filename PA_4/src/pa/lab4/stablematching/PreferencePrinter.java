package pa.lab4.stablematching;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;


/**
 * - use a single function for printing preferences: Map<Student, Set<School>
 *                                                 Map<School, Set<Student>
 * - or stable matching results
 */
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
        String output = "\n------------------------------------------------------------" + title + "----\n";

        for(Map.Entry<T, Set<M>> entry : preferences.entrySet()) {
            T student = entry.getKey();

            output = output + String.format("%s:(", student.getName());
            output += entry.getValue()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            output += ")\n";
        }

        output = output + "------------------------------------------------------------" + title + "----end\n\n";

        return output;
    }

    /**
     * string representing matches
     * @param matches
     * @return
     */
    public String matchesToString(Map<T, M> matches) {
       return String.format("\n------------------------------------------------------------Matches-------------------\n[%s]\n------------------------------------------------------------Matches-------------------\n",
               matches
                .entrySet()
                .stream()
                .map((Map.Entry<T, M> match) -> String.format("(%s:%s)", match.getKey().getName(), match.getValue().getName()))
                .collect(Collectors.joining(","))
       );
    }
}
