package pa.lab3.optional;

import lombok.Getter;
import lombok.Setter;
import pa.lab3.location.City;
import pa.lab3.location.Location;
import pa.lab3.location.interfaces.Visitable;

import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;


@Setter
public class TravelPlan {
    private List locationToVisit = new ArrayList<Visitable>();
    private TreeMap locationPreference = new TreeMap<Visitable, Integer>();
    private final City city;

    public TravelPlan(List locationToVisit, City city) {
        this.locationToVisit = locationToVisit;
        this.city = city;
    }

    public void addPreference(Visitable location, Integer preference) {
        locationPreference.put(location, preference);
    }

    private int compareByPreferenceAndStartEnd(Object v1, Object v2, Visitable locStart, Visitable locEnd) {
        v1 = (Visitable) v1;
        v2 = (Visitable) v2;

        /*
         * always place the start location to the front and the last one to the last
         */
        if(v1 == locStart) {
            return 1;
        }
        if(v2 == locStart) {
            return -1;
        }
        if(v1 == locEnd) {
            return -1;
        }
        if(v2 == locEnd) {
            return 1;
        }

        /*
         *  otherwise sort by preference
         */
        Integer preferenceV1 = 0;
        Integer preferenceV2 = 0;

        if(this.locationPreference.containsKey(v1)) {
            preferenceV1 = (Integer) this.locationPreference.get(v1);
        }
        if(this.locationPreference.containsKey(v2)) {
            preferenceV2 = (Integer) this.locationPreference.get(v2);
        }
        int comparisonResult = preferenceV1.compareTo(preferenceV2);

        if(comparisonResult == 0) {
            return comparisonResult;
        }
        return comparisonResult < 0 ? 1 : -1;
    }



    public void createTravelPlan(Visitable locStart, Visitable locEnd) {
        /* sort location by preference */
        this.locationToVisit.sort((v1, v2) -> {
            return this.compareByPreferenceAndStartEnd(v1, v2, locStart, locEnd);
        });



    }

}
