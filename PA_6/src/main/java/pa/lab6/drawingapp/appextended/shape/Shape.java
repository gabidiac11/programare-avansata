package pa.lab6.drawingapp.appextended.shape;

import lombok.AllArgsConstructor;

import java.awt.*;
import java.util.List;

@AllArgsConstructor
public abstract class Shape {
    protected int shapeX;
    protected int shapeY;
    protected int stroke;
    protected Color color;

    public abstract boolean locationIncludedInShape(int x, int y);
    public abstract void drawShape(Canvas canvas);

    public abstract void eraseShape(Canvas canvas);

    public abstract boolean intersects(Shape shape);

    public static boolean shapesAreIntersecting(Shape shape1, Shape shape2) {
        if(shape1 instanceof Circle && shape1 instanceof Circle) {
            return shape1.intersects(shape2);
        }
        return false;
    }
}
