package pa.lab4.optional;

import pa.lab4.didactic.School;
import pa.lab4.didactic.SchoolAllocation;
import pa.lab4.didactic.Student;

import java.util.*;
import java.util.stream.Stream;

import com.github.javafaker.Faker;

/**
 * this generate examples like in compulsory - but changes some things:
 * - the way students are preferred by the schools -> it only insert grades and the SchoolAllocation figures out the preference
 * - FAKER for generating students and schools names
 */
public class Main {

    /**
     * generates a list of students
     * @return - a linked list of students sorted by name
     */
    public static List<Student> generateStudentList() {
        /* create a list of students using a linked list */
        List<Student> students = new LinkedList<>();

        Faker faker = new Faker();

        Stream.of(0, 1, 2, 3).forEach(i -> {
            students.add(new Student(faker.name().firstName(), faker.name().lastName(), String.format("serial_number_%d", i)));
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

        Faker faker = new Faker();

        Stream.of(0, 1, 2).forEach(i -> {
            schools.add(new School(faker.university().name(), String.format("serial_school_number_%d", i)));
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
                    preferredUniversities.add((School) schoolsArray[1]);
                    preferredUniversities.add((School) schoolsArray[0]);
                    break;
                case 3:
                    preferredUniversities.add((School) schoolsArray[0]);
                    preferredUniversities.add((School) schoolsArray[2]);
                    break;
                default:
                    preferredUniversities.add((School) schoolsArray[2]);
                    preferredUniversities.add((School) schoolsArray[0]);
                    preferredUniversities.add((School) schoolsArray[1]);
                    break;
            }
            studentPreferences.put(student, preferredUniversities);
        }
        return studentPreferences;
    }

    public static void addGradesFromDifferentSchools(List<Student> students, Set<School> schools) {
        for(School school : schools) {
            String regNumber = school.getRegistrationNumber();
            if(
               regNumber.equals("serial_school_number_0") ||
               regNumber.equals("serial_school_number_1")
            ) {
                Map<Student, Integer> grades = new HashMap<>();
                grades.put(students.get(0), 10);
                grades.put(students.get(1), 9);
                grades.put(students.get(2), 8);

                school.setGrades(grades);
            }
            if(regNumber.equals("serial_school_number_2")) {
                Map<Student, Integer> grades = new HashMap<>();
                grades.put(students.get(0), 10);
                grades.put(students.get(1), 9);

                school.setGrades(grades);
            }

            if(regNumber.equals("serial_school_number_3")) {
                Map<Student, Integer> grades = new HashMap<>();
                grades.put(students.get(0), 10);
                grades.put(students.get(2), 9);

                school.setGrades(grades);
            }
        }
    }

    public static void main(String[] arcs) {
        List<Student> students = generateStudentList();
        Set<School> schools = generateSchoolList();

        Map<Student, Set<School>> studentPreferences = generateStudentPreferences(students, schools);

        /*
         * school preference by grades
         */
        addGradesFromDifferentSchools(students, schools);
        SchoolAllocation schoolAllocation = new SchoolAllocation(students, schools, studentPreferences);

        System.out.printf("%s", schoolAllocation.allPreferenceToString());

        schoolAllocation.allocateStudents();
    }
}
