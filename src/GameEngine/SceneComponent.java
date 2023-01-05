package GameEngine;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
* Komponen yang menampilkan adegan yang terdiri dari GrowableShapes yang ditampilkan oleh Frame. Mengelola skor, timer, suara klik
* efek dan menentukan apakah tikus telah diklik untuk mendapatkan poin.
 */
public class SceneComponent extends JComponent {

	private int time;
	private int score;
	private Point mousePoint;
	private ArrayList<GrowableShape> shapes;
	private Dimension screenSize;
	private Audio hitSound;
	private boolean timerStarted;

	// Untuk menunjukkan kepada pengguna bahwa Tahi Lalat telah diklik dan mereka mendapatkan satu poin
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
	Menambahkan bentuk ke scene.
	@param s bentuk yang akan ditambahkan
	 */
	public void add(GrowableShape s) {
		shapes.add(s);
		repaint();
	}

	/**
	 * Jika Mole telah diklik, perbarui skor, beri tahu pengguna bahwa mereka mencetak satu poin, dan mainkan efek suara.
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
	 * Memulai Timer nya dari 60 detik
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
	 * Mereset variabel untuk indikator "peroleh poin" karena Lubang dan tikus akan muncul kembali ke lokasi baru
	 */
	public void resetPointAnimation() {
		moleHasBeenClicked = false;
		moleX = 0;
		moleY = 0;
	}

	/**
	 * Menganimasikan indikator "peroleh poin" yang muncul setelah tikus diklik
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
	* Menarik Lubang, tikus, label SKOR, Label WAKTU ke layar
	* @param g konteks grafik
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
	* Memeriksa apakah Timer 60 detik telah dimulai
	* @return true jika Timer telah dimulai
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
