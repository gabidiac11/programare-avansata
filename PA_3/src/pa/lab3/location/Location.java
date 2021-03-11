package pa.lab3.location;

import javafx.util.Pair;
import lombok.Getter;
import lombok.Setter;
import pa.lab3.location.interfaces.Visitable;

import java.util.*;

@Getter
@Setter
public abstract class Location {
    protected static int numOfLocations = 0;
    protected static Map<Location, Map<Location, Integer>> costMatrix = new HashMap<>();
    private static Location[] allInstances = new Location[0];



    private String name;
    private String description;
    private String image;
    private float latitude;
    private float longitude;
    private final int id;
    private int priority = Integer.MAX_VALUE;

    public Location(String name, String description, String image, float latitude, float longitude) {

        this.name = name;

        this.id = numOfLocations;

        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * mark the distance between 2 locations, using its unique id (which has a role of an 'index')
     * @param loc1 - location 1
     * @param loc2 - location 2
     * @param distance - a number of km
     */
    public static void setDistanceBetweenLocations(Location loc1, Location loc2, int distance) {

        Map<Location, Integer> costLine = costMatrix.get(loc1);
        if(costLine == null) {
            costLine = new HashMap<>();
        }

        costLine.put(loc2, distance);
        costMatrix.put(loc1, costLine);

        costLine = costMatrix.get(loc2);
        if(costLine == null) {
            costLine = new HashMap<>();
        }

        costLine.put(loc1, distance);
        costMatrix.put(loc2, costLine);
    }

    /**
     * @return - a string detailing the cost of travelling between 2 locations (according to cost matrix)
     *         - each row has this format: [*location-name1 -> *location-name2]: *distance
     *  Example:
         [v1 -> v2]: 10
         [v1 -> v3]: 50
         [v2 -> v3]: 20
         [v2 -> v4]: 20
         [v2 -> v5]: 10
         [v3 -> v4]: 20
         [v4 -> v5]: 30
         [v4 -> v6]: 10
         [v5 -> v6]: 20
     *
     */
    public static String getMapToString() {
        String stringResult = "";
        Set<Location> alreadyDisplayed = new HashSet<>();

        for(Location location : costMatrix.keySet()) {
            Map<Location, Integer> costLine = costMatrix.get(location);

            for(Location location2 : costLine.keySet()) {
                if(alreadyDisplayed.contains(location2)) {
                    continue;
                }

                stringResult = String.format(
                        "%s\n [%s -> %s]: %d",
                        stringResult,
                        location.getName(),
                        location2.getName(),
                        costLine.get(location2));
            }
            alreadyDisplayed.add(location);
        }

        return String.format("%s\n", stringResult);
    }

    public abstract boolean isVisitable();

    public abstract boolean isPayable();

    public abstract boolean isClassifiable();

    public abstract String getSpecialization();

    protected static int getCost(Location location1, Location location2) {
        Map<Location, Integer> costLine = costMatrix.get(location1);
        if(costLine != null && costLine.containsKey(location2)) {
            return costLine.get(location2);
        }
        return -1;
    }

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude + String.format(", priority=%d", this.getPriority())
                ;
    }
}
