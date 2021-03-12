package pa.lab3.location;

import pa.lab3.graph.NodeComparator;
import pa.lab3.location.interfaces.Visitable;
import pa.lab3.program.Program;

public class Church extends Location implements Visitable {
    private Program program;

    public Church(String name, String description, String image, float latitude, float longitude, Program program) {
        super(name, description, image, latitude, longitude);
        this.setProgram(program);
    }

    @Override
    public void setProgram(Program program) {
        this.program = program;
    }

    @Override
    public Program getProgram() {
        return this.program;
    }

    @Override
    public boolean isVisitable() {
        return true;
    }

    @Override
    public boolean isPayable() {
        return false;
    }

    @Override
    public boolean isClassifiable() {
        return false;
    }

    @Override
    public String getSpecialization() {
        return "CHURCH";
    }

    @Override
    public int compare(Visitable o1, Visitable o2) {
        return Integer.compare(o1.getPriority(), o2.getPriority());
    }

    @Override
    public int getCost(NodeComparator object) {
        return Location.getCost(this, (Location) object);
    }

    @Override
    public String nodeToString() {
        return String.format("Name: %s, Priority: %d", this.getName(), this.getPriority());
    }

    @Override
    public int compareTo(Visitable o) {
        return Integer.compare(this.getPriority(), o.getPriority());
    }

    @Override
    public int compareToNode(NodeComparator subject) {
        //TODO: check type
        return this.compareTo((Visitable) subject);
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        if (!super.equals(o)) return false;
//        Church church = (Church) o;
//        return Objects.equals(program, church.program) && super.equals(o) && super.equals(o);
//    }
//
//    @Override
//    public int hashCode() {
//        return super.hashCode();
//    }
}
