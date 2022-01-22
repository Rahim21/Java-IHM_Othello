
import javax.swing.JPanel;
/**
 * @author Allicio Mikael
 */
public class OthelloModel {
    
    private String[][] le_Symbole;
    private int scoreBlack;
    private int scoreWhite;
    private int tour;
    private int tourMax;
    
    private String valide = "valide";
    private String verifValide;
    private boolean caseSeg = false;
    
    public JPanel plateau;
    
    public OthelloModel() {
        this.tour = 0;
        this.tourMax = 60;
        le_Symbole = new String[8][8];
        scoreBlack = 2;
        scoreWhite = 2;
    }
    
    // methods
    public String DetecteVide(int ligneX, int ligneY, MyJLabel laSource){
        if(tourVide(ligneX, ligneY)) {
            laSource.setEtat("VIDE");
            le_Symbole[ligneX][ligneY] = "VIDE";
            tour = tour-1;
            return "Déplacement invalide !";
        }
        return "";
    }
    // see if all case arround are empty
    private boolean tourVide(int ligneX, int ligneY) {
        boolean res = true;
        for(int i=ligneX-1; i<=ligneX+1; i++){
            for(int j=ligneY-1; j<=ligneY+1; j++){
                if(i==ligneX && j==ligneY) // ignore center case
                    continue;
                boolean isOutBounds = i<0 || j<0 || i>7 || j>7; // check if we re not out of range of matrice
                if(!isOutBounds)
                   res = res && le_Symbole[i][j].equals("VIDE"); // all cases should be vide to return true
            }
        }
        return res;
    }    
    // check if can play in this case
    public boolean tourColor(int ligneX, int ligneY, String colorF, String colorS, boolean change_color) {
        boolean found = false;
        for(int i=ligneX-1; i<=ligneX+1; i++){
            for(int j=ligneY-1; j<=ligneY+1; j++){
                if(i==ligneX && j==ligneY)
                    continue;
                boolean isOutBonds = i>7 || i<0 || j>7 || j<0;
                if(!isOutBonds)
                    if(le_Symbole[i][j].equals(colorF)){
                        boolean t = checkAhead(ligneX, ligneY, i, j, colorS, change_color);
                        found = found || t;
                    }
            }
        }
        score();
        return found;
    }    
    // check if there is same color in the row, column, diagonale ahead
    private boolean checkAhead(int actuelI, int actuelJ, int i, int j, String color, boolean change_color){
        int x = i - actuelI , y = j - actuelJ;
        while(true){
            if(i>7 || i<0 || j>7 || j<0)
                break;
            if(le_Symbole[i][j].equals("VIDE"))
                return false;
            if(le_Symbole[i][j].equals(color)){
                caseSeg = true;
                if(change_color){
                    caseSeg = false;
                    changeColor(actuelI, actuelJ, i, j, x, y, color);
                }
                return true;
            }
            i += x;
            j += y;
        }
        return false;
    }
    // change color 
    private void changeColor(int startI, int startJ, int endI, int endJ, int moveI, int moveJ, String color){
        while(startI != endI || startJ != endJ){
            MyJLabel maCase = (MyJLabel)plateau.getComponent(endI*8 + endJ);
            maCase.setEtat(color);
            maCase.setEnabled(false);
            le_Symbole[endI][endJ] = color;
            endI -= moveI;
            endJ -= moveJ;
        }
        // these to change color for the case choise
        MyJLabel maCase = (MyJLabel)plateau.getComponent(endI*8 + endJ);
        maCase.setEtat(color);
        maCase.setEnabled(false);
        le_Symbole[endI][endJ] = color;
    }
    // calculate score
    private void score(){
        scoreBlack = 0;
        scoreWhite = 0;
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){
                if(le_Symbole[i][j].equals("WHITE"))
                    scoreWhite++;
                else if(le_Symbole[i][j].equals("BLACK"))
                    scoreBlack++;
            }
        }
    }
    
    public String DetectePionNoir(int ligneX, int ligneY, MyJLabel laSource){
        if(tourColor(ligneX, ligneY, "WHITE", "BLACK", true)){
            laSource.setEtat("BLACK");
            laSource.setEnabled(false);
            verifValide = "valide";
            return "";
        }
        else {
            verifValide = "invalide";
            return "Position inconrrecte";
        }
    }
    public String DetectePionBlanc(int ligneX, int ligneY, MyJLabel laSource){
        if(tourColor(ligneX, ligneY, "BLACK", "WHITE", true)){
            laSource.setEtat("WHITE");
            laSource.setEnabled(false);
            verifValide = "valide";
             return "";
        }
        else {
            verifValide = "invalide";
            return "Position inconrrecte";
        }
    }
    
    // setters
    public void setLe_Symbole(String val, int i, int j){
        le_Symbole[i][j] = val;
    }
    public void iniTour(){
        tour = 1;
    }
    public void iniTourMax(){
        tourMax = 60;
    }
    public int incTour(){
        tour++;
        return tour;
    }
    public int incTourMax(){
        tourMax++;
        return tourMax;
    }
    public int decTour(){
        tour--;
        return tour;
    }
    public void setValide(String valide) {
        this.valide = valide;
    }
    public void setVerifValide(String verifValide) {
        this.verifValide = verifValide;
    }
    public void setCaseSeg(boolean caseSeg) {
        this.caseSeg = caseSeg;
    }
    public void setScoreBlack(int scoreBlack) {
        this.scoreBlack = scoreBlack;
    }
    public void setScoreWhite(int scoreWhite) {
        this.scoreWhite = scoreWhite;
    }
    
    
    // getters
    public String[][] getLe_Symbole() {
        return le_Symbole;
    }
    public int getScoreBlack() {
        return scoreBlack;
    }
    public int getScoreWhite() {
        return scoreWhite;
    }
    public int getTour() {
        return tour;
    }
    public int getTourMax() {
        return tourMax;
    }
    public String getValide() {
        return valide;
    }
    public String getVerifValide() {
        return verifValide;
    }
    public boolean isCaseSeg() {
        return caseSeg;
    }
    
}
