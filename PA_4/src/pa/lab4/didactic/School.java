package pa.lab4.didactic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import pa.lab4.stablematching.PreferencePrintable;

import java.util.Map;

@EqualsAndHashCode

public class School implements Comparable<School>, PreferencePrintable {
    @Getter
    private String name;
    @Getter
    private final String registrationNumber;
    @Setter
    private Map<Student, Integer> grades;

    public School(String name, String registrationNumber) {
        this.name = name;
        this.registrationNumber = registrationNumber;
    }

    public School(String name, String registrationNumber, Map<Student, Integer> grades) {
        this.name = name;
        this.registrationNumber = registrationNumber;
        this.grades = grades;
    }

    public Integer getStudentGrade(Student s) {
        if(grades != null && grades.containsKey(s)) {
            return grades.get(s);
        }
        return null;
    }

    @Override
    public int compareTo(School o) {
        return this.getRegistrationNumber().compareTo(o.getRegistrationNumber());
    }
}

