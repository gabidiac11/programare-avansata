package pa.lab6.drawingapp.appextended;

import javafx.util.Pair;
import pa.lab6.drawingapp.ShapeType;
import pa.lab6.drawingapp.actionpanel.ActionPanel;
import pa.lab6.drawingapp.appextended.shape.Circle;
import pa.lab6.drawingapp.appextended.shape.Shape;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;

public class AppOptional {
    private Frame mainFrame;
    private Canvas canvas;
    private Graphics graphics;

    private JPanel topPanel;

    //input by which the drawing is customized
    private JTextField sizeField;
    private JTextField strokeField;

    private ShapeType shapeTypeSelected = ShapeType.CIRCLE;

    //backup selection when selecting eraser
    private ShapeType prevShapeTypeSelected = null;

    JButton eraserButton;

    private List<Shape> drawnShapes = new ArrayList<>();

    private boolean eraserActive = false;

    /*
        - list of buttons that if pressed selects the shape to be drawn
        - the selected one is marked different from others
     */
    private Map<ShapeType, JButton> availableShapes;

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
            public void mousePressed(MouseEvent e) {
                if(eraserActive) {
                    eraseShapeAtLocation(e.getX(), e.getY());
                } else {
                    drawSelectedShape(e.getX(), e.getY());
                }
            }

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

    private void drawSelectedShape(int x, int y) {
        /* center the position of the shape around the point*/
        int size = getNumberFromTextField(this.sizeField);
        int stroke = getNumberFromTextField(this.strokeField);
        Color color = generateRandomColor();

        Shape shape = null;
        switch (this.shapeTypeSelected) {
            case CIRCLE:
                shape = new Circle(x, y, stroke, color, size/2);
                break;
        }

        if(shape != null) {
            shape.drawShape(this.canvas);
            this.drawnShapes.add(shape);
        }
    }



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
            selectShape(shapeType);
        });

        return button;
    }

    private JButton createEraserButton() {
        setEraserValue(false);

        JButton button = new JButton("ERASE");

        button.setBounds(0, 0, 70, 50);

        button.addActionListener(e -> {
            onClickEraser();
        });

        return button;
    }

    private void onClickEraser() {
        JButton button = eraserButton;

        this.setEraserValue(!this.eraserActive);
        if(this.eraserActive) {
            button.setBackground(Color.red);

            /* mark the active status in the bottom panel */
            prevShapeTypeSelected = this.shapeTypeSelected;
            selectShape(null);
        } else {
            button.setBackground(Color.white);
            selectShape(this.prevShapeTypeSelected);
        }
    }

    private void setEraserValue(boolean newValue) {
        String pathToCursor = "";
//        if(newValue) {
//            pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\eraser.png";
//        } else {
//            pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\pen.png";
//        }
//
//        Toolkit toolkit = Toolkit.getDefaultToolkit();
//        Image imageCrossCursor = toolkit.getImage(pathToCursor);
//        canvas.setCursor(toolkit.createCustomCursor(imageCrossCursor, new Point(0,0), "Some cursor"));
        this.eraserActive = newValue;
    }

    /**
     * highlights the button of the shape selected and selects the shape to drawn
     * @param shapeType
     */
    private void selectShape(ShapeType shapeType) {

        this.shapeTypeSelected = shapeType;

        this.availableShapes.entrySet().forEach(item -> {
            if(item.getKey() == shapeType) {
                item.getValue().setBackground(Color.orange);
            } else {
                item.getValue().setBackground(Color.WHITE);
            }
        });

        /* deactivate eraser when a shape is clicked  */
        if(shapeType != null && eraserActive) {
            onClickEraser();
        }

    }

    private void initializeTopPanel() {
        /** assign panel and its fields **/
        topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.setBackground(Color.gray);

        /** compute fields of a field **/
        java.util.List<Pair<JTextField, JLabel>> fields = createTopPanelTextFields();

        fields.forEach(item -> {
            topPanel.add(item.getValue());
            topPanel.add(item.getKey());
        });
    }

    private void initializeListOfShapeButtons() {
        this.availableShapes = new HashMap<>();
        Arrays.stream(ShapeType.values()).forEach(item -> {
            availableShapes.put(item, createShapeButton(item));
        });

        /* mark the one selected by default */
        selectShape(this.shapeTypeSelected);
    }

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

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.cyan);

        this.initializeListOfShapeButtons();

        this.availableShapes.entrySet().forEach(item -> {
            bottomPanel.add(item.getValue());
        });

        //add eraser button
        eraserButton = createEraserButton();
        bottomPanel.add(eraserButton);

        return bottomPanel;
    }
}

