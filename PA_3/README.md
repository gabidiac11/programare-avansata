# Lab 3
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

This piece is aiming at offering a better understanding concerning the following aspects of the source code:

- problem-solving strategies that were used
- justification for choices made regarding object modeling 
- what exactly are the exercises that I assume are completed (~✔️), partially completed (⏰) or are not started (❌)

## Addressed exercises 
### Compulsory (1p) 
The main bulk of these part is placed in `pa.lab3.location`, which has a sub-package `pa.lab3.location.interfaces`. The class modeling of the problem is representated in this uml schema (which does not offer all, but mostly all methods and properties neccessary for understanding what has been tried for compulsory):


[![N|Solid](https://github.com/gabidiac11/programare-avansata/blob/main/PA_3/compulsory_diagram.png)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_3/compulsory_diagram.png)




Requirements and their status:

 #### - Create an object-oriented model of the problem. You should have at least the following classes City, Hotel, Museum, Church, Restaurant. The natural ordering of their objects is given by their names.~✔️
 #### - Create the interfaces Visitable, Payable, Classifiable. The classes above must implement these interfaces accordingly.~✔️
 This can be observed in the uml diagram. I used getters and settters like methods to force the classes to implement their own properties (classes implementing Visitable need to provide a program, Payable - a fee, Classifiable - a rank). The Program class (`pa.lab3.program`) acts like a placeholder for a 7 week long list of intervals which marks when a location is open. This is done using WeekDay enum (with values like MONDAY, TUESDAY, etc.) and the classes Interval and Time (represents the time using hour, minute, seconds properties). WeekDay and Interval are paired using Map `java.util.Map`. All the classes regarding the program are placed into `pa.lab3.program`.
 
 ````java
  //Program.java
  private Map<WeekDay, Interval[]> programPerDay;
 ````


 #### - The City class will contain a List of locations.~✔️
 #### - Each location will contain a Map representing the times required to go from this location to others.~✔️
 #### - Create all the objects given in the example.~✔️
  In the `pa.lab3.compulsory.CityInitializer.createATestCity()`, I created the City object with the list of locations as was required:
 
```java
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
```

### Optional (2p) 
For this section I used multiple packages, but the example is made in the main function of the `pa.lab3.optional`. 

Requirements and their status:

 #### - In the City class, create a method to display the locations that are visitable and are not payable, sorted by their opening hour.~✔️
   The `Location` class has the abtract boolean methods `isVisitable` and `isPayable`. Because the location to be printed are visitable I created a list of Visitable typed instances results and for the interface a static compartor methods compareByOpeningHour. 
   
  ```java
  /**
     * print all the city names that are visitable, but not payable (with the program attached)
     * sorted by opening hour
     *
     * Example:
         * Location visitable, but not payable:
         * ===========================================
         * v4 (CHURCH)
         * PROGRAM:
         *       MONDAY:  [09:30:00 - 20:00:00]
         *      TUESDAY:  [10:30:00 - 15:00:00] [17:30:00 - 20:00:00]
         *    WEDNESDAY:  [10:30:00 - 12:00:00]
         *     THURSDAY:  [12:30:00 - 16:00:00]
         *       FRIDAY:  [14:30:00 - 21:00:00]
         *     SATURDAY:  [11:30:00 - 17:00:00]
         *       SUNDAY:  CLOSED
         *
         *  ....(etc.)....
     */
    public void printVisitableButNotPayable() {
        System.out.printf("Location visitable, but not payable:\n===========================================\n");
        List locationResults = new ArrayList<Visitable>();

        for(int i = 0; i < this.locations.length; i++) {
            if(this.locations[i].isVisitable() && !this.locations[i].isPayable()) {
                locationResults.add(this.locations[i]);
            }
        }

        locationResults.sort((a, b) -> Visitable.compareByOpeningHour((Visitable) a, (Visitable) b));

        if(locationResults.size() == 0) {
            System.out.printf("No location.\n");
        } else {
            for (Object locationResult : locationResults) {
                Visitable currentLocation = (Visitable) locationResult;
                System.out.printf("%s (%s)\n%s \n", currentLocation.getName(), currentLocation.getSpecialization(), currentLocation.getProgram().toString());
            }
        }
        System.out.printf("===========================================\n");
    }
  ```
  
  Every class that implements Visitable needs to have a Program. In the Program class I implemented a method that checks what is the first day that these location are open. If the day is the same, it generates instances of Time (`pa.lab3.program.Time` - keeps hours, minutes, seconds) for each program. These times are compared using the static method `Time.compareIntervals`, that creates `java.sql.Timestamp` instances to help compare Time instances.
  
    ```java
    private static Timestamp timeToTimestamp(Time time) {
        return Timestamp.valueOf(String.format("1953-03-05 %s", time.toString()));
    }
    
    /**
     * compare 2 Time instances using the java Timestamp
     * @param time1
     * @param time2
     * @return - (-1 | 0 | 1)
     */
    public static int compareIntervals(Time time1, Time time2) {
        Timestamp timeStamp1 = timeToTimestamp(time1);
        Timestamp timeStamp2 = timeToTimestamp(time2);

        return timeStamp1.compareTo(timeStamp2);
    }
    /**
     * gets a Duration instance consisting of amount of time betwee 2 Time instances using the java Timestamp
     * @param time1
     * @param time2
     * @return - Duration instance
     */
    public static Duration getDurationBetween(Time time1, Time time2) {
        Timestamp timeStamp1 = timeToTimestamp(time1);
        Timestamp timeStamp2 = timeToTimestamp(time2);

        return Duration.between(timeStamp1.toLocalDateTime(), timeStamp1.toLocalDateTime());
    }
      ```
 
