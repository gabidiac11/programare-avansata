package pa.lab6.drawingapp.appextended;

import javafx.util.Pair;
import pa.lab6.drawingapp.ShapeType;
import pa.lab6.drawingapp.actionpanel.ActionPanel;
import pa.lab6.drawingapp.appextended.shape.Circle;
import pa.lab6.drawingapp.appextended.shape.DrawableShape;
import pa.lab6.drawingapp.appextended.shape.Rectangle;
import pa.lab6.drawingapp.appextended.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class AppOptional {
    private final static ShapeType[] ALLOWED_SHAPES = new ShapeType[] {ShapeType.CIRCLE, ShapeType.SQUARE, ShapeType.FREE_SHAPE};

    private Frame mainFrame;
    private Canvas canvas;

    private JPanel topPanel;

    //input by which the drawing is customized
    private JTextField sizeField;
    private JTextField strokeField;

    /**
     * keeps track of all shapes drawn on the canvas (in order)
     * helps with redrawing, finding a shape based on mouse position
     */
    private final List<Shape> drawnShapes = new ArrayList<>();
    
    private final List<JButton> bottomPanelButtons = new ArrayList<>();
    private int indexBottomSelected = -1; //selected button between (one of the shapes or the eraser button)

    public AppOptional() {
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
        mainFrame.add(new ActionPanel(canvas, mainFrame, this.drawnShapes).getJPanel());

        mainFrame.setVisible(true);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);

        /* set whole width so canvas gets to a new line */
        canvas.setBounds(120, 250, getMainFrameDimensions().getKey(), 500);

        canvas.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {}

            @Override
            public void mousePressed(MouseEvent e) {
                onCanvasMousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

    }

    private void onCanvasMousePressed(MouseEvent e) {
        if(this.indexBottomSelected > -1) {
            JButton button = this.bottomPanelButtons.get(this.indexBottomSelected);
            String action = button.getActionCommand();

            if(action.equals(ActionButtonType.ERASER.name())) {
                eraseShapeAtLocation(e.getX(), e.getY());
            } else {
                    for(ShapeType shapeTye : ALLOWED_SHAPES) {
                        if(action.equals(shapeTye.name())) {
                            drawSelectedShape(e.getX(), e.getY(), shapeTye);
                            break;
                        }
                }
            }
        }

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

    /**
     * draw a specific shape and keeps track of what has been drawn
     * @param x
     * @param y
     * @param shapeTypeSelected - on of the ShapeType
     */
    private void drawSelectedShape(int x, int y, ShapeType shapeTypeSelected) {
        /* center the position of the shape around the point*/
        int size = getNumberFromTextField(this.sizeField);
        int stroke = getNumberFromTextField(this.strokeField);
        Color color = generateRandomColor();

        Shape shape = null;
        switch (shapeTypeSelected) {
            case CIRCLE:
                shape = new Circle(x, y, stroke, color, size/2);
                break;

            case SQUARE:
                shape = new Rectangle(x, y, stroke, color, size);
                break;

            case FREE_SHAPE:
                shape = new DrawableShape(
                        x, y, this.getNumberFromTextField(this.strokeField),
                        generateRandomColor(),
                        this.canvas
                );
                break;
        }

        if(shape != null) {
            shape.drawShape(this.canvas);
            this.drawnShapes.add(shape);
        }
    }

    /**
     * 1. finds the shape pressed by the mouse clicked based on the click coords
     * and based on the list of shape drawn (from the last drawn to the first)
     * - each shape has methods to figure out if the location is inside
     * - each shape has a method for erasing
     * 2. redraws the other shapes that might be affected by the erasing (for now it draws everything)
     *
     * NOTE: free shape is not erasable yet
     * @param x
     * @param y
     */
    private void eraseShapeAtLocation(int x, int y) {
        for(int i = this.drawnShapes.size() - 1; i >= 0; i--) {
            Shape shape = drawnShapes.get(i);
            if(shape.locationIncludedInShape(x, y)) {
                shape.eraseShape(canvas);

                /*
                 Ideally a option is to find shape colliding with this one and redraw them
                 but re-drawing the colliding ones means drawing over other shapes colliding with that one

                 for now its re-drawing everything..
                * */
                for(int ii = 0; ii < this.drawnShapes.size(); ii++) {
                    if(ii != i) {
                        this.drawnShapes.get(ii).drawShape(canvas);
                    }
                }

                this.drawnShapes.remove(i);
                break;
            }
        }
    }

    /**
     * creates a number text field for convenience (used for generating inputs for shape size, stroke size)
     * @param label - each input has a text before it to express for it does
     * @param defaultNumber -
     * @return - button
     */
    private static Pair<JTextField, JLabel> createGenericNumberField(String label, int defaultNumber) {
        JTextField sizeField = new JTextField(Integer.toString(defaultNumber));
        sizeField.setPreferredSize(new Dimension(150,20));

        JLabel jLabel = new JLabel(label);

        return new Pair<>(sizeField, jLabel);
    }

    /**
     * creates one of the buttons responsible with selecting the shape
     * @param shapeType
     * @return
     */
    private JButton createShapeButton(ShapeType shapeType) {
        JButton button = new JButton(shapeType.name());

        button.setBounds(0, 0, 70, 50);

        button.setActionCommand(shapeType.name());
        button.addActionListener(e -> {
            selectButton(shapeType.name());
        });

        return button;
    }

    /**
     * creates and attaches event for eraser button
     * @return
     */
    private JButton createEraserButton() {

        JButton button = new JButton(ActionButtonType.ERASER.name());

        button.setActionCommand(ActionButtonType.ERASER.name());

        button.setBounds(0, 0, 70, 50);

        button.addActionListener(e -> {
            selectButton(ActionButtonType.ERASER.name());
        });

        return button;
    }

    /**
     * marks what button from the left bottom panel (shapes + eraser buttons) is selected
     * by selecting an index
     * @param action
     */
    private void selectButton(String action) {
        this.indexBottomSelected = -1;

        for(int i = 0; i < this.bottomPanelButtons.size(); i++) {
            JButton item = this.bottomPanelButtons.get(i);
            if(item.getActionCommand().equals(action)) {
                item.setBackground(Color.orange);
                this.indexBottomSelected = i;
            } else {
                item.setBackground(Color.WHITE);
            }
        }

        this.setCursorBasedOnIndex(this.indexBottomSelected);
    }


    /**
     * sets the cursor based on the left bottom panel selection buttons (shapes + eraser)
     * - eraser cursor png
     * - pen - pen png (for 'free drawing')
     * - default - otherwise
     * @param index
     */
    private void setCursorBasedOnIndex(int index) {
        if(index > -1) {
            String action = this.bottomPanelButtons.get(index).getActionCommand();

            String pathToCursor = null;

            if(action.equals(ActionButtonType.ERASER.name())) {
                pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\eraser.png";
            } else if(action.equals(ShapeType.FREE_SHAPE.name())) {
                pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\pen.png";
            }

            if(pathToCursor != null) {
                Toolkit toolkit = Toolkit.getDefaultToolkit();
                Image imageCrossCursor = toolkit.getImage(pathToCursor);
                canvas.setCursor(toolkit.createCustomCursor(imageCrossCursor, new Point(0,0), "Some cursor"));

                return;
            }
        }

        /*
         * set the default if no custom one was set in the lines above
         */
        canvas.setCursor(Cursor.getDefaultCursor());
    }


    /**
     * creates the panel with those 2 inputs (size, stroke)
     */
    private void initializeTopPanel() {
        /* assign panel and its fields **/
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.gray);

        /* compute fields of a field **/
        java.util.List<Pair<JTextField, JLabel>> fields = createTopPanelTextFields();

        fields.forEach(item -> {
            topPanel.add(item.getValue());
            topPanel.add(item.getKey());
        });
    }

    /**
     * creates those 2 inputs (size, stroke) to be displayed inside the top panel
     */
    private java.util.List<Pair<JTextField, JLabel>> createTopPanelTextFields() {
        List<Pair<JTextField, JLabel>> fields = new ArrayList<>();

        Pair<JTextField, JLabel> sizePair = createGenericNumberField("Shape size:", 40);
        sizeField = sizePair.getKey();
        fields.add(sizePair);

        Pair<JTextField, JLabel> strokePair = createGenericNumberField("Stroke size:", 2);
        strokeField = strokePair.getKey();
        fields.add(strokePair);

        return fields;
    }

    /**
     * creates the panel that has:
     * 1. a left section with the shape selector + eraser
     * 2. a right section with the saving/load/exit/reset options
     * @return
     */
    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.cyan);

        /* initialize shape selector buttons */
        Arrays.stream(ALLOWED_SHAPES).forEach((item) -> {
            this.bottomPanelButtons.add(createShapeButton(item));
        });

        /* initialize eraser selector */
        this.bottomPanelButtons.add(createEraserButton());

        this.bottomPanelButtons.forEach(bottomPanel::add);

        /* preselect the first button */
        this.bottomPanelButtons.get(0).doClick();

        return bottomPanel;
    }
}

