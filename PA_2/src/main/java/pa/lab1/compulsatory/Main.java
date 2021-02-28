package pa.lab1.compulsatory;

import java.lang.reflect.Array;

public class Main {

    public static void main(String[] args) {
        /*
            Problem class takes care of solving the transportation problem:

            	D1	D2	D3	Supply
            S1	2	3	1	10
            S2	5	4	8	35
            S3	5	6	8	25
        Demand	20	25	25

            (the above table is a representation of the bellow example:)
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

        System.out.printf("%s", problem1.toString());
    }

}
