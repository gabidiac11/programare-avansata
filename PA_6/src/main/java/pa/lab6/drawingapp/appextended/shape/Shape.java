package pa.lab6.drawingapp.appextended.shape;

import lombok.AllArgsConstructor;

import java.awt.*;


/**
 * this class offers:
 * - the general properties needed to place a shape on the screen (x, y, stroke, color)
 * - method that verifies if a point is located inside the shape (help in erasing with the click position)
 * - method to draw the shape at need
 * - method to erase the shape at need (draw with WHITE)
 */
@AllArgsConstructor
public abstract class Shape {
    protected int shapeX;
    protected int shapeY;
    protected int stroke;
    protected Color color;

    public abstract boolean locationIncludedInShape(int x, int y);
    public abstract void drawShape(Canvas canvas);

    public abstract void eraseShape(Canvas canvas);

    /**
     * IS NOT IN USED -> MARKED FOR OPTIMISATION
     * INTENDED: help re-draw only shapes affected by a erasing the intended shape to be erased
     * @param shape
     * @return
     */
    public abstract boolean intersects(Shape shape);

    /**
     * IS NOT IN USED -> MARKED FOR OPTIMISATION
     * INTENDED: help re-draw only shapes affected by a erasing the intended shape to be erased
     * @param shape1
     * @param shape2
     * @return
     */
    public static boolean shapesAreIntersecting(Shape shape1, Shape shape2) {
        if(shape1 instanceof Circle && shape1 instanceof Circle) {
            return shape1.intersects(shape2);
        }
        return false;
    }
}
