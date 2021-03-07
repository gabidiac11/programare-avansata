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
