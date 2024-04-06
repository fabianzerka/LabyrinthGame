import java.awt.*;

public class Main {

    static boolean loading;
    //static GraphicsDevice device = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[0];
    static boolean MenujaOnStartUP = true; // mban a u hap loja per here te pare
    static int highScore = 0; // mban piket me te larta from the start saved
    static int numriNivelit = 0; // ruan nivelin me te larget saved
    static int pikerRunning; // mban piket me te larta from the start saved
    static int numriNivelitRunning; // ruan nivelin me te larget saved
    static int fullScreenX = (int)Toolkit.getDefaultToolkit().getScreenSize().getWidth(); // lexon permasat e ekranit full screen X
    static int fullScreenY = (int)Toolkit.getDefaultToolkit().getScreenSize().getHeight(); // lexon permasat e ekranit full screen Y
    private static Muzike muzike;

    public static void main(String[] args) {
        new Menu();
        muzike=new Muzike();
        playMusic(1);
    }


    public static void playMusic(int i) {
        muzike.setFile(i);
        muzike.play();
        muzike.loop();
    }
    public void stopMusic() {
        muzike.stop();}
}