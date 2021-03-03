package pa.lab3.location;

import pa.lab3.location.interfaces.Payable;
import pa.lab3.location.interfaces.Visitable;

public class Museum extends Location implements Visitable, Payable {
    public Museum(String name, String description, String image, float latitude, float longitude) {
        super(name, description, image, latitude, longitude);
    }

    @Override
    public boolean isVisitable() {
        return true;
    }

    @Override
    public boolean isPayable() {
        return true;
    }

    @Override
    public boolean isClassifiable() {
        return false;
    }

    @Override
    public String getSpecialization() {
        return "MUSEUM";
    }
}
