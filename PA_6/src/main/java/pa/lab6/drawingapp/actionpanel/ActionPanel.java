package pa.lab6.drawingapp.actionpanel;

import lombok.Getter;
import pa.lab6.drawingapp.appextended.shape.Shape;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * manages save, load, exit, reset actions for a canvas
 * provides a panel with buttons for each action
 */
public class ActionPanel {
    @Getter
    JPanel jPanel;
    Canvas canvas;
    Frame frame;
    List<Shape> drawnShapes;

    /**
     * constructor for COMPULSORY - no shape history provided 
     * @param canvas
     * @param frame
     */
    public ActionPanel(Canvas canvas, Frame frame) {
        this.canvas = canvas;
        this.frame = frame;
        this.drawnShapes = null;

        this.initializePanel();
    }

    /**
     * constructor for OPTIONAL
     * @param canvas
     * @param frame
     */
    public ActionPanel(Canvas canvas, Frame frame, List<Shape> drawnShapes) {
        this.canvas = canvas;
        this.frame = frame;
        this.drawnShapes = drawnShapes;

        this.initializePanel();
    }

    /**
     * draws panel and adds events to the buttons
     */
    private void initializePanel() {
        jPanel = new JPanel();
        jPanel.setLayout(new FlowLayout());
        jPanel.setBackground(Color.gray);

        Arrays.stream(ActionPanelType.values()).forEach(actionPanelType -> {
            String label = actionPanelType.name();

            JButton button = new JButton(label);
            button.setBounds(0,0,70,70);
            button.setActionCommand(label);
            button.addActionListener(e -> {
                onClickButton(actionPanelType);
            });

            jPanel.add(button);
        });
    }

    /**
     * see what button has been pressed and follow through
     * @param actionPanelType
     */
    private void onClickButton(ActionPanelType actionPanelType) {
        switch (actionPanelType) {
            case SAVE:
                this.saveCanvas();
                break;

            case LOAD:
                this.loadToCanvas();
                break;

            case RESET:
                this.resetCanvas();
                break;

            case EXIT:
                this.exit();
                break;
        }
    }

    /**
     * save the image on the same location
     */
    private void saveCanvas() {
        BufferedImage image = new BufferedImage(canvas.getWidth(), canvas.getHeight(),BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        canvas.print(graphics);
        try {
            FileOutputStream out = new FileOutputStream("imageName.png");
            ImageIO.write(image, "png", out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * add a image to the canvas to be drawn
     */
    private void loadToCanvas() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
                "JPG & GIF Images",
                "jpg", "gif","png"
        );

        chooser.setFileFilter(filter);

        int result = chooser.showOpenDialog(chooser.getParent());

        if (result == JFileChooser.APPROVE_OPTION) {
            BufferedImage img = null;
            try {
                img = ImageIO.read(chooser.getSelectedFile());
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            if (img != null) {
                int x = 10;
                int y = 20;
                canvas.getGraphics().drawImage(img, x, y, (img1, infoflags, x1, y1, width, height) -> false);
            }
        }

    }

    /**
     * erases the canvas
     * deletes the shape history if is any
     */
    private void resetCanvas() {
        Graphics graphics = canvas.getGraphics();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        /* remove the list of drawn shapes (so the eraser would not take them into account in the future when reconstructing shapes) */
        /*  */
        if(this.drawnShapes != null) {
            while(drawnShapes.size() > 0) {
                drawnShapes.remove(drawnShapes.size() - 1);
            }
        }
    }

    private void exit() {
        frame.dispose();
        System.exit(0);
    }
}
