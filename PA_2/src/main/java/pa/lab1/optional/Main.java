package pa.lab1.optional;

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

        /* create sources */
        Source[] sources = {
          new Factory(10),
          new Warehouse(35),
          new Warehouse(25)
        };

        Solution problem1 = new Solution(
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
                sources
        );

        System.out.printf("%s", problem1.toString());
    }
}
