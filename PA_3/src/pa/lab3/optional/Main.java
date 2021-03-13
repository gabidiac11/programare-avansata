package pa.lab3.optional;

import pa.lab3.compulsory.CityInitializer;
import pa.lab3.location.City;
import pa.lab3.location.interfaces.Visitable;

public class Main {
    public static void main(String[] args) {
        City newCity = CityInitializer.createOptionModuleCity();
        //newCity.printVisitableButNotPayable();

        TravelPlan travelPlan = new TravelPlan(newCity);

        newCity.printMap();

        /*
         * for this example I added to all of them these preferences - but only Visitable will be set
         */
        travelPlan.addPreference("v1", 1);
        travelPlan.addPreference("v2", 2);
        travelPlan.addPreference("v3", 3);
        travelPlan.addPreference("v4", 5);
        travelPlan.addPreference("v5", 6);
        travelPlan.addPreference("v6", 7);

        travelPlan.printLocationPreferences();

        travelPlan.createTravelPlan(
                (Visitable) newCity.getLocationByName("v2"),
                (Visitable) newCity.getLocationByName("v6"));
    }
}
