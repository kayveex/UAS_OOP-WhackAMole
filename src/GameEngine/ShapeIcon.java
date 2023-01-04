package GameEngine;

import java.awt.*;
import javax.swing.*;

/**
 * An icon that contains a moveable shape. The shape will be placed into this Icon so it can be put in a JLabel
 *
 * @author Kelompok 1
 *
 */
public class ShapeIcon implements Icon {

    private int width;
    private int height;
    private GrowableShape shape;

    public ShapeIcon(GrowableShape shape, int width, int height) {
        this.shape = shape;
        this.width = width;
        this.height = height;
    }

    public int getIconWidth() {
        return width;
    }

    public int getIconHeight() {
        return height;
    }

    public void paintIcon(Component c, Graphics g, int x, int y) {
        Graphics2D g2 = (Graphics2D) g;
        shape.draw(g2);
    }
}