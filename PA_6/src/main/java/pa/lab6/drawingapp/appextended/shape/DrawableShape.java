package pa.lab6.drawingapp.appextended.shape;

import lombok.Getter;
import pa.lab6.drawingapp.appextended.shape.Shape;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

/**
 * this class implements what a Shape class should offer -> please see Shape class wiki comments
 *
 * ALSO:
 * - draws a pen like path and preserves the list of point drawn for re-drawing at need
 *
 * HOW IT WORKS:
 * 1. attaches this instance as a event listener to the provided canvas
 * 2. wait for a click event
 * 3. draws the shape while the left click is hold -> keep all the point drawn
 * 4. remove event listener when mouse is released so no other shape is draw by this instance !!
 *
 * NOT DONE:
 * - the color is not the one selected
 */
public class DrawableShape extends Shape implements MouseListener, MouseMotionListener {
    final int stroke;

    private final Point point;
    private Canvas canvas;

    List<PointCustom> points = new ArrayList<>();


    public DrawableShape(int shapeX, int shapeY, int stroke, Color color, Canvas canvas) {
        super(shapeX, shapeY, stroke, color);
        this.stroke = stroke;
        this.canvas = canvas;

        this.point = new Point(shapeX, shapeY);

        canvas.addMouseListener(this);
        canvas.addMouseMotionListener(this);
    }

    /**
     * starts the drawing
     * @param mouseEvent
     */
    public void mousePressed(MouseEvent mouseEvent) {
        canvas.setForeground(this.color);
        point.move(mouseEvent.getX(), mouseEvent.getY());
    }

    /**
     * 3. draws the shape while the left click is hold -> keep all the point drawn
     * @param e
     */
    public void mouseDragged(MouseEvent e) {
        PointCustom point1 = new PointCustom(e.getX(), e.getY(), e.isMetaDown());
        points.add(point1);

        this.drawPoint(point1);
    }

    /**
     * draws based on a PointCustom instance
     * @param point1
     */
    public void drawPoint(PointCustom point1) {
        Graphics graphics = canvas.getGraphics();

        if (point1.isMetaDown) {
            graphics.fillOval((int) point1.getX() - (stroke /2), (int) point1.getY() - (stroke /2),
                    stroke, stroke);
        } else {
            graphics.drawLine(point.x, point.y, (int) point1.getX(), (int) point1.getY());
        }

        point.move((int) point1.getX(), (int) point1.getY());
    }


    /**
     * 4. remove event listener when mouse is released so no other shape is draw by this instance
     * @param e
     */
    @Override
    public void mouseReleased(MouseEvent e) {
        canvas.removeMouseListener(this);
        canvas.removeMouseMotionListener(this);
    }

    @Override
    public void drawShape(Canvas canvas) {
        for(PointCustom pointCustom : this.points) {
            this.drawPoint(pointCustom);
        }
    }

    /**
     * this is intended to draw the shape as WHITE
     */
    @Override
    public void eraseShape(Canvas canvas) {
        canvas.setForeground(Color.white);

        for(PointCustom pointCustom : this.points) {
            this.drawPoint(pointCustom);
        }
    }

    @Override
    public void mouseMoved (MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public boolean locationIncludedInShape(int x, int y) {
        return false;
    }

    @Override
    public boolean intersects(Shape shape) {
        return false;
    }

    /**
     * this class holds the coordinates of mouse events and if the right mouse is pressed
     */
    @Getter
    class PointCustom {
        final boolean isMetaDown;
        private int x;
        private int y;

        public PointCustom(int x, int y, boolean isMetaDown) {
            this.x = x;
            this.y = y;
            this.isMetaDown = isMetaDown;
        }
    }
}