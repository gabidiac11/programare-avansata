package pa.lab4.didactic;

import com.sun.istack.internal.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@AllArgsConstructor
@EqualsAndHashCode
public class Student {
    @Getter
    private String firstName;
    private String lastName;
    private String serialNumber;

    public String getName() {
        return String.format("%s %s", this.firstName, this.lastName);
    }
}

