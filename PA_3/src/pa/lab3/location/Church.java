package pa.lab3.location;

import pa.lab3.location.interfaces.Visitable;

public class Church extends Location implements Visitable {
    public Church(String name, String description, String image, float latitude, float longitude) {
        super(name, description, image, latitude, longitude);
    }

    @Override
    public boolean isVisitable() {
        return true;
    }

    @Override
    public boolean isPayable() {
        return false;
    }

    @Override
    public boolean isClassifiable() {
        return false;
    }

    @Override
    public String getSpecialization() {
        return "CHURCH";
    }
}
