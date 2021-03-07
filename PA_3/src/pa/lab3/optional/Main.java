package pa.lab3.optional;

import pa.lab3.compulsory.CityInitializer;
import pa.lab3.location.City;

public class Main {
    public static void main(String[] args) {
        City newCity = CityInitializer.createATestCity();
        newCity.printVisitableButNotPayable();
    }
}
