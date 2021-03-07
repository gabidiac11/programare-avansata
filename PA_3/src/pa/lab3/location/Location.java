package pa.lab3.location;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Location {
    protected static int numOfLocations = 0;
    protected static int[][] costMatrix = new int[0][0];
    private static Location[] allInstances = new Location[0];

    private String name;
    private String description;
    private String image;
    private float latitude;
    private float longitude;
    private final int id;

    public Location(String name, String description, String image, float latitude, float longitude) {
        this.name = name;

        this.id = numOfLocations;
        this.pushNewLocationToCostMatrix();

        this.description = description;
        this.image = image;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    /**
     * append the new location to the linking matrix of location cost
     */
    private void pushNewLocationToCostMatrix() {
        /*
         * increase the count indicator of instances
         */
        numOfLocations++;

        /*
         * copy the previous state of the cost matrix
         */
        int costMatrixCopy[][] = new int[numOfLocations][numOfLocations];
        for(int i = 0; i < costMatrix.length; i++) {
            for(int ii = 0; ii < costMatrix.length; ii++) {
                costMatrixCopy[i][ii] = costMatrix[i][ii];
            }
        }
        /*
         *  initialize the link with the new location added and the rest
         */
        for(int ii = 0; ii < costMatrixCopy.length; ii++) {
            costMatrixCopy[costMatrixCopy.length - 1][ii] = 0;
        }
        costMatrix = costMatrixCopy;

        /*
         * push this instance reference to stack
         */
        Location[] allInstancesCopy = new Location[numOfLocations];
        System.arraycopy(allInstances, 0, allInstancesCopy, 0, allInstances.length);
        allInstancesCopy[numOfLocations-1] = this;
        allInstances = allInstancesCopy;
    }

    /**
     * mark the distance between 2 locations, using its unique id (which has a role of an 'index')
     * @param loc1 - location 1
     * @param loc2 - location 2
     * @param distance - a number of km
     */
    public static void setDistanceBetweenLocations(Location loc1, Location loc2, int distance) {
        costMatrix[loc1.id][loc2.id] = distance;
        costMatrix[loc2.id][loc1.id] = distance;
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

        for(int i = 0; i < costMatrix.length; i++) {
            for(int ii = i+1; ii < costMatrix.length; ii++) {
                if(costMatrix[i][ii] > 0) {
                    stringResult = String.format("%s\n [%s -> %s]: %d", stringResult, allInstances[i].getName(), allInstances[ii].getName(), costMatrix[i][ii]);
                }
            }
        }

        return stringResult;
    }

    public abstract boolean isVisitable();

    public abstract boolean isPayable();

    public abstract boolean isClassifiable();

    public abstract String getSpecialization();

    @Override
    public String toString() {
        return "Location{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude
                ;
    }
}
