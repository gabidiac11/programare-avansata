# Lab 1
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

This piece is aiming at offering a better understanding concerning the following aspects of the source code:

- problem-solving strategies that were used
- justification for choices made regarding object modeling 
- what exactly are the exercises that are completed or partially completed

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

### Optional (2p)
Requirements:
- Let n be an integer given as a command line argument. Validate the argument!
Create a n x n matrix, representing the adjacency matrix of a random graph .
Display on the screen the generated matrix (you might want to use the geometric shapes from the Unicode chart to create a "pretty" representation of the matrix).
Verify if the generated graph is connected and display the connected components (if it is not).~✔️
- Assuming that the generated graph is connected, implement an algorithm that creates a partial tree of the graph. Display the adjacency matrix of the tree. 
(***I did implement this for the bonus section) [??]
- For larger n display the running time of the application in nanoseconds (DO NOT display the matrices). Try n > 30_000. You might want to adjust the JVM Heap Space using the VM options -Xms4G -Xmx4G. ~✔️
- Launch the application from the command line, for example: java Lab1 100. ❌

A package named `pa.lab1.optional` was dedicated to this chapter. This time I choose to create a class name "RandomGraph", alluding to the way the graph is generated. Below are the main properties that characterize this class:

```java
    public int n; //the number of nodes
    private final int[][] adjacentMatrix;
    private final boolean isConnected;
```
All these properties are only assigned in the constructor. The matrix ought to be of an un-oriented graph.
The constructor takes the job of generating the graph in the form of an adjacent matrix by assigning a random connection between each combination of nodes:
```java
        this.helper = new Helper();
        
        for(int i = 0; i < this.n; i++) {
            int[] row  = new int[this.n];
            for(int ii = i+1; ii < this.n; ii++) {
                if(ii != i) {
                    //generates an integer in [0, 1]
                    final int isLinked = helper.getRandomNumber(0, 1);
                    this.adjacentMatrix[i][ii] = isLinked;
                    this.adjacentMatrix[ii][i] = isLinked;
                }
            }
        }
```

The Helper class instance is used for operations that are not necessarily related to RandomClass. Its functionality is shared with another class. Besides generating a random number, it's also used to pretty-print the matrix. The location of this class is `pa.lab1.helper`.
````java
/**
     * prints a matrix's rows and column
     *  Exemple: 7 x 7 matrix (connection is highlighted by a ✪)
     *
     *            ✶ ✶ ✪ ✶ ✶ ✶ ✶
     *            ✶ ✶ ✪ ✪ ✶ ✶ ✶
     *            ✪ ✪ ✶ ✶ ✶ ✶ ✶
     *            ✶ ✪ ✶ ✶ ✪ ✪ ✪
     *            ✶ ✶ ✶ ✪ ✶ ✶ ✶
     *            ✶ ✶ ✶ ✪ ✶ ✶ ✶
     *            ✶ ✶ ✶ ✪ ✶ ✶ ✶
     *
     * designed to print up to 99 sized squared matrix
     * @param length - the length of the squared matrix and the number of nodes
     * @param adjacentMatrix - a matrix of un-oriented graph
     */
    public void printAdjacentMatrix(int length, int adjacentMatrix[][]) {
        System.out.printf("\n------ Printing the matrix -START-");

        for(int i = 0; i < length; i++) {
            System.out.printf("\n");

            for(int ii = 0; ii < length; ii++) {
                if(adjacentMatrix[i][ii] == 1) {
                   System.out.printf("%-2c", (char) 10026);
                } else {
                    System.out.printf("%-2c", (char) 10038);
                }
            }
        }

        System.out.printf("\n------ Printing the matrix --END--\n");
    }
````

The functionality around the connected components is based on this implementation of the depth-first search algorithm.

````java
    /**
     * traverses a graph in depth first search and can do a printing of visited nodes
     * @param nodeIndex - the node of whose list of adjacency will be traversed
     * @param visited - the list of node visited already
     * @param usePrinting - choose if you want to use this for printing visited nodes
     */
    private void dfs(int nodeIndex, boolean[] visited, boolean usePrinting) {
        if(usePrinting) {
            System.out.printf("%d ", nodeIndex+1);
        }

        visited[nodeIndex] = true;
        for(int i = 0; i < this.n; i++) {
            if(this.adjacentMatrix[nodeIndex][i] == 1 && !visited[i]) {
                this.dfs(i, visited, usePrinting);
            }
        }
    }
````

The above method is used in the same manner by 2 other members (RandomClass.printConnectedComponents, RandomClass.checkIfConnected - assigns value to the property isConnected, in the constructor). 
I created a simple algorithm that visits each group of connected nodes.

