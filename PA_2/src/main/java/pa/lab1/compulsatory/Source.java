package pa.lab1.compulsatory;

import java.util.Objects;

public class Source {
    /**
     * helps generate a unique name for a instance using an index
     */
    private static int numOfInstances;
    static
    {
        numOfInstances = 1;
    }

    private final String signature;
    private final SourceType type;
    private final int initialSupply;
    private int currentSupply;

    public Source(SourceType type, int supply) {
        this.type = type;
        this.signature = this.generateUniqueSignature();
        this.initialSupply = supply;
        this.currentSupply = supply;
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
     * @return - indicates if the supply is consumed completely
     */
    public boolean isExhausted() {
        return this.currentSupply <= 0;
    }

    /**
     *
     * @return - SourceType enum: WAREHOUSE, FACTORY
     */
    public SourceType getType() {
        return type;
    }

    public int getSupply() {
        return this.currentSupply;
    }

    /**
     *
     * @return - the unique name of the source
     */
    public String getSignature() {
        return signature;
    }

    /**
     * subtracts from demand as much as the source can provide
     * @param demand - amount to fulfill
     * @return - how much has contributed
     */
    public int contributeToDemand(int demand) {
        if(!this.isExhausted()) {
            int supply = this.getSupply();
            int contribution  = 0;
            if(supply < demand) {
                contribution = supply;
            } else {
                contribution = demand;
            }

            this.currentSupply = supply - contribution;

            return contribution;
        }
        return 0;
    }


    /**
     * used for testing
     * @return - a string representation for its members
     */
    @Override
    public String toString() {
        return "Source{" +
                "signature='" + signature + '\'' +
                ", type=" + type +
                ", initialSupply=" + initialSupply +
                ", currentSupply=" + currentSupply +
                '}' + '\n';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Source source = (Source) o;
        return initialSupply == source.initialSupply && currentSupply == source.currentSupply && Objects.equals(signature, source.signature) && type == source.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(signature, type, initialSupply, currentSupply);
    }
}
