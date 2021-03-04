package pa.lab3.location;

import pa.lab3.location.interfaces.Payable;
import pa.lab3.location.interfaces.Visitable;
import pa.lab3.program.Program;

public class Museum extends Location implements Visitable, Payable {
    private float fee;
    private Program program;

    public Museum(String name, String description, String image, float latitude, float longitude, float fee, Program program) {
        super(name, description, image, latitude, longitude);
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
    public boolean isVisitable() {
        return true;
    }

    @Override
    public boolean isPayable() {
        return true;
    }

    @Override
    public boolean isClassifiable() {
        return false;
    }

    @Override
    public String getSpecialization() {
        return "MUSEUM";
    }
}
