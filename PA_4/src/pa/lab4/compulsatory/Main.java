package pa.lab4.compulsatory;

import pa.lab4.didactic.School;
import pa.lab4.didactic.SchoolAllocation;
import pa.lab4.didactic.Student;

import java.util.LinkedList;
import java.util.List;
import java.util.*;
import java.util.stream.Stream;

/**
 * generates a instance of SchoolAllocation to print an example of the problem (the preferences of each student and school)
 */
public class Main {

    /**
     * generates a list of students
     * @return - a linked list of students sorted by name
     */
    public static List<Student> generateStudentList() {
        /* create a list of students using a linked list */
        List<Student> students = new LinkedList<>();

        Stream.of(0, 1, 2, 3).forEach(i -> {
            students.add(new Student(String.format("S%d", i), "", String.format("serial_number_%d", i)));
        });

        /*
         * sort students by name
         */
        students.sort(new Comparator<Student>() {
            @Override
            public int compare(Student o1, Student o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });

        return students;
    }

    /**
     * generates a list of students sortable by their name
     * @return - a tree set of schools
     */
    public static Set<School> generateSchoolList() {
        Set<School> schools = new TreeSet<>();

        Stream.of(0, 1, 2).forEach(i -> {
            schools.add(new School(String.format("H%d", i), String.format("serial_school_number_%d", i)));
        });

        return schools;
    }

    /**
     * @param students
     * @param schools
     * @return - a map from a student to a ordinated set of school (the preference is given by the order in which are pushed)
     */
    public static Map<Student, Set<School>> generateStudentPreferences(List<Student> students, Set<School> schools) {
        Map<Student, Set<School>> studentPreferences = new HashMap<>();
        Object[] schoolsArray = schools.toArray();

        for(int  i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            /*
             * preferences are given by the order in which each item is pushed
             * (so we will use a likedHashSet for that)
             */
            Set<School> preferredUniversities = new LinkedHashSet<>();

            switch (i) {
                case 2:
                    preferredUniversities.add((School) schoolsArray[0]);
                    preferredUniversities.add((School) schoolsArray[1]);
                    break;
                case 3:
                    preferredUniversities.add((School) schoolsArray[0]);
                    preferredUniversities.add((School) schoolsArray[2]);
                    break;
                default:
                    preferredUniversities.add((School) schoolsArray[0]);
                    preferredUniversities.add((School) schoolsArray[1]);
                    preferredUniversities.add((School) schoolsArray[2]);
                    break;
            }
            studentPreferences.put(student, preferredUniversities);
        }
        return studentPreferences;
    }

    /**
     * @param students
     * @param schools
     * @return - a map from a school to a ordinated set of students (the preference is given by the order in which are pushed)
     */
    public static Map<School, Set<Student>> generateSchoolPreferences(List<Student> students, Set<School> schools) {
        Map<School, Set<Student>> schoolPreferences = new HashMap<>();
        Object[] schoolsArray = schools.toArray();

        for(int  i = 0; i < schoolsArray.length; i++) {
            School school = (School) schoolsArray[i];

            /*
             * preferences are given by the order in which each item is pushed
             * (so we will use a likedHashSet for that)
             */
            Set<Student> preferredStudents = new LinkedHashSet<>();

            switch (i) {
                case 0:
                    preferredStudents.add(students.get(3));
                    preferredStudents.add(students.get(0));
                    preferredStudents.add(students.get(1));
                    preferredStudents.add(students.get(2));
                    break;
                case 1:
                    preferredStudents.add(students.get(0));
                    preferredStudents.add(students.get(2));
                    preferredStudents.add(students.get(1));
                    break;
                default:
                    preferredStudents.add(students.get(0));
                    preferredStudents.add(students.get(1));
                    preferredStudents.add(students.get(3));
                    break;
            }
            schoolPreferences.put(school, preferredStudents);
        }
        return schoolPreferences;
    }

    public static void main(String[] args) {

        List<Student> students = generateStudentList();
        Set<School> schools = generateSchoolList();

        Map<Student, Set<School>>  studentPreferences = generateStudentPreferences(students, schools);
        Map<School, Set<Student>>  schoolPreferences = generateSchoolPreferences(students, schools);

        SchoolAllocation schoolAllocation = new SchoolAllocation(students, schools, studentPreferences, schoolPreferences);

        System.out.printf("%s", schoolAllocation.allPreferenceToString());
    }
}