````java
    /**
     * uses the depth first search to print nodes grouped by their membership in a connected component
     */
    public void printConnectedComponents() {
        System.out.print("\n------ Printing connected components -START-\n");

        boolean[] visited = new boolean[this.n];

        int startNode = 0;
        do {
            System.out.printf("Group starting from node %d: \n", startNode + 1);

            this.dfs(startNode, visited, true);

            /*
             * if an unvisited node is found after dfs -> there is sure another connected component
             */
            startNode = -1;
            for(int i = 0; i < this.n; i++) {
                if(!visited[i]) {
                    startNode = i;
                    break;
                }
            }

            System.out.print("\n");
        } while(startNode > -1);

        System.out.print("------ Printing connected components --END--\n");
    }
````

Output of the pa.lab1.optional.Main.main():
````

------ Printing the matrix -START-
✶ ✪ ✪ ✪ 
✪ ✶ ✶ ✪ 
✪ ✶ ✶ ✪ 
✪ ✪ ✪ ✶ 
------ Printing the matrix --END--

------ Printing connected components -START-
Group starting from node 1: 
1 2 4 3 
------ Printing connected components --END--

This graph is connected 

The running time of the application in nanoseconds:
627204448354600.000000 

Process finished with exit code 0
`````

### Bonus (1p) (~✔️)

Requirements:
- Implement an efficient algorithm that generates a random rooted tree. Create and display a textual representation of the tree, for example:
````
+node0
  +node1
    -node2
  +node3
    -node4
    -node5
````
For this section, I used a class that very much resembles the previous one I described, named RandomTree, in the `pa.lab1.bonus` package. The strategy adopted for creating a tree was using the depth-first search sequence of a regular un-oriented graph.
The regular graph is created in the same way as it is in the RandomGraph class. The dfs method is adapted in order to create a new adjacent matrix with the visited connections:

````java
    /**
     * creates a adjacent matrix for a tree using a regular adjacent matrix
     * @param nodeIndex - the node of whose list of adjacency will be traversed
     * @param visited - the list of node visited already
     * @param matrix - the adjacent matrix with a regular graph (no restriction for connections or cycles); the source used for constructing the tree
     * @param finalMatrix - the adjacent matrix of a tree that will be constructed as the functions progresses based on the matrix above
     */
    private void dfs(int nodeIndex, boolean visited[], int matrix[][], int finalMatrix[][]) {
        visited[nodeIndex] = true;
        for(int i = 0; i < this.n; i++) {
            if(matrix[nodeIndex][i] == 1 && !visited[i]) {
                finalMatrix[nodeIndex][i] = 1;
                finalMatrix[i][nodeIndex] = 1;
                this.dfs(i, visited, matrix, finalMatrix);
            }
        }
    }
````

If the graph has multiple connected components a link is created between them:

````java
/*
    in RandomTree.RandomTree(int n_) 
*/
        int startNode = 0;
        do {
            this.dfs(startNode, visited, matrix, dfsMarkedMatrix);

            final int prevIndex = startNode;
            startNode = -1;
            for(int i = 0; i < this.n; i++) {
                if(!visited[i]) {
                    startNode = i;
                    break;
                }
            }

            /*
             * connect the previously connected component with the following one, if is any
             */
            if(startNode > -1) {
                dfsMarkedMatrix[startNode][prevIndex] = 1;
                dfsMarkedMatrix[prevIndex][startNode] = 1;
            }
        } while(startNode > -1);
````
The "hierarchical" printing of the tree is made using the Pre-order Traversal and a dose of indentation:

````java
/**
     * prints a tree using a "folder" like structure (hierarchical)
     * @param indexNode - the node of whose list of adjacency will be traversed
     * @param indent - indicates the number of spaces needed to be printed to visually indicate sub-elements
     * @param visited - the list of node visited already
     */
    private void printThreeAux(int indexNode, int indent, boolean visited[]) {
        visited[indexNode] = true;

        System.out.printf("\n");
        for(int i = 0; i < indent; i++) {
            System.out.printf(" ");
        }
        System.out.printf("+node%d", indexNode + 1);

        for(int i = 0; i < this.n; i++) {
            if(!visited[i] && this.adjacentMatrix[indexNode][i] == 1) {
                printThreeAux(i, indent +1, visited);
            }
        }
    }

    /**
     * serves as a starting point to a function that
     * prints a tree using a "folder" like structure (hierarchical)
     */
    public void printThree() {
        boolean visited[] = new boolean[this.n];
        this.printThreeAux(0, 0, visited);
    }
````
Output of the pa.lab1.bonus.Main.main():
````
------ Printing the matrix -START-
✶ ✪ ✪ ✶ ✶ ✶ ✶ 
✪ ✶ ✶ ✶ ✪ ✶ ✶ 
✪ ✶ ✶ ✶ ✶ ✶ ✶ 
✶ ✶ ✶ ✶ ✪ ✪ ✪ 
✶ ✪ ✶ ✪ ✶ ✶ ✶ 
✶ ✶ ✶ ✪ ✶ ✶ ✶ 
✶ ✶ ✶ ✪ ✶ ✶ ✶ 
------ Printing the matrix --END--

+node1
 +node2
  +node5
   +node4
    +node6
    +node7
 +node3
Process finished with exit code 0
`````

## Other
This is built with Gradle.
