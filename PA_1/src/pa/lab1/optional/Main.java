package pa.lab1.optional;

public class Main {
    public static void main(String[] args) {
        RandomGraph myGraph = new RandomGraph(4);

        if(myGraph.n <= 100) {
            myGraph.printMatrix();
        } else {
            System.out.printf("This graph is too big to be printed \n");
        }

        myGraph.printConnectedComponents();

        System.out.printf("\n");
        if(myGraph.isConnected()) {
            System.out.printf("This graph is connected \n");
        } else {
            System.out.printf("This graph is NOT connected. \n");
        }

        System.out.printf("\nThe running time of the application in nanoseconds:\n%f \n", (double) System.nanoTime());
    }
}
