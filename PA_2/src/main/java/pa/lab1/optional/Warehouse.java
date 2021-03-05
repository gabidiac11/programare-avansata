package pa.lab1.optional;

public class Warehouse extends Source {
    //majuscule - constante
    private static final String type = "WAREHOUSE";

    public Warehouse(int supply) {
        super(supply);
    }

    @Override
    public String getType() {
        return type;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Type %s'}'\n", type);
    }

    /**
     * overwrite equals using the parent class equals function
     * draw the distinction between Warehouse and Factory
     * @param o - an Object
     * @return - shallow equal
     */
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof Warehouse;
    }
}
