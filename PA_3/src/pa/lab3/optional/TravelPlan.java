package pa.lab3.optional;

import lombok.Getter;
import lombok.Setter;
import pa.lab3.location.City;
import pa.lab3.location.Location;
import pa.lab3.location.interfaces.Visitable;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.TreeMap;


@Setter
public class TravelPlan {
    private List locationToVisit = new ArrayList<Visitable>();
    private PriorityQueue<Visitable> locationPreference;
    private final City city;

    public TravelPlan(List locationToVisit, City city) {
        this.locationToVisit = locationToVisit;
        this.city = city;
        this.locationPreference = city.getVisitableLocations();
    }

    public void addPreference(Visitable location, Integer preference) {
        location.setPriority(preference);
    }

    public void createTravelPlan(Visitable locStart, Visitable locEnd) {

    }

}
