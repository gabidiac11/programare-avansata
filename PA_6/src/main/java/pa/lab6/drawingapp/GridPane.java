package pa.lab6.drawingapp;


import pa.lab6.drawingapp.BoxListener;

import javax.swing.*;
import java.awt.*;

public class GridPane extends JPanel {

    public GridPane(int row, int col) {

        int count = 0 ; // use to give a name to each box so that you can refer to them later
        setLayout(new GridLayout(row, col));
        setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));

        for (int i = 1; i <= (row * col); i++) {
            JPanel pan = new JPanel();

            pan.setEnabled(true);
            pan.setBackground(Color.WHITE);
            pan.setPreferredSize(new Dimension(30, 30));
            pan.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            pan.addMouseListener(new BoxListener()); // add a mouse listener to make the panels clickable
            pan.setName(count+"");
            ++count;
            add(pan);
        }
    }
}