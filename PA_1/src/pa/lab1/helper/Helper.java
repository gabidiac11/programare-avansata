package pa.lab1.helper;

import java.util.Random;

/**
 * this class is used a helper for other classes
 * does isolated operations
 */
public class Helper {
    /*
     **************************************************************************************
     *    Title: Math.random() explanation
     *    Author:  AusCBloke
     *    Date: Nov 1 '11 at 2:29
     *    Code version: -
     *    Availability: https://stackoverflow.com/questions/7961788/math-random-explanation
     *
     **************************************************************************************
     * */
    /**
     * generated a random number
     * @param min - lower limit
     * @param max - upper limit
     * @return - integer that is in [min, max] interval
     */
    public int getRandomNumber(int min, int max) {
        int range = (max - min) + 1;
        /*
         * Math.random() returns a number between zero and one.
         */
        return (int)(Math.random() * range) + min;
    }

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
     * designed to print upp to 99 sized squared matrix
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

    /**
     * prints edges of an un-oriented graph passed as adjacent matrix
     * @param length - the length of the squared matrix and the number of nodes
     * @param adjacentMatrix - a matrix of un-oriented graph
     */
    public void printAdjacentMatrixEdges(int length, int adjacentMatrix[][]) {
        System.out.printf("\nStart printing edges:\n");

        for(int i = 0; i < length; i++) {
            for(int ii = i+1; ii < length; ii++) {
                if(adjacentMatrix[i][ii] == 1) {
                    System.out.printf("%d <-> %d \n", i+1, ii+1);
                }
            }
        }

        System.out.printf("End printing edges.\n\n");
    }

}

