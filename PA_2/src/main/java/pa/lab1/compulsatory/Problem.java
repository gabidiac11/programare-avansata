package pa.lab1.compulsatory;

import java.lang.Comparable;
import java.util.Arrays;

public class Problem {
    /**
     * helps generate a unique name for a instance using an index
     */
    private static int numOfProblems;
    static
    {
        numOfProblems = 1;
    }

    /**
     * this class is mainly used for the toString method of the parent class
     *
     * is also sortable using Arrays.sort using the signature of the destination
     */
    private class ResultLine implements Comparable<ResultLine> {
        private final String sourceSignature;
        private final String destinationSignature;
        private final int units;
        private final int cost;

        /**
         *
         * @param sourceSignature - signature name of the Source instance that took part in this transportation
         * @param destinationSignature - signature name of the Destination instance that took part in this transportation
         * @param units -  number of units transported
         * @param cost - the cost of a transport of a unit
         */
        public ResultLine(String sourceSignature, String destinationSignature, int units, int cost) {
            this.sourceSignature = sourceSignature;
            this.destinationSignature = destinationSignature;
            this.units = units;
            this.cost = cost;
        }

        /**
         * helps toString implementation of its parent class
         * @return - string representation of a transportation line (looks like this: "$source -> $destination: $units units * cost $cost = $result")
         */
        @Override
        public String toString() {
            return (
                    String.format("%s -> %s: %d units * cost %d = %d",
                            this.sourceSignature,
                            this.destinationSignature,
                            this.units,
                            this.cost,
                            this.units * this.cost
                    )
            );
        }

        /**
         *
         * @return - how much this line of transportation consumed
         */
        public int getTotalCost() {
            return this.cost * this.units;
        }

        /**
         * helps Arrays implement the its sort functionality
         * @param line - an instance of this class
         * @return - the sum of char values that is used for comparison in sorting
         */
        @Override
        public int compareTo(ResultLine line) {
            int sum = 0;
            for(int i = 0; i < line.sourceSignature.length(); i++) {
                sum += (int) line.sourceSignature.charAt(i);
            }
            return sum;
        }
    }

    private Destination[] destinations;
    private Source[] sources;
    private int[][] costMatrix;
    private ResultLine[] resultLines = new ResultLine[0];
    /* unique signature of a Problem instance */
    private final String signature;

    /**
     * The Transportation Problem
        An instance of the Transportation Problem consists of source and destinations.

        Each source has a given capacity, i.e. how many units of a commodity it is able to supply to the destinations.
        Each destination demands a certain amount of commodities.
        The cost of transporting a unit of commodity from each source to each destination
      is given by a cost matrix (or function).
        We consider the problem of determining the quantities to be transported from sources to destinations, in order to minimize the total transportation cost. The supply and demand constraints must be satisfied. (We may assume that all the values are integer).
        Problem class takes care of solving a transportation problem.
     Visual example:
            D1	D2	D3	Supply
         S1	2	3	1	10
         S2	5	4	8	35
         S3	5	6	8	25
     Demand	20	25	25
     * @param costMatrix - the matrix of cost between sources and demands (the matrix {S_i, D_i} from the visual exemple)
     * @param supplyAmounts - the source supply values (the last column from the example, {10, 35, 25})
     * @param demandAmounts - the demand values (the last row, {20	25	25} in the example)
     * @param sourceTypes - the source type represented by an enum of SourceType - WAREHOUSE, FACTORY
     */
    public Problem(int[][] costMatrix, int[] supplyAmounts, int[] demandAmounts, SourceType[] sourceTypes) {
        this.signature = String.format("Problem%d", numOfProblems);
        numOfProblems ++;

        this.assignDestinations(demandAmounts);
        this.assignSources(supplyAmounts, sourceTypes);
        this.costMatrix = costMatrix;

        this.solveTheProblem();
    }

    /**
     * initializes the destination array needed
     * @param demandAmounts - values for the destination demands
     */
    void assignDestinations(int[] demandAmounts) {
        this.destinations = new Destination[demandAmounts.length];
        for(int i = 0; i < demandAmounts.length; i++) {
            this.destinations[i] = new Destination(demandAmounts[i]);
        }
    }

    /**
     * initializes the source array needed
     * @param supplyAmounts - values for the source supply
     * @param sourceTypes - values for each source in order to be assign a type
     */
    void assignSources(int[] supplyAmounts, SourceType[] sourceTypes) {
        this.sources = new Source[supplyAmounts.length];
        for(int i = 0; i < supplyAmounts.length; i++) {
            this.sources[i] = new Source(sourceTypes[i], supplyAmounts[i]);
        }
    }

