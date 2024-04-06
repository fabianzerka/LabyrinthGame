import javax.swing.*;
import java.awt.*;

public class MurHorizontal extends JLabel {

    private int X;
    private int Y;

    public static int width = Labirinti.WidthLabirinti /(Labirinti.nrKutiX+1);
    public static int height = width/2;

    ImageIcon murH;
    public MurHorizontal(int X, int Y){
        this.X=X;
        this.Y=Y;
        this.setBounds(this.X,this.Y,width,height);
        ImageIcon originalIcon = new ImageIcon("Asete/MurHorizontal3.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        murH = new ImageIcon(resizedImage);

        this.setIcon(murH);
        this.setVisible(true);
    }

}
