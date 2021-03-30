package pa.lab6.drawingapp;

/**
 * helps working with list of buttons responsible with selected the shape that is drawn at click
 */
public enum ShapeType {
    CIRCLE("Circle"), SQUARE("Square"), LINE("Line");

    private final String name;

    ShapeType(String name) {
        this.name = name;
    }
}
