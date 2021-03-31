package pa.lab6.drawingapp.appextended.shape;

import java.awt.*;

public class Circle extends Shape {

    private int ray;


    public Circle(int shapeX, int shapeY, int stroke, Color color, int ray) {
        super(shapeX, shapeY, stroke, color);
        this.ray = ray;
    }

    /**
     * see if location is inside the circle using a geometrical solution
     * https://math.stackexchange.com/questions/198764/how-to-know-if-a-point-is-inside-a-circle
     * @param x
     * @param y
     * @return - point is inside circle
     */
    @Override
    public boolean locationIncludedInShape(int x, int y) {
        // Compare radius of circle with distance
        // of its center from given point
        if ((x - shapeX) * (x - shapeX) +
                (y - shapeY) * (y - shapeY) <= ray * ray)
            return true;
        else
            return false;
    }



    @Override
    public void drawShape(Canvas canvas) {
        Graphics graphics = canvas.getGraphics();
        ((Graphics2D) graphics).setStroke(new BasicStroke(this.stroke));
        graphics.setColor(this.color);

        try {
            graphics.fillOval(shapeX - ray, shapeY - ray, ray*2, ray*2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    public void drawShape(Canvas canvas, Color color) {
        Graphics graphics = canvas.getGraphics();
        graphics.setColor(color);

        try {
            graphics.fillOval(shapeX - ray, shapeY - ray, ray*2, ray*2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void eraseShape(Canvas canvas) {
        Graphics graphics = canvas.getGraphics();
        ((Graphics2D) graphics).setStroke(new BasicStroke(this.stroke));
        graphics.setColor(Color.white);

        try {
            graphics.fillOval(shapeX - ray, shapeY - ray, ray*2, ray*2);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * https://www.geeksforgeeks.org/check-two-given-circles-touch-intersect/
     * @param shape
     * @return
     */
    @Override
    public boolean intersects(Shape shape) {
        int x1 = shape.shapeX;
        int y1 = shape.shapeY;
        int r1 = ((Circle) shape).ray;

        int x2 = this.shapeX;
        int y2 = this.shapeY;
        int r2 = this.ray;

        /**
         *
         Two circles intersect if, and only if, the distance between their centers is between the sum and the difference of their radii.
         Given two circles (x0, y0, R0) and (x1, y1, R1), the formula is as follows:
         */
        double distance = Math.pow((x1 - x2), 2) + Math.pow((y1 - y2), 2);

        return Math.pow(Math.abs(r1 - r2), 2) <= distance && distance <= Math.pow(r1 + r2, 2);
    }
}
