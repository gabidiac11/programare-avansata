package pa.lab6.drawingapp.actionpanel;

import lombok.Getter;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

public class ActionPanel {
    @Getter
    JPanel jPanel;
    Canvas canvas;
    Frame frame;

    public ActionPanel(Canvas canvas, Frame frame) {
        this.canvas = canvas;
        this.frame = frame;

        this.initializePanel();
    }

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

    private void resetCanvas() {
        Graphics graphics = canvas.getGraphics();
        graphics.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    private void exit() {
        frame.dispose();
        System.exit(0);
    }
}
