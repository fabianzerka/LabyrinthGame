import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.util.Date;

public class Game extends JFrame {

    static int piketAktuale =0;
    static int numriNivelit =2;

    private static Menu menu;
    public static int WIDTHdritares = Main.fullScreenX;
    public static int HEIGHTdritares = Main.fullScreenY;
    JLayeredPane layeredPane = new JLayeredPane();

    Labirinti labirinti;





    public Game(Menu menu) {
        if(!Main.MenujaOnStartUP){
            numriNivelit = Main.numriNivelitRunning;
            piketAktuale = Main.pikerRunning;
        }
        this.menu = menu;
        this.setTitle("Labirinti " + new Date());
        this.setSize(WIDTHdritares, HEIGHTdritares);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ImageIcon logojaFrameit = new ImageIcon("Asete/logo.png"); //krijon ikonen si objekt
        this.setIconImage(logojaFrameit.getImage()); //ndryshon ikonen e framit

        layeredPane.setLayout(null);
        layeredPane.setBounds(0,0,WIDTHdritares, HEIGHTdritares);
        layeredPane.setPreferredSize(new Dimension(WIDTHdritares, HEIGHTdritares));

        JLabel piket = new JLabel(String.valueOf(piketAktuale));
        piket.setBackground(Color.black);
        piket.setOpaque(true);
        piket.setForeground(Color.WHITE);
        piket.setBounds(WIDTHdritares-100,0,100,50);
        piket.setHorizontalTextPosition(JLabel.CENTER);
        layeredPane.add(piket,JLayeredPane.PALETTE_LAYER);

        //Vendos karkterin, muret, collectables ne layers te ndryshme
        if(Main.loading){
            labirinti = Menu.labirinti;
        }
        else
            labirinti = new Labirinti(llogaritKuti(numriNivelit).x, llogaritKuti(numriNivelit).y);
        layeredPane.add(labirinti,JLayeredPane.PALETTE_LAYER);

        // ******************
        //Pret shtypjen e esc per te rishfaqur Menune
        Action escapeAction = new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.rishfaqMenunPaused();
            }
        };

        KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
        getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
        getRootPane().getActionMap().put("ESCAPE", escapeAction);
        //**********************


        this.add(layeredPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    public static Point llogaritKuti(int numriNivelit){
        return new Point((WIDTHdritares/HEIGHTdritares)*numriNivelit + 3,numriNivelit +2);
    }

    public static Menu getMenu() {
        return menu;
    }
}

