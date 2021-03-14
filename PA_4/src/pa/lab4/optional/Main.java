package pa.lab4.optional;

import pa.lab4.didactic.School;
import pa.lab4.didactic.SchoolAllocation;
import pa.lab4.didactic.Student;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class Main {
    public static void main(String[] arcs) {
        List<Student> students = pa.lab4.compulsatory.Main.generateStudentList();
        Set<School> schools = pa.lab4.compulsatory.Main.generateSchoolList();

        Map<Student, Set<School>> studentPreferences = pa.lab4.compulsatory.Main.generateStudentPreferences(students, schools);
        Map<School, Set<Student>>  schoolPreferences = pa.lab4.compulsatory.Main.generateSchoolPreferences(students, schools);

        SchoolAllocation schoolAllocation = new SchoolAllocation(students, schools, studentPreferences, schoolPreferences);

        System.out.printf("%s", schoolAllocation.allPreferenceToString());

        schoolAllocation.allocateStudents();


    }
}
