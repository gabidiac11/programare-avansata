package pa.lab3.location;

import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import pa.lab3.location.*;

import java.util.Arrays;

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
        boolean existResults = false;
        for(int i = 0; i < this.locations.length; i++) {
            if(this.locations[i].isVisitable() && !this.locations[i].isPayable()) {
                System.out.printf("%s (%s)\n", this.locations[i].getName(), this.locations[i].getSpecialization());
                if(!existResults) {
                    existResults = true;
                }
            }
        }

        if(!existResults) {
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
