package pa.lab6.drawingapp.appextended.shape;

import java.awt.*;

public class Rectangle extends Shape {
    private int length;
    private int height;

    public Rectangle(int centerPointX, int centerPointY, int stroke, Color color, int length, int height) {
        super(centerPointX, centerPointY, stroke, color);
        this.length = length;
        this.height = height;
    }

    @Override
    public boolean locationIncludedInShape(int x, int y) {
        return false;
    }

    @Override
    public void drawShape(Canvas canvas) {

    }

    @Override
    public void eraseShape(Canvas canvas) {

    }

    @Override
    public boolean intersects(Shape shape) {
        return false;
    }
}
