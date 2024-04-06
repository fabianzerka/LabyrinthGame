import javax.swing.*;
import java.awt.*;

public class Thesari extends JLabel {

    public static int x;
    public static int y;

    public Thesari(int X, int Y) {

        //konstruktori

        this.x = X;
        this.y = Y;
        this.setBounds(X, Y, 200, 200); // 2 nr e pare: pozicioni i karakterit 2 nr e dyte madhesia e hitbox-it

        ImageIcon thesari = new ImageIcon("Asete/GlowingWisp.gif");
        this.setIcon(thesari);
        this.setVisible(true);

    }
}