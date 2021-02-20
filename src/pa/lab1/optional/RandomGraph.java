package pa.lab1.optional;

import pa.lab1.helper.Helper;

import java.util.Arrays;

public class RandomGraph {

    /**
     * the number of nodes
     */
    public int n;
    private int[][] adjacentMatrix;
    private boolean isConnected;

    Helper helper;

    /**
     * Creates our random matrix of adjacent nodes
     * @param n_ - the length of the squared matrix and the number of nodes
     */
    RandomGraph(int n_) {
        this.n = n_;
        this.helper = new Helper();

        this.adjacentMatrix = new int[this.n][this.n];
        for(int i = 0; i < this.n; i++) {
            for( int ii = 0; ii < this.n; ii++) {
                this.adjacentMatrix[i][ii] = 0;
            }
        }

        for(int i = 0; i < this.n; i++) {
            int[] row  = new int[this.n];
            for(int ii = i+1; ii < this.n; ii++) {
                if(ii != i) {
                    final int isLinked = helper.getRandomNumber(0, 1);
                    this.adjacentMatrix[i][ii] = isLinked;
                    this.adjacentMatrix[ii][i] = isLinked;
                }
            }
        }

        this.isConnected = this.checkIfConnected();
    }
    public void printMatrix() {
        this.helper.printAdjacentMatrix(this.n, this.adjacentMatrix);
    }

    private void dfs(int nodeIndex, boolean visited[], boolean usePrinting) {
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

    public void printConnectedComponents() {
        System.out.printf("\n------ Printing connected components -START-\n");

        boolean visited[] = new boolean[this.n];

        int startNode = 0;
        do {
            System.out.printf("Group starting from node %d: \n", startNode + 1);

            this.dfs(startNode, visited, true);
            startNode = -1;
            for(int i = 0; i < this.n; i++) {
                if(!visited[i]) {
                    startNode = i;
                    break;
                }
            }

            System.out.printf("\n");
        } while(startNode > -1);
        System.out.printf("------ Printing connected components --END--\n");
    }

    public boolean checkIfConnected() {
        boolean visited[] = new boolean[this.n];

        int startNode = 0;
        do {
            this.dfs(startNode, visited, false);
            startNode = -1;
            for(int i = 0; i < this.n; i++) {
                if(!visited[i]) {
                    return false;
                }
            }
        } while(startNode > -1);

        return true;
    }

    public boolean isConnected() {
        return this.isConnected;
    }
}
