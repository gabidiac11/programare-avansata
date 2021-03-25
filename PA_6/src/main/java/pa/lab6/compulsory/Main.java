package pa.lab6.compulsory;

import pa.lab6.drawingapp.GridPane;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Main {
    public static void main(String argc[]) {
        Frame f = new Frame (" Border Layout ");

        // get the screen size as a java dimension
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

        // get 2/3 of the height, and 2/3 of the width
        int height = screenSize.height * 2 / 3;
        int width = screenSize.width * 2 / 3;

        // set the jframe height and width
        f.setPreferredSize(new Dimension(width, height));

        GridPane gridPane = new GridPane(3, 5);

        // This is the default for frames
        f.setLayout (new GridLayout(2,1));

        f.add(gridPane);
        f.add(new Button(" South") );
        f.pack ();
        f.setVisible(true);


    }
}


