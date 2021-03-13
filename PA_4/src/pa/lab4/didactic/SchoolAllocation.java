package pa.lab4.didactic;

import com.sun.istack.internal.NotNull;

import java.util.*;
import java.util.stream.Collectors;

/**
 * stores school - student preference
 * print this information in a visual representation -> see SchoolAllocation.allPreferenceToString()
 */
public class SchoolAllocation {
    private List<Student> students;
    private Set<School> schools;
    private Map<Student, Set<School>> studentPreferences;
    private Map<School, Set<Student>> schoolPreferences;


    public SchoolAllocation(
            @NotNull List<Student> students,
            @NotNull Set<School> schools,
            @NotNull Map<Student, Set<School>> studentPreferences,
            @NotNull Map<School, Set<Student>> schoolPreferences
    ) {
        this.students = students;
        this.schools = schools;
        this.studentPreferences = studentPreferences;
        this.schoolPreferences = schoolPreferences;
    }

    /**
     * prints both representation of preferences (from student + school endpoints) -> see functions references by the return stmt
     * @return - string
     */
    public String allPreferenceToString() {
        return String.format("%s%s", this.studentPreferencesToString(), this.schoolPreferencesToString());
    }

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
    private String studentPreferencesToString() {
        String output = "\n-----STUDENTS-PREFERENCES----\n";

        for(Map.Entry<Student, Set<School>> entry : this.studentPreferences.entrySet()) {
            Student student = entry.getKey();

            output = output + String.format("%s:(", student.getFirstName());
            output += entry.getValue()
                    .stream()
                    .map(school -> school.getName())
                    .collect(Collectors.joining(","));

            output += ")\n";
        }

        output = output + "-----STUDENTS-PREFERENCES----end\n";

        return output;
    }

    /**
     * generates a visual representation of preferences:
     * -----UNIVERSITIES-PREFERENCES----
     * H2:(S0 ,S1 ,S3 )
     * H1:(S0 ,S2 ,S1 )
     * H0:(S3 ,S0 ,S1 ,S2 )
     * -----UNIVERSITIES-PREFERENCES----end
     *
     * @return - string
     */
    private String schoolPreferencesToString() {
        String output = "\n-----UNIVERSITIES-PREFERENCES----\n";

        for(Map.Entry<School, Set<Student>> entry : this.schoolPreferences.entrySet()) {
            School school = entry.getKey();

            output = output + String.format("%s:(", school.getName());

            output += entry.getValue()
                    .stream()
                    .map(item -> item.getName())
                    .collect(Collectors.joining(","));

            output += ")\n";
        }

        output = output + "-----UNIVERSITIES-PREFERENCES----end\n";

        return output;
    }
}
