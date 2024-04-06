import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

public class Labirinti extends JPanel{
    public static final int HYRJE = 0;
    public static final int DALJE = 1;
    public static final int ME_LARG = 2;
    public static boolean[][] matricaNdarjet;
    public static int WidthLabirinti = Game.WIDTHdritares;
    public static int HeightLabirinti = Game.HEIGHTdritares;
    public static ArrayList<Rectangle> kufinjtMure = new ArrayList<>();

    Lojtari lojtari;
    Armiku armiku;
    ImageIcon sfondi;
    Thesari thesari;

    public static int nrKutiX, nrKutiY;
    KutiaQelize[][] matricaLabirintit;
    Stack<KutiaQelize> stackLabirinti = new Stack<>();
    static Point[] qelizaRendesishme = new Point[3]; // [0] hyrja, [1] dalje, [2] Me e largeta
    Labirinti(){//therritet nga loaded game
        this(Menu.matricaNdarjetSaved[0].length-1,(Menu.matricaNdarjetSaved.length-1)/2);
    }
    Labirinti(int nrKutiX, int nrKutiY){



        this.setBounds(0,0, WidthLabirinti, HeightLabirinti);
        this.nrKutiX = nrKutiX;
        this.nrKutiY = nrKutiY;

        //Gjenerimi i Labirintit Random ose Load
        if(!Main.loading)
        this.gjeneroLabirintin(nrKutiX, nrKutiY);
        else this.matricaNdarjet = Menu.matricaNdarjetSaved;

        //Konvertimi Koordinata Matrice ne Pixel
        //konvertoPiksel(qelizaRendesishme[HYRJE]);

        this.setLayout(null);

        if(!Main.loading) {
            thesari = new Thesari(konvertoPiksel(qelizaRendesishme[ME_LARG]).x, (konvertoPiksel(qelizaRendesishme[ME_LARG]).y)-35);
            lojtari = new Lojtari(konvertoPiksel(qelizaRendesishme[HYRJE]).x,konvertoPiksel(qelizaRendesishme[HYRJE]).y,this);

        }
        else {
            lojtari = new Lojtari(Menu.lojtariX, Menu.lojtariY,this);
           thesari= new Thesari(Menu.thesariX, Menu.thesariY);

        }
        this.add(lojtari);
        this.add(thesari);


        int pikaTargetX=0;
        int pikaTargetY=0;
        kufinjtMure.clear();
        for(int j = 0; j<matricaNdarjet.length; j++){
            pikaTargetX=0;
            //puthitja e rreshtave horizontal
            if(j%2==0 && j!=0) pikaTargetY+=MurVertikal.height;

            for(int i =0; i< matricaNdarjet[j].length; i++){
                if(j%2==0){
                    //true kur rruga eshte e hapur. Nuk duhet vizatuar
                    //false ka mur
                    if(!matricaNdarjet[j][i]){
                        MurHorizontal tempMurH = new  MurHorizontal(pikaTargetX,pikaTargetY);
                        this.add(tempMurH);
                        kufinjtMure.add(tempMurH.getBounds());
                        pikaTargetX+=MurHorizontal.width;

                    }else {
                        pikaTargetX += MurHorizontal.width;
                    }

                }
                else {
                    if (!matricaNdarjet[j][i]) {
                        MurVertikal tempMurV = new MurVertikal(pikaTargetX, pikaTargetY);
                        this.add(tempMurV);
                        kufinjtMure.add(tempMurV.getBounds());
                        pikaTargetX += MurHorizontal.width;
                    } else {
                        pikaTargetX += MurHorizontal.width;
                    }
                }
            }
        }

        ImageIcon originalIcon = new ImageIcon("Asete/BG.png");
        Image originalImage = originalIcon.getImage();
        Image resizedImage = originalImage.getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
        sfondi = new ImageIcon(resizedImage);
            JLabel label = new JLabel();
            label.setIcon(sfondi);
            label.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.add(label);
        this.setVisible(true);
    }

