
# Lab 6
## _Advanced Programming 2021_
[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://www.info.uaic.ro/)

Diac P. Gabriel
2A2

- completed (~✔️), partially completed (⏰) or are not started (❌)
  
### Compusory
Canvas in which a selected shape (circle, square, line) is drawn at mouse position click, using a random color. It can also be added an image, like in the example bellow.

[![N|Solid](https://github.com/gabidiac11/programare-avansata/blob/main/PA_6/exemplu.PNG)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_6/exemplu.PNG)


## Addressed exercises 
### Optional (2p) 

Requirements and their status:

 #### -Implement a retained mode drawing and add support for deleting shapes.~✔️ 
 #### -Add support for drawing multiple types of components. Consider creating a new panel, containing a list of available shapes.
The configuration panel must adapt according to the type of the selected shape. Implement at least two types of shapes.~✔️ 

 I've created another class `pa\lab6\drawingapp\appextended\AppOptional`, that holds all the shapes that had been drawn using the a list of instances of a new class, Shape. Shape is extended by Circle, Rectangle and DrawableShape. Each of this instances implements methods to help erase the correct shape indicated by mouse position. Erasing is done by drawing the shape using white color.  The shapes that are affected by the eraser are re-drawn (because all shapes are re-drawn).
 
 
 I added custom cursors also to mark the eraser better. (a screen shot did not show my cursor so I snap a photo of it instead)

[![N|Solid](https://plati-taxe.uaic.ro/img/logo-retina1.png)](https://github.com/gabidiac11/programare-avansata/blob/main/PA_6/IMG_20210401_011011.jpg)



   #### - Implement free drawing and a simple shape recognition algorithm, capable of recognizing at least lines and circles.~✔️⏰ (no shape recognition..)
   
   In `pa\lab6\drawingapp\appextended\shape\DrawableShape` add a free drawing feature using an event listener and a list of point drawn to re-drawn when other shapes are erased. An extended description is found in the javadoc of the class:
   
   ````java
   
   /**
   * this class implements what a Shape class should offer -> please see Shape class wiki comments
   *
   * ALSO:
   * - draws a pen like path and preserves the list of point drawn for re-drawing at need
   *
   * HOW IT WORKS:
   * 1. attaches this instance as a event listener to the provided canvas
   * 2. wait for a click event
   * 3. draws the shape while the left click is hold -> keep all the point drawn
   * 4. remove event listener when mouse is released so no other shape is draw by this instance !!
   *
   * NOT DONE:
   * - the color is not the one selected
   */
 
 ```
