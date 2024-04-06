import javax.swing.*;
import java.awt.*;

public class MurVertikal extends JLabel {

    private int X;
    private int Y;

    public static int height = (Labirinti.HeightLabirinti /(Labirinti.nrKutiY+1));
    public static int width = height*20/135;
    ImageIcon murV;
    public MurVertikal(int X, int Y){
        this.X=X;
        this.Y=Y;

        this.setBounds(this.X,this.Y,width,height+MurHorizontal.height);

        ImageIcon originalIcon = new ImageIcon("Asete/MurVertikal1.jpg");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        murV = new ImageIcon(resizedImage);

        this.setIcon(murV);
        this.setVisible(true);
    }

}
