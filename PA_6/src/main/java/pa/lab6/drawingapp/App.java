package pa.lab6.drawingapp;

import javafx.util.Pair;
import pa.lab6.drawingapp.actionpanel.ActionPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

/**
 * creates the frame
 * create the top panel with the 'Shape size' and 'Stroke size' text fields and uses the input from them
 * creates the canvas and listens for click events and draws shapes based on these events
 * adds the bottom action buttons list from ActionPanel class
 */
public class App {
    private Frame mainFrame;
    private Canvas canvas;
    private Graphics graphics;

    private JPanel topPanel;

    //input by which the drawing is customized
    private JTextField sizeField;
    private JTextField strokeField;

    private Shape shapeSelected = Shape.CIRCLE;

    /*
        - list of buttons that if pressed selects the shape to be drawn
        - the selected one is marked different from others
     */
    private Map<Shape, JButton> availableShapes;

    public App() {
        this.initializeFrame();
    }

    /**
     * creates the frame
     * create the top panel with the 'Shape size' and 'Stroke size' text fields
     *
     * creates the canvas and listens for click events
     * adds the bottom action buttons list from ActionPanel class
     */
    private void initializeFrame() {
        this.initializeTopPanel();
        this.initializeCanvas();

        this.mainFrame = new JFrame("Drawing App");
        this.assignFrameSize();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.add(topPanel);
        mainFrame.add(this.canvas);
        mainFrame.add(this.createBottomPanel());
        mainFrame.add(new ActionPanel(canvas, mainFrame).getJPanel());

        mainFrame.setVisible(true);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);

        /* set whole width so canvas gets to a new line */
        canvas.setBounds(120, 250, getMainFrameDimensions().getKey(), 500);

        canvas.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {drawSelectedShape(e.getX(), e.getY());}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }

    /**
     * assigns the calculated dimensions to the frame
     */
    private void assignFrameSize() {
        Pair<Integer, Integer> dims = getMainFrameDimensions();
        mainFrame.setSize(dims.getKey(), dims.getValue());
    }

    /**
     * calculates width and height of the desired frame based on the screen size
     * @return
     */
    private static Pair<Integer, Integer> getMainFrameDimensions() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        return new Pair<>(
                ((int) (screenSize.width * 0.8)),
                700 //(int) (screenSize.height * 0.6)
        );
    }

    /**
     * parses a number from a JTextField, with some fallbacks
     * @param jTextField
     * @return
     */
    private int getNumberFromTextField(JTextField jTextField) {
        int number;
        try {
            number = Integer.parseInt(jTextField.getText());
            if(number < 0) {
                throw new Exception("Invalid");
            }
        } catch (Exception e) {
            number = 1;
        }
        return number;
    }


    private static Color generateRandomColor() {
        Color[] palletOfColors = new Color[]{
                Color.black,
                Color.blue,
                Color.red,
                Color.gray,
                Color.yellow,
                Color.cyan
        };

        return palletOfColors[new Random().nextInt(palletOfColors.length)];
    }

    private void drawCircle(int x, int y) {
        int size = getNumberFromTextField(this.sizeField);

        graphics = canvas.getGraphics();
        assignStrokeToGraphic(graphics);

        try {
            graphics.drawOval(x,y,size,size);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void drawLine(int x, int y) {
        int size = getNumberFromTextField(this.sizeField);

        graphics = canvas.getGraphics();
        assignStrokeToGraphic(graphics);

        try {
            graphics.drawLine(x,y, x + size, y);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    private void drawSquare(int x, int y) {
        int size = getNumberFromTextField(this.sizeField);

        graphics = canvas.getGraphics();
        assignStrokeToGraphic(graphics);

        try {
            graphics.drawRect(x,y,size,size);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    /**
     * sets the current stroke value from input when drawing
     * @param graphics
     */
    private void assignStrokeToGraphic(Graphics graphics) {
        ((Graphics2D) graphics).setStroke(new BasicStroke(getNumberFromTextField(strokeField)));
        graphics.setColor(generateRandomColor());

    }

    private void drawSelectedShape(int x, int y) {
        /* center the position of the shape around the point*/
        int size = getNumberFromTextField(this.sizeField);
        x = x - size / 2;
        y = y - size / 2;

        switch (this.shapeSelected) {
            case CIRCLE:
                drawCircle(x, y);
                break;
            case SQUARE:
                drawSquare(x, y);
                break;
            case LINE:
                drawLine(x, y);
                break;
        }
    }

    private static Pair<JTextField, JLabel> createGenericNumberField(String label, int defaultNumber) {
        JTextField sizeField = new JTextField(Integer.toString(defaultNumber));
        sizeField.setPreferredSize(new Dimension(150,20));

        JLabel jLabel = new JLabel(label);

        return new Pair<>(sizeField, jLabel);
    }

    /**
     * creates one of the buttons responsable with selecting the shape
     * @param shape
     * @return
     */
    private JButton createShapeButton(Shape shape) {
        JButton button = new JButton(shape.name());

        button.setBounds(0, 0, 70, 50);

        button.setActionCommand(shape.name());
        button.addActionListener(e -> {
            selectShape(shape);
        });

        return button;
    }

    /**
     * highlights the button of the shape selected and selects the shape to drawn
     * @param shape
     */
    private void selectShape(Shape shape) {

        this.shapeSelected = shape;

        this.availableShapes.entrySet().forEach(item -> {
            if(item.getKey() == shape) {
                item.getValue().setBackground(Color.orange);
            } else {
                item.getValue().setBackground(Color.WHITE);
            }
        });
    }

    private void initializeTopPanel() {
        /** assign panel and its fields **/
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.gray);

        /** compute fields of a field **/
        List<Pair<JTextField, JLabel>> fields = createTopPanelTextFields();

        fields.forEach(item -> {
            topPanel.add(item.getValue());
            topPanel.add(item.getKey());
        });
    }

    private void initializeListOfShapeButtons() {
        this.availableShapes = new HashMap<>();
        Arrays.stream(Shape.values()).forEach(item -> {
            availableShapes.put(item, createShapeButton(item));
        });

        /* mark the one selected by default */
        selectShape(this.shapeSelected);
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.cyan);

        this.initializeListOfShapeButtons();

        this.availableShapes.entrySet().forEach(item -> {
            bottomPanel.add(item.getValue());
        });

        return bottomPanel;
    }

    private List<Pair<JTextField, JLabel>> createTopPanelTextFields() {
        List<Pair<JTextField, JLabel>> fields = new ArrayList<>();

        Pair<JTextField, JLabel> sizePair = createGenericNumberField("Shape size:", 40);
        sizeField = sizePair.getKey();
        fields.add(sizePair);

        Pair<JTextField, JLabel> strokePair = createGenericNumberField("Stroke size:", 2);
        strokeField = strokePair.getKey();
        fields.add(strokePair);

        return fields;
    }

}
