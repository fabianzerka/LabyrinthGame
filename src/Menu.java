import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Menu extends JFrame implements ActionListener {

    Game newGame;
    JLayeredPane layeredPane = new JLayeredPane();
    ImageIcon sfondi;
    JLabel label;
    JButton start;
    JButton exit;
    JButton cont;
    JButton save;
    JButton delete;
    Font PixelGame;
    static int lojtariX, lojtariY; // variabla temporar per leximin nga file dhe global per tu implementuar te lojtari
    static int thesariX, thesariY;
    static boolean[][] matricaNdarjetSaved; // matrica temprorar per leximin e labirintit dhe dergimin te vizatimi
    static boolean kaSaveFile;  // ekzsiton apo jo file i savit
    static Labirinti labirinti; // objekt labirint temporar per dergim ne klasen game nga leximi i savefile-it
    static File file = new File("Asete/savefile.txt");

    public Menu() {
        if(Main.MenujaOnStartUP) {/* KJO ESHTE PER FONT TE RI
            try {
                PixelGame = Font.createFont(Font.TRUETYPE_FONT, new File("PixelGameFont.ttf")).deriveFont(Font.PLAIN, 20f);
                GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
                //register fontin
                ge.registerFont(PixelGame);
            } catch (FontFormatException e) {
                throw new RuntimeException(e);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }*/
            menaxhoSaveFile();
        }
        this.setSize(1000, 600);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        layeredPane.setLayout(null);

        Sfondi();

        //Shtohen butona ktu por duhen deklaruar jashte konstruktorit fillimisht
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new FlowLayout());
        start = new JButton();
        cont = new JButton();
        exit = new JButton();
        save = new JButton();
        delete = new JButton();

        //Madhesia e butonave ktu
        start.setPreferredSize(new Dimension(200, 50));
        cont.setPreferredSize(new Dimension(200, 50));
        exit.setPreferredSize(new Dimension(200, 50));
        save.setPreferredSize(new Dimension(200, 50));
        delete.setPreferredSize(new Dimension(200, 50));


        ImageIcon button = new ImageIcon("Asete/Base-01.png");
        start.setIcon(button);
        start.setText("Start Game");
        start.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,20));
        start.setVerticalTextPosition(JButton.CENTER);
        start.setHorizontalTextPosition(JButton.CENTER);

        cont.setIcon(button);
        cont.setText("Continue");
        cont.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,20));
        cont.setVerticalTextPosition(JButton.CENTER);
        cont.setHorizontalTextPosition(JButton.CENTER);

        exit.setIcon(button);
        exit.setText("Quit Game");
        exit.setFont(new Font("Matura MT Script Capitals",Font.PLAIN, 20));
        exit.setVerticalTextPosition(JButton.CENTER);
        exit.setHorizontalTextPosition(JButton.CENTER);

        save.setIcon(button);
        save.setText("Save Game");
        save.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,20));
        save.setVerticalTextPosition(JButton.CENTER);
        save.setHorizontalTextPosition(JButton.CENTER);

        delete.setIcon(button);
        delete.setText("Delete Saved");
        delete.setFont(new Font("Matura MT Script Capitals",Font.PLAIN,20));
        delete.setVerticalTextPosition(JButton.CENTER);
        delete.setHorizontalTextPosition(JButton.CENTER);

        start.setFocusable(false);
        cont.setFocusable(false);
        exit.setFocusable(false);
        save.setFocusable(false);
        delete.setFocusable(false);

        start.addActionListener(this);
        exit.addActionListener(this);
        cont.addActionListener(this);
        save.addActionListener(this);
        delete.addActionListener(this);

        //Madhesia dhe pozicioni i panelit te butonave ktu
        buttonsPanel.setBounds(400, 150, 200, 300);

        buttonsPanel.add(cont);
        buttonsPanel.add(start);
        buttonsPanel.add(save);
        buttonsPanel.add(delete);
        buttonsPanel.add(exit);


        buttonsPanel.setOpaque(false);
        layeredPane.add(buttonsPanel, JLayeredPane.PALETTE_LAYER);

        // Ktu ben butonin Continue te pashtypshem heren e par dhe nese nuk ka savefile
        if(Main.MenujaOnStartUP) {
            save.setVisible(false);
            if(!kaSaveFile) {
                delete.setVisible(false);
                cont.setEnabled(false);
            }
        }

        this.add(layeredPane);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

    }

    private void menaxhoSaveFile() {

        if(file.exists()) kaSaveFile = true; //butoni i dukshem kur ka safe file - menuja false (vlera fillestare false caktuar nga rei)
        else kaSaveFile = false; // butoni cont frozen kur s'ka safe file - menuja true
    }

    private void Sfondi() {
        ImageIcon originalIcon = new ImageIcon("Asete/Background.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        sfondi = new ImageIcon(resizedImage);
        label = new JLabel();
        label.setIcon(sfondi);
        label.setBounds(0, 0, 1000, 600);
        layeredPane.add(label, JLayeredPane.DEFAULT_LAYER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Shtypet starti behet e padukshme menuja. Loaded klasa Game. Fshihet savefile.
        if (e.getSource() == start) {
            if(kaSaveFile) {
                try { //ruajtja e highscorit te vjeter para fshires se savefile-it
                    BufferedReader lexues = new BufferedReader(new FileReader(file));
                    Main.highScore = Integer.parseInt(lexues.readLine());
                    lexues.readLine();
                    Main.numriNivelit = Integer.parseInt(lexues.readLine());
                    lexues.close();
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
            }
            newGame = new Game(this);
            this.setVisible(false);
            Main.MenujaOnStartUP = false;
        }

        // Mbyll lojen
        if (e.getSource() == exit) {
            System.exit(0);
        }

        // Vazhdon lojen
        if (e.getSource() == cont) {
            if(newGame == null){ //loja ka savefile dhe eshte hapur per here te pare

                try {
                    Main.loading = true; //deklarimi i modes loading per kufizim ne klasat Labirinti dhe Game
                    BufferedReader lexues = new BufferedReader(new FileReader(file));
                    //leximi i highScore-it, nivelit, pozicionit te lojtarit
                    Main.highScore = Integer.parseInt(lexues.readLine());
                    int piketAktuale = Integer.parseInt(lexues.readLine());
                    Main.numriNivelit = Integer.parseInt(lexues.readLine());
                    int numriNivelit = Integer.parseInt(lexues.readLine());
                    lojtariX = Integer.parseInt(lexues.readLine());
                    lojtariY = Integer.parseInt(lexues.readLine());
                    thesariX = Integer.parseInt(lexues.readLine());
                    thesariY = Integer.parseInt(lexues.readLine());


                    //leximi i matrices se labirintit
                    matricaNdarjetSaved = new boolean[Integer.parseInt(lexues.readLine())][Integer.parseInt(lexues.readLine())];
                    for(int j = 0; j<matricaNdarjetSaved.length; j++){
                        for(int i =0; i<matricaNdarjetSaved[j].length; i++){
                            char tempChar = (char)lexues.read();
                            matricaNdarjetSaved[j][i] = tempChar == '1'? true : false;
                        }
                    }
                    labirinti = new Labirinti();
                    newGame = new Game(this); //parametrat e savefile-it
                    Main.MenujaOnStartUP = false;
                    Game.piketAktuale = piketAktuale;
                    Game.numriNivelit = numriNivelit;
                    lexues.close();
                    Main.loading = false;

                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }else {
                this.setVisible(false); // loja eshte bere pause dhe behet resume edhe nese ka savefile te krijuar ne ate exe.
            }
        }
        // Bej save lojen
        if(e.getSource() == save){
            try {shkruajFilein();}
            catch (IOException ex) { throw new RuntimeException(ex);}
        }

        // Fshi savefile-in
        if(e.getSource() == delete){
            if(file.exists()) file.delete();
            if(Main.MenujaOnStartUP) this.cont.setEnabled(false);
            this.delete.setVisible(false);
        }
    }

    private void shkruajFilein() throws IOException {
        //shkrimi ne safefile. mbivendos nese ka nje ekzistues
        BufferedWriter shkruajtes = new BufferedWriter(new FileWriter(file));
        ArrayList<String> saveData = new ArrayList<>();

        //Ruajtja e pikeve
        if(Main.highScore < Game.piketAktuale)
            Main.highScore = Game.piketAktuale; // ruajtja e pikeve maks
        saveData.add(String.valueOf(Main.highScore)); // all-time high score
        saveData.add(String.valueOf(Game.piketAktuale )); // piket ne kete run

        //Ruajtja e nivelit
        if(Main.numriNivelit < Game.numriNivelit) {
            Main.numriNivelit = Game.numriNivelit; //ruajtja e nivelit max
        }
        saveData.add(String.valueOf(Main.numriNivelit));//niveli max all-time
        saveData.add(String.valueOf(Game.numriNivelit)) ; // Niveli ne kete run

        //Ruajtja e pozicionit te lojtarit
        saveData.add(String.valueOf(Lojtari.X));
        saveData.add(String.valueOf(Lojtari.Y));

        //Ruajtja e pozicionit te thesarit
        saveData.add(String.valueOf(Thesari.x));
        saveData.add(String.valueOf(Thesari.y));


        //Ruajtja e permasave te labirintit
        saveData.add(String.valueOf(Labirinti.matricaNdarjet.length));
        saveData.add(String.valueOf(Labirinti.matricaNdarjet[0].length));
        //Ruajtja e labirintit
        String rreshtiNdarjeve = "";
        for(int j=0; j< Labirinti.matricaNdarjet.length; j++){
            for(int i =0; i<Labirinti.matricaNdarjet[j].length; i++){
                rreshtiNdarjeve += Labirinti.matricaNdarjet[j][i]? "1" : "0";
            }
        }
        saveData.add(rreshtiNdarjeve);

        //Shkruajtja ne file te ndara me newline
        for(String temp: saveData){
            shkruajtes.write(temp);
            shkruajtes.write("\n");
        }

        shkruajtes.close();

    }

    void rishfaqMenunPaused(){
        this.cont.setEnabled(true);
        this.setVisible(true);
        this.save.setVisible(true);
        this.start.setVisible(false);
    }
    void nextGame(){
        System.out.println(Main.pikerRunning);
        newGame.dispose();
        newGame = new Game(this);

    }
}
