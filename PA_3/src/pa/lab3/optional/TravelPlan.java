package pa.lab3.optional;

import lombok.Getter;
import lombok.Setter;
import pa.lab3.graph.Graph;
import pa.lab3.location.City;
import pa.lab3.location.Location;
import pa.lab3.location.interfaces.Visitable;

import java.util.*;


@Setter
public class TravelPlan {
    private PriorityQueue<Visitable> locationPreferences;
    private final City city;

    public TravelPlan(City city) {
        this.city = city;
        this.locationPreferences = city.getVisitableLocations();
    }

    /**
     * set a visitable location searching by name
     * @param locationName - location name
     * @param preference - number indicating the preference
     */
    public void addPreference(String locationName, Integer preference) {
        Location location = this.city.getLocationByName(locationName);
        if(location != null && location.isVisitable()) {
            location.setPriority(preference);
        }
    }

    public void addPreference(Visitable location, Integer preference) {
        location.setPriority(preference);
    }

    public void createTravelPlan(Visitable locStart, Visitable locEnd) {
        Graph<Visitable> graph = new Graph<>(this.locationPreferences);

        Map<Visitable, Integer> result = graph.dijkstra(locStart);

//        System.out.printf("Distance from location %s to location %s is %d\n",
//                locStart.getName(), locEnd.getName(), result.get(locEnd));
            this.printTravelPlan(locStart, result);
       }

    private void printTravelPlan(Visitable locStart, Map<Visitable, Integer> result) {
        System.out.printf("Shortest path between locations:\n===========================================\n");
        for(Visitable location : result.keySet()) {
            if(location == locStart) {
                continue;
            }
            System.out.printf("Distance from location %s to location %s is %d\n",
                    locStart.getName(), location.getName(), result.get(location));
        }
        System.out.printf("Shortest path between locations - END:\n===========================================\n\n");
    }



    public void printLocationPreferences() {
        System.out.printf("Travel plan locations:\n===========================================\n");

        for(Visitable location : this.locationPreferences) {
            System.out.printf("%s\n", location.toString());
        }

        System.out.printf("Travel plan locations - END:\n===========================================\n\n");
    }
}
