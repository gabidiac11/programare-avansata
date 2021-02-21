package pa.lab1.optional;

import pa.lab1.helper.Helper;

public class RandomGraph {

    /**
     * the number of nodes
     */
    public int n;
    private final int[][] adjacentMatrix;

    /**
     * indicates if the graph is connected
     */
    private final boolean isConnected;

    /**
     * helps this class to do generic actions like printing or other that are very not related with this class
     */
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

    /**
     * prints a matrix's rows and column
     * for more graphic details please go to definition of helper.Helper.printAdjacentMatrix
     */
    public void printMatrix() {
        this.helper.printAdjacentMatrix(this.n, this.adjacentMatrix);
    }

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

    /**
     * is used to initialized the isConnected property - which indicated if the graph is connected
     * giving the fact that the graph structure is not modifiable after the constructor
     * @return - true if connected; false if not connected
     */
    private boolean checkIfConnected() {
        boolean[] visited;
        visited = new boolean[this.n];

        int startNode = 0;
        do {
            this.dfs(startNode, visited, false);

            /*
             * if an unvisited node is found after dfs -> there is sure > 1 number of connected components
             */
            startNode = -1;
            for(int i = 0; i < this.n; i++) {
                if(!visited[i]) {
                    return false;
                }
            }
        } while(startNode > -1);

        return true;
    }

    /**
     * gives access to a private property from the outside (is a just getter)
     * due to the graph structure can't be changed after, isConnected is assigned in the constructor with the final result
     * @return - true if connected; false if not connected
     */
    public boolean isConnected() {
        return this.isConnected;
    }
}
