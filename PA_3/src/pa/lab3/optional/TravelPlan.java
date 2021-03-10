package pa.lab3.optional;

import lombok.Getter;
import lombok.Setter;
import pa.lab3.graph.Graph;
import pa.lab3.location.City;
import pa.lab3.location.Location;
import pa.lab3.location.interfaces.Visitable;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;


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

        graph.dijkstra(locStart);
    }



    public void printLocationPreferences() {
        System.out.printf("Location preferences:\n===========================================\n");

        for(Visitable location : this.locationPreferences) {
            System.out.printf("%s\n", location.toString());
        }

        System.out.printf("Location preferences - END:\n===========================================\n");
    }
}
