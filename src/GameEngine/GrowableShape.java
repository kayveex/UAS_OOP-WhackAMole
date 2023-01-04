package GameEngine;

import java.awt.*;
import java.awt.geom.Point2D;

import javax.swing.Timer;

/**
 * A shape that can change its size. Shapes that need to grow/shrink will implement this interface.
 */
public interface GrowableShape {

    /**
     Draws the shape.
     @param g2 the graphics context
     */
    void draw(Graphics2D g2);

    /**
     Increases or decreases the size (animates) of the shape.
     */
    void animate();

    /**
     * Gives the GrowableShape a Timer that will allow it to be animated
     * @param t the animation Timer
     */
    void addAnimateTimer(Timer t);

    /**
     * Determines whether the GrowableShape contains the mouse point (click)
     * @param p the mouse point
     * @return true if the shape has the point
     */
    boolean contains(Point2D p);
}
