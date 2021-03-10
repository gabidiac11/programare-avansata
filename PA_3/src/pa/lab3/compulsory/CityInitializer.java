package pa.lab3.compulsory;

import pa.lab3.location.*;
import pa.lab3.program.Initializer;
import pa.lab3.program.Program;

public class CityInitializer {
    /**
     * creates a test city with the required locations for compulsory exercise
     * @return - City instance, having these locations: v1 (Hotel),
     *                                              v2 (Museum A),
     *                                              v3 (Museum B),
     *                                              v4 (Church A),
     *                                              v5 (Church B),
     *                                              v6 (Restaurant).
     */
    public static City createATestCity() {
        Program genericProgram = Initializer.createProgram();

        Location v1 = new Hotel("v1", "A very beautiful place.", "", 10, 10, 3, 100.99f);
        Location v2 = new Museum("v2", "Art", "", 10, 10, 15, genericProgram);
        Location v3 = new Museum("v3", "History", "", 10, 10, 18.3f, genericProgram);
        Location v4 = new Church("v4", "Catholic", "", 10, 10, genericProgram);
        Location v5 = new Church("v5", "Orthodox", "", 10, 10, genericProgram);
        Location v6 = new Restaurant("v6", "Italian", "", 10, 10, 4, 99.99f, genericProgram);

        /*
         * Maps the values as in the following scheme:
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

        return new City("Compton", new Location[]{
                v1, v2, v3, v4, v5, v6
        });
    }
    /**
     *
     * creates a test city with the required locations for compulsory exercise
     * @return - City instance, having these locations: v1 (Hotel),
     *                                              v2 (Museum A),
     *                                              v3 (Museum B),
     *                                              v4 (Church A),
     *                                              v5 (Church B),
     *                                              v6 (Restaurant).
     * Same as the above, but ORDER OF THE CITY ARRAY LIST CHANGED
     */
    public static City createOptionModuleCity() {
        Program genericProgram = Initializer.createProgram();

        Location v1 = new Hotel("v1", "A very beautiful place.", "", 10, 10, 3, 100.99f);
        Location v2 = new Museum("v2", "Art", "", 10, 10, 15, genericProgram);
        Location v3 = new Museum("v3", "History", "", 10, 10, 18.3f, genericProgram);
        Location v4 = new Church("v4", "Catholic", "", 10, 10, genericProgram);
        Location v5 = new Church("v5", "Orthodox", "", 10, 10, genericProgram);
        Location v6 = new Restaurant("v6", "Italian", "", 10, 10, 4, 99.99f, genericProgram);

        /*
         * Maps the values as in the following scheme:
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

        return new City("Compton", new Location[]{
                v2, v1, v3, v5, v4, v6
        });
    }
}
