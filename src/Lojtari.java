
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lojtari extends JLabel implements KeyListener {

    static public int X;
    static public int Y;


    ImageIcon runR;
    ImageIcon runL;
    ImageIcon idleR;
    ImageIcon idleL;
    Thesari thesari;
    Labirinti labirinti;

    Timer fallTimer;
    public Lojtari(int X, int Y, Labirinti labirinti) {

        this.labirinti=labirinti;
        this.X = X;
        this.Y = Y;
        this.setBounds(X, Y,80 , 80); // 2 nr e pare: pozicioni i karakterit 2 nr e dyte madhesia e hitbox-it

        runR = new ImageIcon("Asete/RunR.gif");
        runL = new ImageIcon("Asete/RunL.gif");
        idleR = new ImageIcon("Asete/IdleR.gif");
        idleL = new ImageIcon("Asete/IdleL.gif");

        this.setIcon(idleR);


        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);

        this.setVisible(true);

    }

    @Override
    public void keyTyped(KeyEvent e) {}
    @Override
    public void keyPressed(KeyEvent e) {

        X = this.getX();
        Y = this.getY();

        //heqja e thesarit nese preket
        if (prekThesarin()) {
            labirinti.remove(labirinti.getThesari());
            Game.piketAktuale += 1;
            labirinti.repaint();
        }

        // Kontrollon levizjen
        switch(e.getKeyCode()) {
            case 37: levizMajtas(); //LEFT
                break;
            case 38: levizLart(); //UP
                break;
            case 39: levizDjathtas(); //RIGHT
                break;
            case 40: levizPoshte(); //DOWN
                break;

        }

        // Nuk lejon kalimin ne mure
        if(!prekMur())
            this.setLocation(X, Y);

       // Krijon nivel te ri kur lojtari arrin ne fund
        if(eshteNeDalje()){

            Main.numriNivelitRunning = Game.numriNivelit +1;
            Main.pikerRunning = Game.piketAktuale+1;

            MurHorizontal.width = Labirinti.WidthLabirinti /(Game.llogaritKuti(Main.numriNivelitRunning).x+1);
            MurHorizontal.height = MurHorizontal.width/3;
            MurVertikal.height = (Labirinti.HeightLabirinti /(Game.llogaritKuti(Main.numriNivelitRunning).y+1));
            MurVertikal.width = MurVertikal.height*20/135;

            Menu oldMenu = Game.getMenu();
            oldMenu.nextGame();

        }


        // Heq collectable nqs takohen

    }
    @Override
    public void keyReleased(KeyEvent e) {
        switch(e.getKeyCode()) {
            case 37:
                    this.setIcon(idleL);
                break;
            case 38, 40:
                if(this.getIcon()==runR)
                        this.setIcon(idleR);
                else this.setIcon(idleL);
                break;
            case 39:
                    this.setIcon(idleR);
                break;
        }
    }
    public boolean eshteNeDalje(){

       // Rectangle hitbox = new Rectangle(Labirinti.getQelizaRendesishme(Labirinti.DALJE).x+MurHorizontal.width, Labirinti.getQelizaRendesishme(Labirinti.DALJE).y, 50, 50);
        Rectangle hitbox = new Rectangle(Game.WIDTHdritares, 0, 50, Main.fullScreenY);

        boolean intersects = false;
        if (hitbox.intersects(this.getBounds())) {
                intersects=true;
        }
        return intersects;
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
    public boolean prekThesarin(){
       // Ketu rregullon sa afer asaj gjes duhet te jete qe ta mbledhi
        Rectangle hitbox = new Rectangle(Thesari.x, Thesari.y, this.getWidth()-15, this.getHeight()-5);

        boolean collect = false;
            if (hitbox.intersects(this.getBounds())) {
                collect=true;
        }
        return collect;
    }

    // Te metodat e levizjes mund te ndryshosh shpejtesine
    public void levizMajtas(){
        X-=20;
            this.setIcon(runL);
    }
    public void levizDjathtas(){
        X+=20;
            this.setIcon(runR);
    }
    public void levizLart(){
        Y-=20;
        if (this.getIcon()==runR || this.getIcon()==idleR)
            this.setIcon(runR);
        else this.setIcon(runL);
    }
    private void levizPoshte() {
        Y+=20;
        if (this.getIcon()==runR || this.getIcon()==idleR)
            this.setIcon(runR);
        else this.setIcon(runL);
    }

}