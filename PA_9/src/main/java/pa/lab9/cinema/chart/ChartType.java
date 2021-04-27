package pa.lab9.cinema.chart;

public enum ChartType {
    BY_RATING("Chart by rating", "By-rating"),
    BY_RELEASE_DATE("Chart by release date", "By-release-date");

    public final String label;
    public final String signature;

    ChartType(String label, String signature) {
        this.label = label;
        this.signature = signature;
    }
}
