package GameEngine;

import java.awt.*;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import javax.swing.Timer;

/**
 * A Mole that will randomly pop out of one of the five randomly placed Holes. If the player clicks on it, they will
 * earn a point.
 */
public class Mole implements GrowableShape {

	private static final int MAX_SIZE = 100;
	private static final int MINIMUM_SIZE = 0;

	private int x;
	private int y;
	private int width;
	private int height;
	private boolean animating;
	private boolean clickable;
	private Status status;

	/**
	 * Constructs a Mole
	 * @param x the X coordinate of center of the base of the mole; i.e. the center of the hole it's coming out of
	 * @param y the Y coordinate of center of the base of the mole; i.e. the center of the hole it's coming out of
	 * @param width the diameter of the semicircle that is the mole's head, as well as the width of it's body
	 * @param startHeight how tall the body of the mole is; start at zero to show the mole emerging from the hole
	 */
	public Mole(int x, int y, int width, int startHeight) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = startHeight;
		this.animating = true;
    	this.clickable = true;
		this.status = Status.GROWING;
	}

	/**
	 * Adds an animation Timer to Mole that allows it to grow/shrink and when it should stop growing/shrinking
	 * @param t the animation Timer
	 */
	public void addAnimateTimer(Timer t) {
		t.addActionListener(animateEvent -> {
			if (animating && this.width < MAX_SIZE && status == Status.GROWING) {
				this.width++;
				this.height++;
				// Reached max size, stop growing
				if (this.width == MAX_SIZE) { animating = false; status = Status.SHRINKING; }
			}
			else if (animating && this.width > MINIMUM_SIZE && status == Status.SHRINKING) {
				this.width--;
				this.height--;
				// Reached minimum size, stop shrinking
				if (this.width == MINIMUM_SIZE) { animating = false; status = Status.GROWING; }
			}
		});
	}

	/**
	 * Sets the animating to true if the Mole is currently in animation (growing/shrinking)
	 * Since it is animating, allow the Mole to be clickable for points
	 */
	public void animate() {
		this.animating = true;
		if (status == Status.GROWING) clickable = true;
	}

	/**
	 * Occurs when a Mole is whacked at a valid time (hasn't been hit before on this animation cycle)
	 * Sets clickable to false since Mole should not be allowed to be clicked again until it reappears
	 */
	public void moleHasBeenClicked() {
		clickable = false;
	}

	/**
	 * Checks whether the Mole has been clicked yet
	 * @return true if the Mole has not been clicked
	 */
	public boolean isClickable() {
		return this.clickable;
	}

	/**
	 * Checks whether the Mole has a mouse pointer over it
	 * @param p the mouse point
	 * @return true if there is a mouse pointer over the Mole
	 */
	public boolean contains(Point2D p) {
		return x-(width /2) <= p.getX() && x+(width /2) >= p.getX() && y-height-(width /2) <= p.getY() && y >= p.getY();
	}

	/**
	 * Draws the Mole using the graphics context
	 * @param g2 the graphics context
	 */
	public void draw(Graphics2D g2) {
		Arc2D.Double head = new Arc2D.Double(this.x-(width /2), this.y-height-(width /2), width, width, 0,
				180, Arc2D.PIE);
		Rectangle2D.Double body = new Rectangle2D.Double(this.x-(width /2), this.y-height, width, height);
		Ellipse2D.Double nose = new Ellipse2D.Double(x - (width /4), y - height - width /4, (width /2), (width /4));
		Ellipse2D.Double leftNostril = new Ellipse2D.Double(x - (width /12) - (width /8), y - height - width /6,
				width /8, width /8);
		Ellipse2D.Double rightNostril = new Ellipse2D.Double(x + (width /12), y - height - width /6, width /8,
				width /8);
		Ellipse2D.Double leftEye = new Ellipse2D.Double(x - (width /12) - (width /10), y - height - width /2.5,
				width /12, width /12);
		Ellipse2D.Double rightEye = new Ellipse2D.Double(x + (width /12), y - height - width /2.5,
				width /12, width /12);

		g2.setColor(new Color(0xD0A43D));
		g2.fill(head);
		g2.fill(body);
		g2.setColor(new Color(0xF299B1));
		g2.fill(nose);
		g2.setColor(Color.BLACK);
		g2.draw(leftNostril);
		g2.draw(rightNostril);
		g2.fill(leftEye);
		g2.draw(leftEye);
		g2.fill(rightEye);
		g2.draw(rightEye);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}
}
