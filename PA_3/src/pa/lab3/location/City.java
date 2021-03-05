package pa.lab3.location;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import pa.lab3.location.*;
import pa.lab3.location.interfaces.Visitable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class City {
    private Location[] locations;
    private String name;

    public City(String name, Location[] locations) {
        this.name = name;
        this.locations = locations;
    }

    /**
     * print all the city names that are visitable, but not payable
     */
    public void printVisitableButNotPayable() {
        System.out.printf("Location visitable, but not payable:\n");
        List locationResults = new ArrayList<Visitable>();

        for(int i = 0; i < this.locations.length; i++) {
            if(this.locations[i].isVisitable() && !this.locations[i].isPayable()) {
                System.out.printf("%s (%s)\n", this.locations[i].getName(), this.locations[i].getSpecialization());
                locationResults.add(this.locations[i]);
            }
        }

        locationResults.sort(Visitable::sortByOpeningHour);

        for(int i = 0; i < locationResults.size(); i++) {
            System.out.printf("%s (%s)\n", ((Location) locationResults.get(i)).getName(), ((Location) locationResults).getSpecialization());
        }

        if(locationResults.size() == 0) {
            System.out.printf("No location.\n");
        }

    }



    public void printMap() {
        System.out.printf("City with the name %s has the following map: %s", this.name, Location.getMapToString());
    }

    private String locationsToString() {
        String text = "";
        for(int i = 0; i < this.locations.length; i++) {
            text = String.format("%s\n%s", text, this.locations[i].toString());
        }
        return text;
    }

    @Override
    public String toString() {
        return "City{" +
                "\n, name='" + name + '\n' +
                "locations=\n" + this.locationsToString() +
                '}';
    }
}
