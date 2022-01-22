import javax.swing.JLabel;
import javax.swing.BorderFactory;
import java.awt.Color;
import java.awt.*;
/**
 * @author Allicio Mikael
 */
public class MyJLabel extends JLabel{
    private int largeur;
    private int hauteur;

    public enum Etat{
        VIDE, BLACK, WHITE, ORANGE
    };

    Etat status;

    public MyJLabel(String text, int largeur, int hauteur){
        super(text);
        this.largeur = largeur;
        this.hauteur = hauteur;
        setHorizontalAlignment(JLabel.CENTER);
        setBorder(BorderFactory.createLineBorder(Color.BLACK));
        setBackground(new Color(39, 97, 45));
    }

    @Override
    public void paint(java.awt.Graphics g){

        super.paint(g);
        int largeur = getWidth();
        int hauteur = getHeight();
        
        Graphics2D g2 = (Graphics2D) g;
        if(status==Etat.BLACK){
            g.setColor(Color.BLACK);
            g.fillOval(10,10,largeur-20,hauteur-20);
        }

        if(status==Etat.WHITE){
            g.setColor(Color.WHITE);
            g.fillOval(10,10,largeur-20,hauteur-20);
        }
        
        if(status==Etat.ORANGE){
            g.setColor(Color.ORANGE);
            g.fillOval(10,10,largeur-20,hauteur-20);
        }
        
        if(status==Etat.VIDE){
//            g.setColor(new Color(39, 97, 45
//            g.fillOval(10,10,largeur-20,hauteur-20);
        }
        
    }

    public void setEtat(String Dessin){
        if (Dessin=="ORANGE") {
            status = Etat.ORANGE;
        }
        if (Dessin=="BLACK") {
            status = Etat.BLACK;
        }
        else{
            if (Dessin=="WHITE") {
                status = Etat.WHITE;
            }
            else{
                if (Dessin=="VIDE") {
                    status = Etat.VIDE;
                }
            }
        }
        System.out.println(status);
        repaint();
    }

    public void getEtat(){
        System.out.println("ETAT : "+this.status);
    }

    public void setLigne(int ligneX){this.largeur =ligneX;}
    public void setColonne(int ligneY){this.hauteur =ligneY;}
    public int getLigne(){return this.largeur;}
    public int getColonne(){return this.hauteur;}
    public int[][] getCase(int ligneX, int ligneY){
        ligneX = this.largeur;
        ligneY = this.hauteur;
        int[][] tableau = new int[ligneX][ligneY];
        return tableau;
    }
}