    void gjeneroLabirintin(int nrKutiX, int nrKutiY){

        int nrVizituar = 0;
        int nrQelizaTotal = nrKutiX*nrKutiY;
        Random rastesi = new Random();
        this.matricaLabirintit = new KutiaQelize[nrKutiY][nrKutiX];
        this.zeroLabirintin();

        Point pikaFillimit = new Point(0, rastesi.nextInt(0,nrKutiY));
        Point pikaFundit = new Point(nrKutiX-1, rastesi.nextInt(0, nrKutiY));

        Point pikaLarget = new Point();
        int madhesiaMaxStack = 0;
        this.bejPikenVisited(pikaFillimit);
        while(!stackLabirinti.empty()){
            KutiaQelize tempQelize = stackLabirinti.peek();
            KutiaQelize[]  fqinjUnvisited = checkFqinjt(tempQelize);
            if(fqinjUnvisited.length!=0){
                KutiaQelize nextQelize = fqinjUnvisited[rastesi.nextInt(0,4)%fqinjUnvisited.length];
                tempQelize.kaloNextQelize(nextQelize);
                stackLabirinti.add(nextQelize);
                if(madhesiaMaxStack<stackLabirinti.size()){
                    madhesiaMaxStack = stackLabirinti.size();
                    pikaLarget = nextQelize.getKoordinatat();
                }
            }else{
                stackLabirinti.pop();
            }
        }
        matricaLabirintit[pikaFundit.y][pikaFundit.x].setDjathtas(true);
        ndertoMatricenNdarje();
        printoLabirintinConsole();
        this.qelizaRendesishme[HYRJE] = pikaFillimit;
        this.qelizaRendesishme[DALJE] = pikaFundit;
        this.qelizaRendesishme[ME_LARG] = pikaLarget;
    }
    KutiaQelize[] checkFqinjt(KutiaQelize tempQelize){
        ArrayList<KutiaQelize> temp = new ArrayList<>();
        int i =0;
        if(!isSkaj(tempQelize, 'U')){
            if(!matricaLabirintit[tempQelize.getKoordinatat().y-1][tempQelize.getKoordinatat().x].getIsVendosur()) {
                temp.add(matricaLabirintit[tempQelize.getKoordinatat().y-1][tempQelize.getKoordinatat().x]);
            }
        }
        if(!isSkaj(tempQelize, 'R')){
            if(!matricaLabirintit[tempQelize.getKoordinatat().y][tempQelize.getKoordinatat().x+1].getIsVendosur()) {
                temp.add(matricaLabirintit[tempQelize.getKoordinatat().y][tempQelize.getKoordinatat().x+1]);
            }
        }
        if(!isSkaj(tempQelize, 'D')){
            if(!matricaLabirintit[tempQelize.getKoordinatat().y+1][tempQelize.getKoordinatat().x].getIsVendosur()) {
                temp.add(matricaLabirintit[tempQelize.getKoordinatat().y+1][tempQelize.getKoordinatat().x]);
            }
        }
        if(!isSkaj(tempQelize, 'L')){
            if(!matricaLabirintit[tempQelize.getKoordinatat().y][tempQelize.getKoordinatat().x-1].getIsVendosur()) {
                temp.add(matricaLabirintit[tempQelize.getKoordinatat().y][tempQelize.getKoordinatat().x-1]);
            }
        }
        KutiaQelize[] vektoriFqinjve = new KutiaQelize[temp.size()];
        for( KutiaQelize kuti : temp){
            vektoriFqinjve[i++] = kuti;
        }
        return vektoriFqinjve;
    }
    Boolean isSkaj(KutiaQelize kutiaQelize, char direksioni){
        switch (direksioni){
            case 'L':
                if (kutiaQelize.getKoordinatat().x == 0) return true;
                break;
            case 'R':
                if (kutiaQelize.getKoordinatat().x == matricaLabirintit[kutiaQelize.getKoordinatat().y].length-1) return true;
                break;
            case 'U':
                if(kutiaQelize.getKoordinatat().y == 0) return true;
                break;
            case 'D':
                if(kutiaQelize.getKoordinatat().y == matricaLabirintit.length-1) return true;
                break;
        }
        return false;
    }
    void zeroLabirintin(){
        for(int i =0; i<this.matricaLabirintit.length; i++){
            for(int j = 0; j<this.matricaLabirintit[i].length; j++){
                matricaLabirintit[i][j] = new KutiaQelize(j, i);
            }
        }
    }
    void bejPikenVisited(Point pika){
        matricaLabirintit[pika.y][pika.x].setIsVendosur(true);
        matricaLabirintit[pika.y][pika.x].setMajtas(true);
        stackLabirinti.push(matricaLabirintit[pika.y][pika.x]);
    }

    static void ndertoMatricenNdarje(int y, int x){
        matricaNdarjet = new boolean[y][x];
    }
    void ndertoMatricenNdarje () {
        int i, j, k=0;
        matricaNdarjet = new boolean[matricaLabirintit.length*2+1][matricaLabirintit[0].length+1];
        for (j = 0; j < matricaLabirintit.length; j++) {
            for (i = 0; i < matricaLabirintit[j].length; i++) {
                k=2*j;
                matricaNdarjet[k][i] = matricaLabirintit[j][i].isLart();
            }
            matricaNdarjet[k][i] = true;
            k++;
            for (i = 0; i < matricaLabirintit[j].length; i++) {
                matricaNdarjet[k][i] = matricaLabirintit[j][i].isMajtas();
            }
            matricaNdarjet[k][i] = matricaLabirintit[j][i-1].isDjathtas();
        }
        int l;
        for(l = 0; l< matricaLabirintit[0].length; l++){
            matricaNdarjet[k+1][l] = matricaLabirintit[j-1][l].isPosht();
        }
        matricaNdarjet[k+1][l] = true;
    }
    void printoLabirintinConsole(){
        for(int j =0; j<matricaLabirintit.length*2+1; j++){
            if(j%2 == 0) System.out.print(" ");
            for(int i =0; i<matricaLabirintit[0].length+1; i++){
                if(j%2==0){
                    System.out.print(matricaNdarjet[j][i]?  "  " : "--");
                }else{
                    System.out.print(matricaNdarjet[j][i]? "  ":"| " );
                }
            }
            System.out.println();
        }
    }
    static Point konvertoPiksel(Point qelizaKordinat){
        return new Point(MurHorizontal.width * qelizaKordinat.x + 3*MurVertikal.width, MurVertikal.height*qelizaKordinat.y + MurHorizontal.height/2);
    }

    public static Point getQelizaRendesishme(int indeksi) {
        return konvertoPiksel(qelizaRendesishme[indeksi]);
    }

    public Thesari getThesari() {
        return thesari;
    }
}
