package pa.lab1.compulsatory;

import java.util.Objects;

public class Destination {
    /**
     * helps generate a unique name for a instance using an index
     */
    private static int numOfInstances;
    static
    {
        numOfInstances = 1;
    }

    private final int initialDemand;
    private int currentDemand;
    private final String signature;

    public Destination(int demand) {
        this.initialDemand = demand;
        this.currentDemand = demand;
        this.signature = this.generateUniqueSignature();
    }

    /**
     *
     * @return - unique name based on the number of this class instances
     */
    private String generateUniqueSignature() {
        numOfInstances ++;
        return String.format("D%d", numOfInstances - 1);
    }

    /**
     *
     * @return - demand after multiple arrivals of supply
     */
    public int getDemand() {
        return this.currentDemand;
    }

    /**
     *
     * @return - if the demand is fulfilled
     */
    public boolean isFulfilled() {
        return this.currentDemand <= 0;
    }

    public String getSignature() {
        return signature;
    }

    /**
     * update the demand fulfilled
     * @param currentDemand - after a source contributes to the demand, a lower demand is passed to this function
     */
    public void setDemand(int currentDemand) {
        this.currentDemand = currentDemand;
    }

    /**
     * used for testing
     * @return - a string representation for its members
     */
    @Override
    public String toString() {
        return "Destination{" +
                "initialDemand=" + initialDemand +
                ", currentDemand=" + currentDemand +
                ", signature='" + signature + '\'' +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Destination that = (Destination) o;
        return initialDemand == that.initialDemand && currentDemand == that.currentDemand && Objects.equals(signature, that.signature);
    }

    @Override
    public int hashCode() {
        return Objects.hash(initialDemand, currentDemand, signature);
    }
}
