package pa.lab3.location;

import pa.lab3.location.interfaces.Visitable;

import java.util.ArrayList;
import java.util.List;

public class City {
    private Location[] locations;
    private String name;

    public City(String name, Location[] locations) {
        this.name = name;
        this.locations = locations;
    }

    /**
     * print all the city names that are visitable, but not payable (with the program attached)
     * sorted by opening hour
     *
     * Example:
         * Location visitable, but not payable:
         * ===========================================
         * v4 (CHURCH)
         * PROGRAM:
         *       MONDAY:  [09:30:00 - 20:00:00]
         *      TUESDAY:  [10:30:00 - 15:00:00] [17:30:00 - 20:00:00]
         *    WEDNESDAY:  [10:30:00 - 12:00:00]
         *     THURSDAY:  [12:30:00 - 16:00:00]
         *       FRIDAY:  [14:30:00 - 21:00:00]
         *     SATURDAY:  [11:30:00 - 17:00:00]
         *       SUNDAY:  CLOSED
         *
         *  ....(etc.)....
     */
    public void printVisitableButNotPayable() {
        System.out.printf("Location visitable, but not payable:\n===========================================\n");
        List locationResults = new ArrayList<Visitable>();

        for(int i = 0; i < this.locations.length; i++) {
            if(this.locations[i].isVisitable() && !this.locations[i].isPayable()) {
                locationResults.add(this.locations[i]);
            }
        }

        locationResults.sort((a, b) -> Visitable.compareByOpeningHour((Visitable) a, (Visitable) b));

        if(locationResults.size() == 0) {
            System.out.printf("No location.\n");
        } else {
            for (Object locationResult : locationResults) {
                Visitable currentLocation = (Visitable) locationResult;
                System.out.printf("%s (%s)\n%s \n", currentLocation.getName(), currentLocation.getSpecialization(), currentLocation.getProgram().toString());
            }
        }
        System.out.printf("===========================================\n");
    }

    /**
     * print the city's map cost between each location -> for more info go to Location.getMapToString
     */
    public void printMap() {
        System.out.printf("City with the name %s has the following map: %s",
                this.name,
                Location.getMapToString());
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
