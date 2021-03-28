package pa.lab6.drawingapp;

/**
 * helps working with list of buttons responsible with selected the shape that is drawn at click
 */
public enum Shape {
    CIRCLE("Circle"), SQUARE("Square"), LINE("Line");

    private final String name;

    Shape(String name) {
        this.name = name;
    }
}
