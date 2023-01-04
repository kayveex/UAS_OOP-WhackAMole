package GameEngine;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 * A component that shows a scene composed of GrowableShapes displayed by Frame. Manages the score, timer, clicked sound
 * effect and determining whether the Mole has been clicked for points.
 */
public class SceneComponent extends JComponent {

	private int time;
	private int score;
	private Point mousePoint;
	private ArrayList<GrowableShape> shapes;
	private Dimension screenSize;
	private Audio hitSound;
	private boolean timerStarted;

	// For showing user the Mole has been clicked and they earned a point
	private int moleX;
	private int moleY;
	private boolean moleHasBeenClicked;

	/**
	 * Creates a SceneComponent
	 */
	public SceneComponent() {
		this.time = 60;
		this.score = 0;
		addMouseListener(new MousePressedListener());
		this.shapes = new ArrayList<>();
		this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		this.hitSound = new Audio("src\\GameEngine\\HitSound.wav");
		this.timerStarted = false;

		this.moleHasBeenClicked = false;
		this.moleX = 0;
		this.moleY = 0;
	}

	/**
	 Adds a shape to the scene.
	 @param s the shape to add
	 */
	public void add(GrowableShape s) {
		shapes.add(s);
		repaint();
	}

	/**
	 * If the Mole has been clicked on, update the score, tell user they scored a point, and play sound effect
	 */
	private class MousePressedListener extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			mousePoint = event.getPoint();
			for (GrowableShape s : shapes) {
				if (s.contains(mousePoint) && s.getClass() == Mole.class && ((Mole)s).isClickable()) {
					((Mole)s).moleHasBeenClicked();
					score++;
					moleHasBeenClicked = true;
					moleX = ((Mole)s).getX();
					moleY = ((Mole)s).getY();
					hitSound.play();
					break;
				}
			}
			repaint();         
		}
	}

	/**
	 * Starts the 60 seconds Timer aka the duration of one game
	 */
	public void startTimer() {
		Thread thread = new Thread(new Runnable () {
			@Override
			public void run() {
				for (int i = 60; i >= 1; i--) {
					try {
						Thread.sleep(1000);
						time--;
					}
					catch (InterruptedException e) {}
				}
			}
		});
		thread.start();
		timerStarted = true;
	}

	/**
	 * Resets variables for the "earned a point" indicator since Holes and Mole are going to respawn to new locations
	 */
	public void resetPointAnimation() {
		moleHasBeenClicked = false;
		moleX = 0;
		moleY = 0;
	}

	/**
	 * Animates the "earned a point" indicator floating up after Mole has been clicked
	 */
	public void pointAnimation() {
		for( int i = 1; i < 3; i++ ) {
			moleY--;
			repaint();
			try {
				Thread.sleep(1);
			}
			catch(InterruptedException ex) { }
		}
	}

	/**
	 * Draws the Holes, Mole, SCORE label, TIME Label onto the screen
	 * @param g the graphics context
	 */
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		for (GrowableShape s : shapes)
			s.draw(g2);

		g.setFont(new Font("Roboto Condensed", Font.BOLD, 70));
		g.setColor(Color.WHITE);
		g.drawString("SCORE: " + score, 100, 100);
		g.setFont(new Font("Roboto Condensed", Font.BOLD, 70));

		// Warning indicator for when there's less than 10 seconds remaining
		if (time <= 10)
			g.setColor(Color.RED);

		g.drawString("TIME: " + time, screenSize.width - 370 , 100);

		// If Mole has been clicked on, shows the "earned a point" indicator
		if (moleHasBeenClicked) {
			g.setColor(new Color(0x05820b));
			g.drawString("+1", moleX - 40, moleY - 175);
			pointAnimation();
		}
	}
	
	/**
	 * Checks whether the 60 second Timer has started
	 * @return true if the Timer has started
	 */
	public boolean hasTimerStarted() {
		return timerStarted;
	}

	public int getTime() {
		return time;
	}

	public int getScore() {
		return score;
	}

	public void setTimerStarted(boolean timerStarted) {
		this.timerStarted = timerStarted;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
