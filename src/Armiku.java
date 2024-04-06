import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Armiku extends JLabel {
    private int X;
    private int Y;
    Random random = new Random();
    int numri = random.nextInt(1,5);

    ImageIcon runR;
    ImageIcon runL;
    ImageIcon idleR;
    ImageIcon idleL;


    Timer fallTimer;
    public Armiku(int X, int Y) {

        this.X = X;
        this.Y = Y;
        this.setBounds(X, Y,80 , 80); // 2 nr e pare: pozicioni i karakterit 2 nr e dyte madhesia e hitbox-it

        runR = new ImageIcon("Asete/vrapi.gif");
        runL = new ImageIcon(pasqyroImazhin(runR.getImage()));
        idleR = new ImageIcon("Asete/qendrim.gif");
        idleL = new ImageIcon(pasqyroImazhin(idleR.getImage()));

        this.setIcon(idleR);


        this.setFocusable(true);
        this.requestFocusInWindow();
        this.setVisible(true);

    }


    public void levizjaArmikut() {

        X = this.getX();
        Y = this.getY();

        // Kontrollon levizjen dhe pluskimin
        switch(numri) {
            case 1: levizMajtas(); //LEFT
                break;
            case 2: levizLart(); //UP
                break;
            case 3: levizDjathtas(); //RIGHT
                break;
            case 4: levizPoshte(); //DOWN
                break;



        }


    }

    public boolean prekMur(){
        // Ketu mund te rregullosh sa afer murit e do te rrije
        Rectangle hitbox = new Rectangle(X+20, Y+60, this.getWidth()-50, 5);

        boolean intersects = false;
        for (Rectangle x : Labirinti.kufinjtMure) {
            if (hitbox.intersects(x.getBounds())) {
                intersects=true;
            }
        }
        return intersects;
    }

    // Te metodat e levizjes mund te ndryshosh shpejtesine
    public void levizMajtas(){
        X-=10;
        if(!prekMur()){
            this.setLocation(X, Y);
            numri++;
        }
        else{
            this.setIcon(runL);
        }

    }
    public void levizDjathtas(){
        X+=10;
        if(!prekMur()){
            this.setLocation(X, Y);
            numri++;
        }
        else{
            this.setIcon(runR);
        }
    }
    public void levizLart(){
        Y-=10;
        if(!prekMur()){
            this.setLocation(X, Y);
            numri++;
        }
        if (this.getIcon()==runR || this.getIcon()==idleR) {
            this.setIcon(runR);
        }
        else {
            this.setIcon(runL);
        }
    }
    private void levizPoshte() {
        Y+=10;
        if(!prekMur()){
            this.setLocation(X, Y);
            numri=1;
        }
        if (this.getIcon()==runR || this.getIcon()==idleR){
            this.setIcon(runR);
        }
        else {
            this.setIcon(runL);
        }
    }

    //pasqyrojme gifet
    private static Image pasqyroImazhin(Image imazhiOrigjinal){
        int gjeresia = imazhiOrigjinal.getWidth(null);
        int gjatesia = imazhiOrigjinal.getHeight(null);

        //bejme nje buffer qe te pasqyrojm imazhin e par
        BufferedImage imazhiPasqyruar = new BufferedImage(gjeresia,gjatesia,BufferedImage.TYPE_INT_ARGB);
        Graphics2D graphics2D = imazhiPasqyruar.createGraphics();
        AffineTransform trasformimi = new AffineTransform(-1,0,0,1,gjeresia,0);
        graphics2D.setTransform(trasformimi);
        graphics2D.drawImage(imazhiOrigjinal,0,0,null);
        graphics2D.dispose();

        return imazhiPasqyruar;
    }

}