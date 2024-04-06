import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Muzike {

    private Clip clip;

    public void setFile(int i) {
        try {
            File file;
            if (i == 1) {
                file = new File("Asete/dungeon_theme_2.wav");
            } else {
                file = new File("resurset/your_other_music_file.wav");
            }

            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);

            AudioFormat baseFormat = audioInputStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16, // Change this to 16-bit
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );

            AudioInputStream decodedAudioInputStream = AudioSystem.getAudioInputStream(decodedFormat, audioInputStream);
            clip = AudioSystem.getClip();
            clip.open(decodedAudioInputStream);

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        if (clip.isRunning())
            clip.stop();
        clip.setFramePosition(0);
        clip.start();
    }

    public void loop() {
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public void stop() {
        clip.stop();
    }
}