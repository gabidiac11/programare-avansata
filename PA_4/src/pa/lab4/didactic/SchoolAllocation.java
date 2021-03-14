package pa.lab4.didactic;

import com.sun.istack.internal.NotNull;
import pa.lab4.stablematching.PreferencePrinter;
import pa.lab4.stablematching.StableMarriage;

import java.util.*;
import java.util.stream.Collectors;

/**
 * stores school - student preference
 * print this information in a visual representation -> see SchoolAllocation.allPreferenceToString()
 */
public class SchoolAllocation {
    private List<Student> students;
    private Set<School> schools;

    /**
     * LinkedHashSet are used because are ordered and doesn't permit duplicates
     * each student has a ordered set of schools based on preference
     */
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

        /*
         * ! - these should be a LinkedHashSet otherwise the preference are not kept as intended - !
         */
        this.studentPreferences = studentPreferences;
        this.schoolPreferences = schoolPreferences;
    }

    public SchoolAllocation(
            @NotNull List<Student> students,
            @NotNull Set<School> schools,
            @NotNull Map<Student, Set<School>> studentPreferences
    ) {
        this.students = students;
        this.schools = schools;

        /*
         * ! - these should be a LinkedHashSet otherwise the preference are not kept as intended - !
         */
        this.studentPreferences = studentPreferences;
        this.schoolPreferences = this.schoolPreferenceBasedOnGrades(this.students, this.schools);
    }

    /**
     * @param students
     * @param schools
     * @return - a map from a school to a ordinated set of students (the preference is given by the order in which are pushed)
     */
    private Map<School, Set<Student>> schoolPreferenceBasedOnGrades(List<Student> students, Set<School> schools) {
        Map<School, Set<Student>> schoolPreferences = new HashMap<>();
        Object[] schoolsArray = schools.toArray();

        for(int  i = 0; i < schoolsArray.length; i++) {
            School school = (School) schoolsArray[i];

            /*
             * preferences are given by the order of the grades
             */
            Set<Student> preferredStudents = new LinkedHashSet<>();

            /* iterate trough a filtered and sorted list of students using school grades */
            this.students.stream().filter(student -> {
                return school.getStudentGrade(student) != null;
            }).sorted((s1, s2) -> {
                return Integer.compare(school.getStudentGrade(s2), school.getStudentGrade(s1));
            }).forEach(student -> {
                preferredStudents.add(student);
            });

            schoolPreferences.put(school, preferredStudents);
        }
        return schoolPreferences;
    }

    /**
     * prints both representation of preferences (from student + school endpoints) -> see functions references by the return stmt
     * @return - string
     */
    public String allPreferenceToString() {
        return String.format("%s%s",
                new PreferencePrinter<Student, School>()
                        .preferenceToString(this.studentPreferences, "STUDENTS-PREFERENCES"),
                new PreferencePrinter<School, Student>()
                        .preferenceToString(this.schoolPreferences, "SCHOOLS-PREFERENCES")
        );
    }


    /**
     * calculates and prints the stable matches
     */
    public void allocateStudents() {
        /*
         * convert these to lists
         */
        Map<Student, List<School>> manPreferences = new HashMap<>();
        this.studentPreferences.entrySet().stream().forEach(entry -> {
            manPreferences.put(entry.getKey(), entry.getValue().stream().collect(Collectors.toList()));
        });

        Map<School, List<Student>> womenPreferences = new HashMap<>();
        this.schoolPreferences.entrySet().stream().forEach(entry -> {
            womenPreferences.put(entry.getKey(), entry.getValue().stream().collect(Collectors.toList()));
        });

        Map<Student, School> results = new StableMarriage<>(
                                                this.students,
                                                this.schools,
                                                manPreferences,
                                                womenPreferences
                                        ).generateStableMatching();

        /* print results */
        System.out.print(
                new PreferencePrinter<Student, School>()
                    .matchesToString(results)
        );
    }
}
