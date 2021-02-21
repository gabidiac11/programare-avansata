package pa.lab1.bonus;

import pa.lab1.helper.Helper;

public class RandomTree {
    /**
     * the number of nodes
     */
    public int n;
    private int[][] adjacentMatrix;

    /**
     * helps this class to do generic actions like printing or other that are not related to this class
     */
    Helper helper;

    /**
     * Creates our random matrix of adjacent nodes resulting in a tree
     * @param n_ - the length of the squared matrix and the number of nodes
     */
    RandomTree(int n_) {
        this.n = n_;
        this.helper = new Helper();

        /*
         * first thing we do is creating a regular graph
         */
        int[][] matrix = new int[this.n][this.n];

        /*
         * we will use this later to mark the connections for the final matrix
         */
        int[][] dfsMarkedMatrix = new int[this.n][this.n];

        /*
         * initialize all elements as 0
         */
        for(int i = 0; i < this.n; i++) {
            for( int ii = 0; ii < this.n; ii++) {
                matrix[i][ii] = 0;
                dfsMarkedMatrix[i][ii] = 0;
            }
        }
        /*
         * generate random connection for each combination of vertex
         */
        for(int i = 0; i < this.n; i++) {
            int[] row  = new int[this.n];
            for(int ii = i+1; ii < this.n; ii++) {
                if(ii != i) {
                    final int isLinked = helper.getRandomNumber(0, 1);
                    matrix[i][ii] = isLinked;
                    matrix[ii][i] = isLinked;
                }
            }
        }

        /*
         * we create a tree from a dfs
         * and connect the graph if is the case
         */
        boolean visited[] = new boolean[this.n];

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
             * connect the previous connected component with the following one if is any other
             */
            if(startNode > -1) {
                dfsMarkedMatrix[startNode][prevIndex] = 1;
                dfsMarkedMatrix[prevIndex][startNode] = 1;
            }
        } while(startNode > -1);

        this.adjacentMatrix = dfsMarkedMatrix;
    }

    /**
     * prints a matrix's rows and column
     * for more graphic details please go to definition of helper.Helper.printAdjacentMatrix
     */
    public void printMatrix() {
      this.helper.printAdjacentMatrix(this.n, this.adjacentMatrix);
    }

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

    /**
     * prints a "x1 <-> x2" lines where x1 and x2 are adjacent nodes
     * used for better sight of the nodes connection (for testing)
     */
    public  void printEdges() {
        this.helper.printAdjacentMatrixEdges(this.n, this.adjacentMatrix);
    }

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
}
