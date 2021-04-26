package pa.lab9.factory;

/**
 * enums for matching the config json property setting
 */
public enum ConnectionType {
    JPA("jpa"),
    JDBC("jdbc");

    public final String value;

    ConnectionType(String label) {
        this.value = label;
    }
}
