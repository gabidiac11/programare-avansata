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

The code around this section of the lab is placed in the package named `pa.lab1.compulsory`. It has a single class, Main. All the logic is placed in the main method as I didn't feel that the code would get more readable if I split it more.

I believe I followed through all the operations:

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
