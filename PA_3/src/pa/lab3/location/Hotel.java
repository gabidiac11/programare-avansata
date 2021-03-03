package pa.lab3.location;

import pa.lab3.location.interfaces.Classifiable;
import pa.lab3.location.interfaces.Payable;

public class Hotel extends Location implements Payable, Classifiable {
    public Hotel(String name, String description, String image, float latitude, float longitude) {
        super(name, description, image, latitude, longitude);
    }

    @Override
    public boolean isVisitable() {
        return false;
    }

    @Override
    public boolean isPayable() {
        return true;
    }

    @Override
    public boolean isClassifiable() {
        return true;
    }

    @Override
    public String getSpecialization() {
        return "HOTEL";
    }
}
