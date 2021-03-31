package pa.lab6.drawingapp.appextended.shape;

import java.awt.*;

/**
 * this class implements what a Shape class should offer -> please see Shape class wiki comments
 */
public class Rectangle extends Shape {
    private int size;

    public Rectangle(int centerPointX, int centerPointY, int stroke, Color color, int size) {
        super(centerPointX, centerPointY, stroke, color);
        this.size = size;
    }

    @Override
    public boolean locationIncludedInShape(int x, int y) {
        int x1 = shapeX;
        int y1 = shapeY;
        int x2 = shapeX + size;
        int y2 = shapeY + size;

        if (x > x1 && x < x2 && y > y1 && y < y2) {
            return true;
        }

        return false;
    }

    @Override
    public void drawShape(Canvas canvas) {
        this.draw(canvas, this.color);
    }

    public void draw(Canvas canvas, Color color) {
        Graphics graphics = canvas.getGraphics();
        ((Graphics2D) graphics).setStroke(new BasicStroke(this.stroke));
        graphics.setColor(color);

        try {
            graphics.fillRect(shapeX, shapeY, size, size);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void eraseShape(Canvas canvas) {
        draw(canvas, Color.white);
    }

    @Override
    public boolean intersects(Shape shape) {
        return false;
    }
}
