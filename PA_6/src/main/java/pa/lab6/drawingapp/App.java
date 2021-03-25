package pa.lab6.drawingapp;

import java.awt.*;

public class App {
    Frame mainFrame;

    public App() {

        this.initializeFrame();


    }

    private void initializeFrame() {
        mainFrame = new Frame ("Drawing app");
        mainFrame.setPreferredSize(this.computeFrameDimension());

        LayoutManager mainGrid = new GridLayout(2, 1);
        LayoutManager bodyGrid = new FlowLayout();

        // This is the default for frames
        mainFrame.setLayout (mainGrid);

        mainFrame.add(new Button(" South") );
        mainFrame.pack ();
        mainFrame.setVisible(true);
    }

    private Dimension computeFrameDimension() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        return new Dimension((int) (screenSize.width * 1.5), (int) (screenSize.height * 1.5));
    }
}
