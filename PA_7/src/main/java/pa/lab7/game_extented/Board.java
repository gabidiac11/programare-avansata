package pa.lab7.game_extented;

import javafx.util.Pair;
import pa.lab7.game_extented.Player;
import pa.lab7.game_extented.Timer;
import pa.lab7.game_extented.Token;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private Frame mainFrame;
    private Canvas canvas;
    private List<Player> playerList;

    private int selectedIndexPlayer = 0;

    private List<Token> tokens;

    public Board(List<Player> playerList, int count) {
        this.initializeFrame();

        this.playerList = playerList;
        this.tokens = generateTokens(count);
    }

    /**
     * generate consecutive groups of tokens
     * @param count - number desired of tokens
     * @return -
     */
    private static List<Token> generateTokens(int count) {
        List<Token> tokens = new Vector<>();
        for(int i = 0; i < count; i++) {
            tokens.add(new Token(i, i + 1));
        }
        return Collections.synchronizedList(tokens);
    }

    private void initializeFrame() {
        this.initializeCanvas();

        this.mainFrame = new JFrame("Match token");
        this.assignFrameSize();
        mainFrame.setLayout(new FlowLayout());
        mainFrame.add(this.canvas);
        mainFrame.add(this.createBottomPanel());

        mainFrame.setVisible(true);
    }

    private void initializeCanvas() {
        canvas = new Canvas();
        canvas.setBackground(Color.WHITE);

        /* set whole width so canvas gets to a new line */
        canvas.setBounds(120, 250, getMainFrameDimensions().getKey(), 500);

        drawTokens(canvas);

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

    private void drawTokens(Canvas canvas) {
        this.tokens.forEach(token -> {
            token.drawShape(canvas);
        });
    }

    private void onCanvasMousePressed(MouseEvent e) {

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

    public boolean isMyTurn(Player player) {
        return this.playerList.get(this.selectedIndexPlayer).equals(player);
    }

    private void eraseShapeAtLocation(int x, int y) {
//        for(int i = this.drawnShapes.size() - 1; i >= 0; i--) {
//            Shape shape = drawnShapes.get(i);
//            if(shape.locationIncludedInShape(x, y)) {
//                shape.eraseShape(canvas);
//
//                /*
//                 Ideally a option is to find shape colliding with this one and redraw them
//                 but re-drawing the colliding ones means drawing over other shapes colliding with that one
//
//                 for now its re-drawing everything..
//                * */
//                for(int ii = 0; ii < this.drawnShapes.size(); ii++) {
//                    if(ii != i) {
//                        this.drawnShapes.get(ii).drawShape(canvas);
//                    }
//                }
//
//                this.drawnShapes.remove(i);
//                break;
//            }
//        }
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
            String action = "";

            String pathToCursor = null;

//            if(action.equals(ActionButtonType.ERASER.name())) {
//                pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\eraser.png";
//            } else if(action.equals(ShapeType.FREE_SHAPE.name())) {
//                pathToCursor = "src\\main\\java\\pa\\lab6\\assets\\pen.png";
//            }

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

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();

        bottomPanel.setLayout(new FlowLayout());
        bottomPanel.setBackground(Color.cyan);

        /* instantiate timer UI  */
        JLabel timerLabel = new JLabel("0 left");
        bottomPanel.add(timerLabel);
        new Thread(new Timer(timerLabel)).start();

        return bottomPanel;
    }

    private List<Integer> extractIndexes(int index, Set<Integer> settled) {
        List<Integer> list = new Vector<>();
        list.add(index);
        settled.add(index);

//        for(int i = index + 1; i < this.tokens.size(); i++) {
//            list = Stream.concat(list.stream(), board.pickTokens().stream())
//                    .collect(Collectors.toList());
//        }

        return list;
    }

    public List<Token> pickTokens() {
        return new Vector<>();
    }
}

