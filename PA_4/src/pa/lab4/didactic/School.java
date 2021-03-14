package pa.lab4.didactic;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import pa.lab4.stablemathing.PreferencePrintable;

import java.util.Comparator;

@AllArgsConstructor
@EqualsAndHashCode
@Getter
public class School implements Comparable<School>, PreferencePrintable {
    private String name;
    private final String registrationNumber;

    @Override
    public int compareTo(School o) {
        return this.getRegistrationNumber().compareTo(o.getRegistrationNumber());
    }
}

