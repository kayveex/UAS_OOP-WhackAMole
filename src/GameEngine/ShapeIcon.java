package GameEngine;

import java.awt.*;
import javax.swing.*;

/**
 *  Ikon yang berisi bentuk bergerak. Bentuknya akan ditempatkan ke dalam Ikon ini sehingga bisa diletakkan di JLabel
 *
 * @author Kelompok 2
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
