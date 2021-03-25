package pa.lab6.drawingapp;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//Class that defines what happens (i.e: the color changes) when a panel is clicked
public class BoxListener extends MouseAdapter
{
    public void mouseClicked(MouseEvent me)
    {
        JPanel clickedBox =(JPanel)me.getSource(); // get the reference to the box that was clicked

        // insert here the code defining what happens when a grid is clicked
    }
}
