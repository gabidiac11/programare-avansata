package pa.lab4.didactic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pa.lab4.stablematching.PreferencePrintable;

@AllArgsConstructor
@EqualsAndHashCode
public class Student implements PreferencePrintable, Comparable<Student>{
    @Getter
    private String firstName;
    private String lastName;
    private String serialNumber;

    public String getName() {
        return this.firstName + (this.lastName.length() > 0 ? " " + this.lastName : "");
    }

    @Override
    public int compareTo(Student o) {
        return this.serialNumber.compareTo(o.serialNumber);
    }
}

