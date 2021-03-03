package pa.lab3.location;

import pa.lab3.location.interfaces.Classifiable;
import pa.lab3.location.interfaces.Payable;
import pa.lab3.location.interfaces.Visitable;

public class Restaurant extends Location implements Visitable, Payable, Classifiable {
    public Restaurant(String name, String description, String image, float latitude, float longitude) {
        super(name, description, image, latitude, longitude);
    }
}
