package pa.lab3.location;

import pa.lab3.location.interfaces.Classifiable;
import pa.lab3.location.interfaces.Payable;

public class Hotel extends Location implements Payable, Classifiable {
    private int rank;
    private float fee;

    public Hotel(String name, String description, String image, float latitude, float longitude, int rank, float fee) {
        super(name, description, image, latitude, longitude);
        this.setRank(rank);
        this.setFee(fee);
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
    public void setFee(float fee) {
        this.fee = fee;
    }

    @Override
    public float getFee() {
        return this.fee;
    }

    @Override
    public boolean isVisitable() {
        return false;
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
        return "HOTEL";
    }

}
