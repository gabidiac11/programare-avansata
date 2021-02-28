# Lab 1
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
The code around this section of the lab is placed in the package named `pa.lab1.compulsory`. It has a the following classes: Main, Problem, Source, Destination and the enum SourceType. 

I believe I followed through all the requirements:

 #### Create an object-oriented model of the problem. You should have (at least) the following classes: Source, Destination, Problem.~✔️
 The class Problem solves the problem using a matrix marking the cost between sources and destinations, a list of Source and Destination instances as its data members. These values are assign using simple arrays:
```java
        /*
            	D1	D2	D3	Supply
            S1	2	3	1	10
            S2	5	4	8	35
            S3	5	6	8	25
        Demand	20	25	25
            The above table is a representation of the bellow example:
         */
        Problem problem1 = new Problem(
                /* the matrix of cost between sources and demands */
                new int[][]{
                        {2,3,1},
                        {5,4,8},
                        {5,6,8}
                },
                /* the source supply values */
                new int[]{10, 35, 25},
                /* the demand values */
                new int[]{20, 25, 25},
                /* the source type represented by an enum */
                new SourceType[] {SourceType.FACTORY, SourceType.WAREHOUSE, SourceType.WAREHOUSE}
        );
```
   The method that solves the actual problem is called in the constructor. This method creates instances of a private inner class named ResultLine. This inner class stores data about a transport line: units, cost, signature of the source (S1, S2 etc.), signature of the destination (D1, D2, etc.). It helps the Problem.toString method to print the results. The class implements `java.lang.Comparable` which make its instances comparable by source name and can be sorted usign `java.util.Arrays.sort()`. ResultLine.toString :
```java
        @Override
        public String toString() {
            return (
                    String.format("%s -> %s: %d units * cost %d = %d",
                            this.sourceSignature,
                            this.destinationSignature,
                            this.units,
                            this.cost,
                            this.units * this.cost
                    )
            );
        }
```

 #### The sources and the destinations have names. The sources will also have the property type. The available types will be implemented as an enum.~✔️
  
 SourceType.java:
```java
public enum SourceType {
    WAREHOUSE, FACTORY;
}
```
 The names of the Destination and Source instances are uniquely generated using a static member.
```java
    /**
     * helps generate a unique name for a instance using an index
     */
    private static int numOfInstances;
    static
    {
        numOfInstances = 1;
    }
    
    /* ..... */
    
    /**
     *
     * @return - unique name based on the number of this class instances
     */
    private String generateUniqueSignature() {
        numOfInstances ++;
        return String.format("D%d", numOfInstances - 1);
    }
```

#### Assume S1 is a factory and S2, S3 are warehouses. ~✔️
 This is done via the single Problem class constructor using the last parameter (exemple above).
#### Each class should have appropriate constructors, getters and setters. ~✔️
#### Use the IDE features for code generation, such as generating getters and setters. ~✔️
#### The toString method form the Object class must be properly overridden for all the classes. ~✔️
#### Use the IDE features for code generation, for example (in NetBeans) press Alt+Ins or invoke the context menu, select "Insert Code" and then "toString()" (or simply start typing "toString" and then press Ctrl+Space). ~✔️
#### Create and print on the screen the instance of the problem described in the example. ~✔️

```java
   /**
     * prints the results and makes use of the ResultLine toString method as well and it's unit and cost members to display the cost
     * @return - a string representation like this:
            Problem1 results:
            D1 -> D3: 10 units * cost 1 = 10
            D2 -> D2: 25 units * cost 4 = 100
            D3 -> D1: 20 units * cost 5 = 100
            D2 -> D3: 10 units * cost 8 = 80
            D3 -> D3: 5 units * cost 8 = 40
            Problem1 final cost: 330
     */
    @Override
    public String toString() {
        String value = String.format("\n%s results:", this.signature);

        int totalCost = 0;

        for(int i = 0; i < this.resultLines.length; i++) {
            totalCost += this.resultLines[i].getTotalCost();

            value = String.format("%s\n%s", value, this.resultLines[i].toString());
        }

        String stringEnd = String.format("%s final cost: %d\n", this.signature, totalCost);
        value = String.format("%s\n%s", value, stringEnd);

        return value;
    }
```