    /**
     * finds the cost minimal road to the transportation problem
     * the "road" is represented by a the ResultLine class instance (where the units, costs of a trip between a source and destination is made)
     */
    void solveTheProblem() {
        boolean allSourceExhausted = false;
        boolean allDestinationFulFilled = false;


        boolean[][] matrixVisited = new boolean[this.sources.length][this.sources.length];
        for(int i = 0; i < matrixVisited.length; i++) {
            for(int j = 0; j < matrixVisited.length; j++) {
                matrixVisited[i][j] = false;
            }
        }

        do {

            this.calculateNextResultLine(matrixVisited);

            /*
             * continue until the demand was fulfilled for all destinations
             * or until all sources are empty
             */
            allSourceExhausted = true;
            allDestinationFulFilled = true;
            for(int i = 0; i < destinations.length; i++) {
                if(!this.destinations[i].isFulfilled()) {
                    allDestinationFulFilled = false;
                }

                if(!this.sources[i].isExhausted()) {
                    allSourceExhausted = false;
                }
            }
        } while(!allDestinationFulFilled && !allSourceExhausted);

        //dummySolveTheProblem();
    }

    /**
     *  finds the next minimal road from a source to a destination with a minimal cost per unit
     *  the "road" is represented by a the ResultLine class instance (where the units, costs of a trip between a source and destination is made)
     * @param matrixVisited - marks what cost are already used in the cost matrix
     */
    private void calculateNextResultLine(boolean[][] matrixVisited) {
        int sourceIndex = -1;
        int destIndex = -1;
        int maxCostPerUnit = Integer.MIN_VALUE;

        /*
          find the {i, j} values corresponding to the minimal cost per unit
          between any source and destination in the cost matrix
        */
        for(int i = 0; i < this.costMatrix.length; i++) {
            final int supply = this.sources[i].getSupply();

            for(int j = 0; !this.sources[i].isExhausted() && j < this.costMatrix.length; j++) {
                /* make sure the destination still has demand and the source still has supply */
                if(
                  !matrixVisited[i][j] &&
                  !this.destinations[j].isFulfilled() &&
                  supply / this.costMatrix[i][j] > maxCostPerUnit
                ) {
                    maxCostPerUnit = supply / this.costMatrix[i][j];
                    sourceIndex = i;
                    destIndex = j;
                }
            }
        }

        if(sourceIndex >= 0 && destIndex >= 0) {


            final int cost = this.costMatrix[sourceIndex][destIndex];
            int demand  = this.destinations[destIndex].getDemand();
            /* transport the soruce and get how many units have been transported */
            final int units = this.sources[sourceIndex].contributeToDemand(demand);

            this.destinations[destIndex].setDemand(demand - units);
            matrixVisited[sourceIndex][destIndex] = true;

            /* push transport to result lines */
            this.pushToResultLines(
                   new ResultLine(this.sources[sourceIndex].getSignature(),
                    this.destinations[destIndex].getSignature(),
                    units,
                    cost
                   )
            );
        }
    }

    /**
     * dynamically push a new result line into the instance
     * @param newLine - a instance of ResultLine
     */
    private void pushToResultLines(ResultLine newLine) {
        ResultLine[] resultLines = new ResultLine[this.resultLines.length + 1];

        /* copy the list that already exists in the bigger array */
        System.arraycopy(this.resultLines, 0, resultLines, 0, this.resultLines.length);

        /* place a new item */
        resultLines[this.resultLines.length] = newLine;
        this.resultLines = resultLines;
    }

    /**
     * sort by source signature name the results
     */
    private void sortResultLines() {
        Arrays.sort(this.resultLines);
    }

    public String getSignature() {
        return signature;
    }

    /**
     * used to test the toString function (it was called in this.solveTheProblem method)
     */
    private void dummySolveTheProblem() {
        this.resultLines = new ResultLine[5];
        this.resultLines[0] = new ResultLine("S1", "D3", 10, 1);
        this.resultLines[1] = new ResultLine("S2", "D2", 25, 4);
        this.resultLines[2] = new ResultLine("S2", "D3", 10, 8);
        this.resultLines[3] = new ResultLine("S3", "D1", 20, 5);
        this.resultLines[4] = new ResultLine("S3", "D3", 5, 8);
    }

    /**
     * prints the results and makes use of the ResultLine toString method as well and it's unit and cost members to display the cost
     * @return - a string representation like this:
            Problem1 results:
            D1 -> D3: 10 units * cost 1 = 10
            D2 -> D2: 25 units * cost 4 = 100
            D3 -> D1: 20 units * cost 5 = 100
            D2 -> D3: 10 units * cost 8 = 80
            D3 -> D3: 5 units * cost 8 = 40
            Problem1 final cost: 330
     */
    @Override
    public String toString() {
        String value = String.format("\n%s results:", this.signature);

        int totalCost = 0;

        for(int i = 0; i < this.resultLines.length; i++) {
            totalCost += this.resultLines[i].getTotalCost();

            value = String.format("%s\n%s", value, this.resultLines[i].toString());
        }

        String stringEnd = String.format("%s final cost: %d\n", this.signature, totalCost);
        value = String.format("%s\n%s", value, stringEnd);

        return value;
    }
}
