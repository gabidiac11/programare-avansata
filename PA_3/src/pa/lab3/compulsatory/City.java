package pa.lab3.compulsatory;

import pa.lab3.location.*;

public class City {
    private Location[] locations;
    private String name;

    public City(String name, Location[] locations) {
        this.name = name;
        this.locations = locations;
    }

    public void printMap() {
        System.out.printf("City with the name %s has the following map: %s", this.name, Location.getMapToString());
    }
}
