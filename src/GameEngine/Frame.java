package GameEngine;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 * This is the Game Engine - Main Game
 */
public class Frame extends JFrame {

    private static final int EASY = 1200;
    private static final int MEDIUM = 600;
    private static final int HARD = 300;
    
    public void gameEasy() {
        // Creates the Frame
        JFrame frame = new JFrame("Easy Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set frame size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

        // GANTI KURSOR JDI PALU
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        // Sets background.png as wallpaper
        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        // Creates the scene
        final SceneComponent scene = new SceneComponent();

        // ArrayList to store holes.
        ArrayList<Hole> holes = new ArrayList<>();

        int speed = EASY;
        
        // Plays the music after difficulty has been chosen and speed has been set
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

        // Creates 5 Holes and Mole and adds them to the scene
        final Hole hole = new Hole(-100, 0, 0, 0);
        final Hole hole2 = new Hole(-100, 0, 0, 0);
        final Hole hole3 = new Hole(-100, 0, 0, 0);
        final Hole hole4 = new Hole(-100, 0, 0, 0);
        final Hole hole5 = new Hole(-100, 0, 0, 0);
        final Hole hole6 = new Hole(-100, 0, 0, 0);
        final Mole mole = new Mole(-100, 0, 0, 0);
        scene.add(hole);
        scene.add(hole2);
        scene.add(hole3);
        scene.add(hole4);
        scene.add(hole5);
        scene.add(hole6);
        scene.add(mole);

        // Sets up holes and mole on screen and adds animation timers to them so they can grow/shrink
        final int DELAY = 0;

        Timer t = new Timer(DELAY, event -> {
            scene.repaint();
        });
        hole.addAnimateTimer(t);
        hole2.addAnimateTimer(t);
        hole3.addAnimateTimer(t);
        hole4.addAnimateTimer(t);
        hole5.addAnimateTimer(t);
        hole6.addAnimateTimer(t);
        mole.addAnimateTimer(t);
        t.start();

        // RUN THE GAME...
        Timer animator = new Timer(speed, animationEvent -> {
            // If any Hole shrinks down to 0 (board is clear and has no Holes) || LUBANG BERSIH GAAD DI PAPAN
            if (hole.getWidth() == 0) {

                // If Timer is not already running, start it (for at the start)
                if (!scene.hasTimerStarted())
                    scene.startTimer();

                // Resets "earned a point" indicator
                scene.resetPointAnimation();

                // If the timer hits 0 -> BG MUSIK STOP, SET TIMERNYA BERHENTI
                if (scene.getTime() == 0) {
                    backgroundMusic.stop();
                    scene.setTimerStarted(false);

                    // ASK USER BUAT MILIH: RESTART ATAU EXIT
                    String[] options = {"Restart", "Exit"};
                    int userChoice = -1;
                  
                    while (userChoice != 0) {
                        userChoice = JOptionPane.showOptionDialog(null, "GAME OVER! " +
                                        "Your score is : " + scene.getScore()+ '\n' +"Don't Forget To Save Your Score Manually on Leaderboard!", "Your Results", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                        // PILIHAN UNTUK RESTART GAME
                        if (userChoice == 0) {
                            scene.setScore(0);
                            scene.setTime(60);
                            scene.startTimer();
                            scene.setTimerStarted(true);
                            try {
                                backgroundMusic.restart();
                            } catch (Exception e) {
                                System.out.println("Error with playing sound.");
                                e.printStackTrace();
                            }
                        }
                        // If user chose to view scores, display a separate Frame showing list of highest scores
                        // of this session.
                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }
                // Generate random coordinates for bottom left Hole
                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                // Generate random coordinates for bottom left middle Hole
                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                // Generate random coordinates for bottom right middle Hole
                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                // Generate random coordinates for bottom right Hole
                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                // Generate random coordinates for top left Hole
                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

                // Generate random coordinates for top right Hole
                hole6.setX((int) (Math.random() * ((screenSize.width * 6 /7 - 250) - screenSize.width * 4/7) - 25) +
                        screenSize.width * 4/7 - 25);
                hole6.setY((int) (Math.random() * ((screenSize.height * 4/5 - 75 ) - screenSize.height * 3/5)) +
                        screenSize.height  * 3/5);

                holes.add(hole);
                holes.add(hole2);
                holes.add(hole3);
                holes.add(hole4);
                holes.add(hole5);
                holes.add(hole6);

                // Pick the random hole for the mole to come out of
                int random = (int) (Math.random() * 6 - 0);
                Hole randomHole = holes.get(random);
                mole.setX(randomHole.getX());
                mole.setY(randomHole.getY());
            }

            hole.animate();
            hole2.animate();
            hole3.animate();
            hole4.animate();
            hole5.animate();
            hole6.animate();
            mole.animate();
        }
        );
        animator.start();
        // Must appear in this order or else png wallpaper gets masked over holes & mole
        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
    
    public void gameMed() {
        // Creates the Frame
        JFrame frame = new JFrame("Medium Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set frame size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

        // GANTI KURSOR JDI PALU
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        // Sets background.png as wallpaper
        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        // Creates the scene
        final SceneComponent scene = new SceneComponent();

        // ArrayList to store holes.
        ArrayList<Hole> holes = new ArrayList<>();

        int speed = MEDIUM;
        
        // Plays the music after difficulty has been chosen and speed has been set
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

        // Creates 5 Holes and Mole and adds them to the scene
        final Hole hole = new Hole(-100, 0, 0, 0);
        final Hole hole2 = new Hole(-100, 0, 0, 0);
        final Hole hole3 = new Hole(-100, 0, 0, 0);
        final Hole hole4 = new Hole(-100, 0, 0, 0);
        final Hole hole5 = new Hole(-100, 0, 0, 0);
        final Hole hole6 = new Hole(-100, 0, 0, 0);
        final Mole mole = new Mole(-100, 0, 0, 0);
        scene.add(hole);
        scene.add(hole2);
        scene.add(hole3);
        scene.add(hole4);
        scene.add(hole5);
        scene.add(hole6);
        scene.add(mole);

        // Sets up holes and mole on screen and adds animation timers to them so they can grow/shrink
        final int DELAY = 0;

        Timer t = new Timer(DELAY, event -> {
            scene.repaint();
        });
        hole.addAnimateTimer(t);
        hole2.addAnimateTimer(t);
        hole3.addAnimateTimer(t);
        hole4.addAnimateTimer(t);
        hole5.addAnimateTimer(t);
        hole6.addAnimateTimer(t);
        mole.addAnimateTimer(t);
        t.start();

        // RUN THE GAME...
        Timer animator = new Timer(speed, animationEvent -> {
            // If any Hole shrinks down to 0 (board is clear and has no Holes) || LUBANG BERSIH GAAD DI PAPAN
            if (hole.getWidth() == 0) {

                // If Timer is not already running, start it (for at the start)
                if (!scene.hasTimerStarted())
                    scene.startTimer();

                // Resets "earned a point" indicator
                scene.resetPointAnimation();

                // If the timer hits 0 -> BG MUSIK STOP, SET TIMERNYA BERHENTI
                if (scene.getTime() == 0) {
                    backgroundMusic.stop();
                    scene.setTimerStarted(false);

                    // ASK USER BUAT MILIH: RESTART ATAU EXIT
                    String[] options = {"Restart", "Exit"};
                    int userChoice = -1;
                  
                    while (userChoice != 0) {
                        userChoice = JOptionPane.showOptionDialog(null, "GAME OVER! " +
                                        "Your score is : " + scene.getScore()+ '\n' +"Don't Forget To Save Your Score Manually on Leaderboard!", "Your Results", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                        // PILIHAN UNTUK RESTART GAME
                        if (userChoice == 0) {
                            scene.setScore(0);
                            scene.setTime(60);
                            scene.startTimer();
                            scene.setTimerStarted(true);
                            try {
                                backgroundMusic.restart();
                            } catch (Exception e) {
                                System.out.println("Error with playing sound.");
                                e.printStackTrace();
                            }
                        }
                        // If user chose to view scores, display a separate Frame showing list of highest scores
                        // of this session.
                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }
                // Generate random coordinates for bottom left Hole
                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                // Generate random coordinates for bottom left middle Hole
                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                // Generate random coordinates for bottom right middle Hole
                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                // Generate random coordinates for bottom right Hole
                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                // Generate random coordinates for top left Hole
                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

                // Generate random coordinates for top right Hole
                hole6.setX((int) (Math.random() * ((screenSize.width * 6 /7 - 250) - screenSize.width * 4/7) - 25) +
                        screenSize.width * 4/7 - 25);
                hole6.setY((int) (Math.random() * ((screenSize.height * 4/5 - 75 ) - screenSize.height * 3/5)) +
                        screenSize.height  * 3/5);

                holes.add(hole);
                holes.add(hole2);
                holes.add(hole3);
                holes.add(hole4);
                holes.add(hole5);
                holes.add(hole6);

                // Pick the random hole for the mole to come out of
                int random = (int) (Math.random() * 6 - 0);
                Hole randomHole = holes.get(random);
                mole.setX(randomHole.getX());
                mole.setY(randomHole.getY());
            }

            hole.animate();
            hole2.animate();
            hole3.animate();
            hole4.animate();
            hole5.animate();
            hole6.animate();
            mole.animate();
        }
        );
        animator.start();
        // Must appear in this order or else png wallpaper gets masked over holes & mole
        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
    
        public void gameHard() {
        // Creates the Frame
        JFrame frame = new JFrame("Hard Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Set frame size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

        // GANTI KURSOR JDI PALU
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        // Sets background.png as wallpaper
        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        // Creates the scene
        final SceneComponent scene = new SceneComponent();

        // ArrayList to store holes.
        ArrayList<Hole> holes = new ArrayList<>();

        int speed = HARD;
        
        // Plays the music after difficulty has been chosen and speed has been set
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

        // Creates 5 Holes and Mole and adds them to the scene
        final Hole hole = new Hole(-100, 0, 0, 0);
        final Hole hole2 = new Hole(-100, 0, 0, 0);
        final Hole hole3 = new Hole(-100, 0, 0, 0);
        final Hole hole4 = new Hole(-100, 0, 0, 0);
        final Hole hole5 = new Hole(-100, 0, 0, 0);
        final Hole hole6 = new Hole(-100, 0, 0, 0);
        final Mole mole = new Mole(-100, 0, 0, 0);
        scene.add(hole);
        scene.add(hole2);
        scene.add(hole3);
        scene.add(hole4);
        scene.add(hole5);
        scene.add(hole6);
        scene.add(mole);

        // Sets up holes and mole on screen and adds animation timers to them so they can grow/shrink
        final int DELAY = 0;

        Timer t = new Timer(DELAY, event -> {
            scene.repaint();
        });
        hole.addAnimateTimer(t);
        hole2.addAnimateTimer(t);
        hole3.addAnimateTimer(t);
        hole4.addAnimateTimer(t);
        hole5.addAnimateTimer(t);
        hole6.addAnimateTimer(t);
        mole.addAnimateTimer(t);
        t.start();

        // RUN THE GAME...
        Timer animator = new Timer(speed, animationEvent -> {
            // If any Hole shrinks down to 0 (board is clear and has no Holes) || LUBANG BERSIH GAAD DI PAPAN
            if (hole.getWidth() == 0) {

                // If Timer is not already running, start it (for at the start)
                if (!scene.hasTimerStarted())
                    scene.startTimer();

                // Resets "earned a point" indicator
                scene.resetPointAnimation();

                // If the timer hits 0 -> BG MUSIK STOP, SET TIMERNYA BERHENTI
                if (scene.getTime() == 0) {
                    backgroundMusic.stop();
                    scene.setTimerStarted(false);

                    // ASK USER BUAT MILIH: RESTART ATAU EXIT
                    String[] options = {"Restart", "Exit"};
                    int userChoice = -1;
                  
                    while (userChoice != 0) {
                        userChoice = JOptionPane.showOptionDialog(null, "GAME OVER! " +
                                        "Your score is : " + scene.getScore()+ '\n' +"Don't Forget To Save Your Score Manually on Leaderboard!", "Your Results", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

                        // PILIHAN UNTUK RESTART GAME
                        if (userChoice == 0) {
                            scene.setScore(0);
                            scene.setTime(60);
                            scene.startTimer();
                            scene.setTimerStarted(true);
                            try {
                                backgroundMusic.restart();
                            } catch (Exception e) {
                                System.out.println("Error with playing sound.");
                                e.printStackTrace();
                            }
                        }
                        // If user chose to view scores, display a separate Frame showing list of highest scores
                        // of this session.
                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }
                // Generate random coordinates for bottom left Hole
                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                // Generate random coordinates for bottom left middle Hole
                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                // Generate random coordinates for bottom right middle Hole
                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                // Generate random coordinates for bottom right Hole
                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                // Generate random coordinates for top left Hole
                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

                // Generate random coordinates for top right Hole
                hole6.setX((int) (Math.random() * ((screenSize.width * 6 /7 - 250) - screenSize.width * 4/7) - 25) +
                        screenSize.width * 4/7 - 25);
                hole6.setY((int) (Math.random() * ((screenSize.height * 4/5 - 75 ) - screenSize.height * 3/5)) +
                        screenSize.height  * 3/5);

                holes.add(hole);
                holes.add(hole2);
                holes.add(hole3);
                holes.add(hole4);
                holes.add(hole5);
                holes.add(hole6);

                // Pick the random hole for the mole to come out of
                int random = (int) (Math.random() * 6 - 0);
                Hole randomHole = holes.get(random);
                mole.setX(randomHole.getX());
                mole.setY(randomHole.getY());
            }

            hole.animate();
            hole2.animate();
            hole3.animate();
            hole4.animate();
            hole5.animate();
            hole6.animate();
            mole.animate();
        }
        );
        animator.start();
        // Must appear in this order or else png wallpaper gets masked over holes & mole
        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
}
