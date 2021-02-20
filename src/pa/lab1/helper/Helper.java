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

    public void printAdjacentMatrix(int length, int adjacentMatrix[][]) {
        System.out.printf("  ");
        for(int i = 0; i < length; i++) {
            System.out.printf("%d ", i+1);
        }
        System.out.printf("\n");

        for(int i = 0; i < length; i++) {
            System.out.printf("%d ", i+1);

            for(int ii = 0; ii < length; ii++) {
                if(adjacentMatrix[i][ii] == 1) {
                    System.out.printf("%c ", (char) 10070);
                } else {
                    System.out.printf("%c ", (char) 12599);
                }
            }
            System.out.printf("\n");
        }
    }
}

