import java.awt.*;

public class KutiaQelize {
    private boolean lart, posht, majtas, djathtas;
    private boolean isVendosur;
    private Point koordinatat;
    KutiaQelize(int x, int y){
        lart = posht = majtas = djathtas = false;
        isVendosur = false;
        koordinatat = new Point(x, y);
    }
    public void setIsVendosur(boolean isVendosur){
        this.isVendosur = isVendosur;
    }
    public Point getKoordinatat(){
        return this.koordinatat;
    }
    public boolean getIsVendosur(){
        return this.isVendosur;
    }

    public void setLart(boolean lart) {
        this.lart = lart;
    }

    public void setPosht(boolean posht) {
        this.posht = posht;
    }

    public void setMajtas(boolean majtas) {
        this.majtas = majtas;
    }

    public void setDjathtas(boolean djathtas) {
        this.djathtas = djathtas;
    }

    public boolean isLart() {
        return lart;
    }

    public boolean isPosht() {
        return posht;
    }

    public boolean isMajtas() {
        return majtas;
    }

    public boolean isDjathtas() {
        return djathtas;
    }

    public void kaloNextQelize(KutiaQelize nextQelize){
        if(this.koordinatat.x != nextQelize.getKoordinatat().x){
            if(this.koordinatat.x < nextQelize.getKoordinatat().x){
                this.djathtas = true;
                nextQelize.setMajtas(true);
            }else{
                this.majtas = true;
                nextQelize.setDjathtas(true);
            }
        }else if(this.koordinatat.y != nextQelize.getKoordinatat().y){
            if(this.koordinatat.y < nextQelize.getKoordinatat().y){
                this.posht = true;
                nextQelize.setLart(true);
            }else{
                this.lart = true;
                nextQelize.setPosht(true);
            }
        }
        nextQelize.setIsVendosur(true);
    }

}
