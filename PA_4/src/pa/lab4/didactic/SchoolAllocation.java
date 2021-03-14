package pa.lab4.didactic;

import com.sun.istack.internal.NotNull;
import pa.lab4.stablemathing.PreferencePrintable;
import pa.lab4.stablemathing.PreferencePrinter;

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

    private Map<School, Set<Student>> allocationResults;


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
        this.allocateStudents();
    }

    /**
     * prints both representation of preferences (from student + school endpoints) -> see functions references by the return stmt
     * @return - string
     */
    public String allPreferenceToString() {
        PreferencePrinter<Student, School> printerStudent = new PreferencePrinter<>();
        PreferencePrinter<School, Student> printerSchool = new PreferencePrinter<>();

        return String.format("%s%s",
                printerStudent.preferenceToString(this.studentPreferences, "STUDENTS-PREFERENCES"),
                printerSchool.preferenceToString(this.schoolPreferences, "SCHOOLS-PREFERENCES")
        );
    }

    private boolean isTheBiggestRank(Student student, School school, Map<School, Boolean> schoolSettled) {
        Object[] schools = this.studentPreferences.get(student).toArray();

        for(int i = 0; i < schools.length; i++) {
            if(((School) (schools[i])).equals(school)) {
                return true;
            } else {
                return false;
            }
        }

        return Integer.MAX_VALUE == 1;
    }

    public void allocateStudents() {
        /* students assigned */
        Map<Student, Boolean> studentSettled = new HashMap<>();
        Map<School, Boolean> schoolSettled = new HashMap<>();

        this.allocationResults = new HashMap<>();

//        while(
//                studentSettled.keySet().size() != this.students.size() &&
//                schoolSettled.keySet().size() != this.schools.size()
//        ) {
//            for(Map.Entry<School, Set<Student>> entry : this.schoolPreferences.entrySet()) {
//                School school = entry.getKey();
//
//                /* get students allocated so far for the current school */
//                Set<Student> preferredStudents;
//                if(this.allocationResults.containsKey(school)) {
//                    preferredStudents = this.allocationResults.get(school);
//                } else {
//                    preferredStudents = new LinkedHashSet<>();
//                }
//
//                if(!schoolSettled.containsKey(school)) {
//                    for(Student student : entry.getValue()) {
//                        if(!studentSettled.containsKey(student)) {
//
//                        }
//                    }
//                }
//            }
//        }


    }
}
