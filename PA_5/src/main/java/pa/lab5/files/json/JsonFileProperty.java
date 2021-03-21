package pa.lab5.files.json;

public enum JsonFileProperty {
    /**
     * general enums for media items
     */
    NAME("name"),
    AUTHOR("author"),
    RELEASE_YEAR("year"),
    RATING("rating"),
    PATH("path"),

    /**
     * particularities for a subclass of media
     */
    PUBLICATION("publication"),

    GENRE("genre");

    public final String label;

    JsonFileProperty(String label) {
        this.label = label;
    }
}
