package GameEngine;

import java.awt.*;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.*;
import javax.swing.JOptionPane;

/**
 * This is the Game Engine - Main Game || Ini "Mesin Utama Game nya"
 */
public class Frame extends JFrame {

    private static final int EASY = 1200;
    private static final int MEDIUM = 600;
    private static final int HARD = 300;
    
    public void gameEasy() {
        // Buat objek frame dari JFrame (parent)
        JFrame frame = new JFrame("Easy Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Atur Framesize
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

        // GANTI KURSOR JDI PALU
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        // Mengatur gameUI.png jdi background
        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        // Membuat scene dari class SceneComponent
        final SceneComponent scene = new SceneComponent();

        // ArrayList untuk menyimpan hole
        ArrayList<Hole> holes = new ArrayList<>();

        int speed = EASY;
        
        // Memainkan musik setelah kesulitan telah dipilih and kecepatan sudah diset
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

        // Membuat 5 Lubang dan tikus dan menambahkannya ke scene
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

        // Membuat lubang dan tikus di layar dan menambahkan penghitung waktu animasi sehingga bisa membesar/menyusut
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

                // Jika Timer belum berjalan, mulai (untuk di awal)
                if (!scene.hasTimerStarted())
                    scene.startTimer();

                // Mereset indikator "mendapat poin".
                scene.resetPointAnimation();

                //Jika timer mencapai 0 dtk
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
                        // Else If -> Exit dari game
                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }
                // Hasilkan koordinat acak untuk Lubang kiri bawah
                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                // Hasilkan koordinat acak untuk Lubang tengah kiri bawah
                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                // Hasilkan koordinat acak untuk Lubang tengah kanan bawah
                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                // Hasilkan koordinat acak untuk Lubang kanan bawah
                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                // Hasilkan koordinat acak untuk Lubang kiri atas
                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

                // Hasilkan koordinat acak untuk Lubang kanan atas
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

                // Pilih lubang acak tempat tikus keluar
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
        // Harus muncul dalam urutan ini atau jika tidak, wallpaper png menutupi lubang & tikus
        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
    
    public void gameMed() {

        JFrame frame = new JFrame("Medium Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

  
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        final SceneComponent scene = new SceneComponent();

        ArrayList<Hole> holes = new ArrayList<>();

        int speed = MEDIUM;
        
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

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

        Timer animator = new Timer(speed, animationEvent -> {

            if (hole.getWidth() == 0) {

                if (!scene.hasTimerStarted())
                    scene.startTimer();

                scene.resetPointAnimation();

                if (scene.getTime() == 0) {
                    backgroundMusic.stop();
                    scene.setTimerStarted(false);

                    String[] options = {"Restart", "Exit"};
                    int userChoice = -1;
                  
                    while (userChoice != 0) {
                        userChoice = JOptionPane.showOptionDialog(null, "GAME OVER! " +
                                        "Your score is : " + scene.getScore()+ '\n' +"Don't Forget To Save Your Score Manually on Leaderboard!", "Your Results", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);

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
                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }

                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

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
        
        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
    
        public void gameHard() {
            
        JFrame frame = new JFrame("Hard Mode - Whack A Mole | 3A-PSTI");
        //Add icon on JFrame
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage("src\\GameEngine\\ico.png"));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(1620,1080);

        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("src\\GameEngine\\palu1.png");
        Image scaledImage = image.getScaledInstance(1500, 1500, Image.SCALE_DEFAULT);
        Cursor cursor = toolkit.createCustomCursor(scaledImage , new Point(frame.getX(), frame.getY()), "hammer");
        frame.setCursor(cursor);

        JLabel background = new JLabel("");
        background.setIcon(new ImageIcon("src\\GameEngine\\gameUI.png"));
        background.setBounds(0,0, 1620, 1080);

        final SceneComponent scene = new SceneComponent();

        ArrayList<Hole> holes = new ArrayList<>();

        int speed = HARD;
        
        Audio backgroundMusic = new Audio("src\\GameEngine\\MusicPBO.wav");
        backgroundMusic.play();

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

        Timer animator = new Timer(speed, animationEvent -> {

            if (hole.getWidth() == 0) {


                if (!scene.hasTimerStarted())
                    scene.startTimer();


                scene.resetPointAnimation();


                if (scene.getTime() == 0) {
                    backgroundMusic.stop();
                    scene.setTimerStarted(false);


                    String[] options = {"Restart", "Exit"};
                    int userChoice = -1;
                  
                    while (userChoice != 0) {
                        userChoice = JOptionPane.showOptionDialog(null, "GAME OVER! " +
                                        "Your score is : " + scene.getScore()+ '\n' +"Don't Forget To Save Your Score Manually on Leaderboard!", "Your Results", JOptionPane.DEFAULT_OPTION,
                                JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);


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

                        else if (userChoice == 1) {
                            System.exit(0);
                        }
                    }
                }
                hole.setX((int) (Math.random() * (screenSize.width / 5 - 125)) + 125);
                hole.setY((int) (Math.random() * ((screenSize.height - 100) - screenSize.height * 5/7)) +
                        screenSize.height * 5/7);

                hole2.setX((int) (Math.random() * (screenSize.width /2 - screenSize.width /4)) +
                        screenSize.width /4);
                hole2.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4)) +
                        screenSize.height * 3/4));

                hole3.setX((int) (Math.random() * (((screenSize.width * 3/4 - 50 )- screenSize.width /2 - 25) -
                        screenSize.width / 2 - 25)) +
                        screenSize.width * 4 / 5);
                hole3.setY((int) (Math.random() * ((screenSize.height - 100 - screenSize.height * 3/4 + 5)) +
                        screenSize.height * 3/4 + 5));

                hole4.setX((int) (Math.random() *(125)) +
                        screenSize.width * 3/4 + 125);
                hole4.setY((int) (Math.random() * ((screenSize.height - 125 ) - screenSize.height * 2 / 3 + 5 + 25)) +
                        screenSize.height * 2 / 3 + 25);

                hole5.setX((int) (Math.random() * ((screenSize.width / 2 - 25)  - screenSize.width /4)) +
                        screenSize.width / 4);
                hole5.setY((int) (Math.random() * ((screenSize.height * 3/4 - 100 ) - screenSize.height * 3/5 + 25)) +
                        screenSize.height  * 3 / 5 + 25);

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

        frame.add(scene);
        frame.setVisible(true);
        frame.add(background);
    }
}
