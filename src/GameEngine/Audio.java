package GameEngine;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.*;

/**
 * Handles playing the audio for the Game. Include play, pause, resume, and restart
 */
public class Audio {

    private Long currentFrame;
    private Clip clip;
    private static String filePath;

    /**
     * Constructs an Audio file
     * @param filepath the filepath to the audio file
     */
    public Audio(String filepath) {
        this.filePath = filepath;
        try {
            File musicPath = new File(filePath);
            if (musicPath.exists()) {
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
            } else {
                System.out.println("Can't find file");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays the audio file
     */
    public void play() {
        clip.start();
    }

    /**
     * Stops playing the audio file
     */
    public void stop() {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    /**
     * Restarts the audio file
     * @throws IOException
     * @throws LineUnavailableException
     * @throws UnsupportedAudioFileException
     */
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    /**
     * Resets the audio stream
     * @throws IOException
     * @throws UnsupportedAudioFileException
     * @throws LineUnavailableException
     */
    public void resetAudioStream() throws IOException, UnsupportedAudioFileException, LineUnavailableException {
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
    }
}
