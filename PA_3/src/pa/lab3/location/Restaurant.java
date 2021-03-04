package pa.lab3.location;

import pa.lab3.location.interfaces.Classifiable;
import pa.lab3.location.interfaces.Payable;
import pa.lab3.location.interfaces.Visitable;
import pa.lab3.program.Program;

public class Restaurant extends Location implements Visitable, Payable, Classifiable {
    private int rank;
    private float fee;
    private Program program;

    public Restaurant(String name, String description, String image, float latitude, float longitude, int rank, float fee, Program program) {
        super(name, description, image, latitude, longitude);
        this.setRank(rank);
        this.setFee(fee);
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
    public void setFee(float fee) {
        this.fee = fee;
    }

    @Override
    public float getFee() {
        return this.fee;
    }

    @Override
    public void setRank(int rank) {
        this.rank = rank;
    }

    @Override
    public int getRank() {
        return this.rank;
    }

    @Override
    public boolean isVisitable() {
        return true;
    }

    @Override
    public boolean isPayable() {
        return true;
    }

    @Override
    public boolean isClassifiable() {
        return true;
    }

    @Override
    public String getSpecialization() {
        return "RESTAURANT";
    }
}
