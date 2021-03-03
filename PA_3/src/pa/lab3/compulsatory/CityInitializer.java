package pa.lab3.compulsatory;

import pa.lab3.location.*;

public class CityInitializer {
    public static City createATestCity() {
        Location v1 = new Hotel("v1", "A very beautiful place.", "", 10, 10);
        Location v2 = new Museum("v2", "Art", "", 10, 10);
        Location v3 = new Museum("v3", "History", "", 10, 10);
        Location v4 = new Church("v4", "Catholic", "", 10, 10);
        Location v5 = new Church("v5", "Orthodox", "", 10, 10);
        Location v6 = new Restaurant("v6", "Italian", "", 10, 10);

        /*
         * Map the values as in the following scheme:
            From-To	    Cost
            v1→v2	    10
            v1→v3	    50
            v2↔v3	    20
            v2→v4	    20
            v2→v5	    10
            v3→v4	    20
            v4↔v5	    30
            v4→v6	    10
            v5→v6	    20
         */
        Location.setDistanceBetweenLocations(v1, v2, 10);
        Location.setDistanceBetweenLocations(v1, v3, 50);
        Location.setDistanceBetweenLocations(v2, v3, 20);
        Location.setDistanceBetweenLocations(v2, v4, 20);
        Location.setDistanceBetweenLocations(v2, v5, 10);
        Location.setDistanceBetweenLocations(v3, v4, 20);
        Location.setDistanceBetweenLocations(v4, v5, 30);
        Location.setDistanceBetweenLocations(v4, v6, 10);
        Location.setDistanceBetweenLocations(v5, v6, 20);

        City cityStraightOfOutCompton = new City("Compton", new Location[]{
                v1, v2, v3, v4, v5, v6
        });

        return cityStraightOfOutCompton;
    }
}
