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

 #### 1. Create an object-oriented model of the problem. You should have (at least) the following classes: Source, Destination, Problem.~✔️
     The class Problem solves the problem using a matrix for the cost between sources and destinations, a list of Source and Destination instances as its data members. These values are assign simple arrays:
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
        The problem method that solves the problem is called in the constructor. This method create instances of a private inner class named ResultLine. This inner class stores data about a transportation: units, cost, signature of a source (S1, S2 etc.), signature of a destination (D1, D2, etc.). It helps the toString method of Problem class to print the results. The class implements `java.lang.Comparable` which make its instances comparable by source name and can be sorted usign `java.util.Arrays.sort()`. ResultLine.toString()
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

 - The sources and the destinations have names. The sources will also have the property type. The available types will be implemented as an enum . For example:
        public enum SourceType {
            WAREHOUSE, FACTORY;
        }

Assume S1 is a factory and S2, S3 are warehouses.
Each class should have appropriate constructors, getters and setters.
Use the IDE features for code generation, such as generating getters and setters.
The toString method form the Object class must be properly overridden for all the classes.
Use the IDE features for code generation, for example (in NetBeans) press Alt+Ins or invoke the context menu, select "Insert Code" and then "toString()" (or simply start typing "toString" and then press Ctrl+Space).
Create and print on the screen the instance of the problem described in the example.



- Display on the screen the message "Hello World!". Run the application. If it works, go to step 2 :) ~✔️
- Define an array of strings languages, containing {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"} ~✔️

```java
        System.out.println("Hello world!");

        String[] languages = {"C", "C++", "C#", "Python", "Go", "Rust", "JavaScript", "PHP", "Swift", "Java"};

        int n = (int) (Math.random() * 1000000);
        System.out.printf("Random number: %d \n", n);
```

- Compute the result obtained (n) after performing the following calculations:
      multiply n by 3;
      add the binary number 10101 to the result;
      add the hexadecimal number FF to the result;
      multiply the result by 6; ~✔️
```java
        n = n * 3 + Integer.parseInt("10101",2) + Integer.parseInt("FF",16);
        n *= 6;
        System.out.printf("Random number after operations: %d \n", n);
```
- Compute the sum of the digits in the result obtained in the previous step. This is the new result. While the new result has more than one digit, continue to sum the digits of the result. ~✔️

For this operation I used the tactic of parsing the number into a String as I'm more used to work with strings in Javascript, which has a lot of methods (reduce, map, slice, etc.) that I like and are found in Java as well. I don't think is the best choice. This would be better done using division, but I was curious about using String methods in Java. 
```java
        String[] digits = Integer.toString(n).split("");
        int sum = 0;
        for (String aDigit: digits) {
            sum += Integer.parseInt(aDigit);
            if(sum / 10 == 0) {
                break;
            }
        }
        System.out.printf("Sum of digits for the above number: %d \n", sum);
```

- Display on the screen the message: "Willy-nilly, this semester I will learn " + languages[result]. ~✔️
```java
        int result = sum;
        System.out.printf("Willy-nilly, this semester I will learn %s \n", languages[result]);
```

Output of the pa.lab1.optional.Main.main():
````
Hello world!
Random number: 340191 
The random number after operations: 6125094 
Sum of digits for the above number: 6 
Willy-nilly, this semester I will learn JavaScript 

Process finished with exit code 0
`````
