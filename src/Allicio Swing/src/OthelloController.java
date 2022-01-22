import javax.swing.*;
import java.awt.Component;
import java.awt.event.MouseEvent;

/**
 * @author Allicio Mikael
 */
public final class OthelloController {
    
    private final OthelloModel theModel;
    private final OthelloView theView;

    public OthelloController(OthelloModel theModel, OthelloView theView) {
        this.theModel = theModel;
        this.theView = theView;

        // configure passer Tour bouton
        this.theView.passerTourListener(new PasserTour());
        // configure restart bouton
        this.theView.restartListener(new RestartClick());
        // configure envent souris
        this.theView.eventSourisListener(new EventSouris(), null);
        // constructoin plateau
        constructionPlateau(theModel.getLe_Symbole());
        // intial value for score and tour
        theView.setTour(theModel.incTour());
        theView.setEtat(theModel.getTour());
        updateScore();
    }
    
    public void constructionPlateau(String[][] le_Symbole){
        Component[] maxCase = theView.getPlateau().getComponents();
        EventSouris event = new EventSouris();
        for(int i=0;i<8;i++){
            for(int j=0;j<8;j++){

                MyJLabel maCase = new MyJLabel("", i, j);
                theView.eventSourisListener(event, maCase);
                theView.getPlateau().add(maCase);
                le_Symbole[i][j] = new String("VIDE");
                
                if (le_Symbole[i][j] == le_Symbole[3][3] || le_Symbole[i][j] == le_Symbole[4][4]){
                    maCase.setEtat("WHITE");
                    maCase.setEnabled(false);
                    le_Symbole[i][j] = "WHITE";
                }
                if (le_Symbole[i][j] == le_Symbole[3][4] || le_Symbole[i][j] == le_Symbole[4][3]){
                    maCase.setEtat("BLACK");
                    maCase.setEnabled(false);
                    le_Symbole[i][j] = "BLACK";
                }

            }
        }
        
        theView.setVisible(true);
    }
    public void updateScore(){
        theView.setScoreBlack(theModel.getScoreBlack());
        theView.setScoreWhite(theModel.getScoreWhite());
        theView.setTour(theModel.getTour());
    }

    class PasserTour implements java.awt.event.ActionListener{
        public void actionPerformed(java.awt.event.ActionEvent ev) {
            theView.setTour(theModel.incTour());
            updateScore();
            theView.setEtat2(theModel.getTour());
            theView.setAlerte("");
            theView.setTour(theModel.incTourMax());
            theView.setTour(theModel.getTour());
        }
    }

    // make button action
    class RestartClick implements java.awt.event.ActionListener{
        public void actionPerformed(java.awt.event.ActionEvent ev){
            Component[] liste = theView.getPlateau().getComponents();
            for(int i=0;i<liste.length;i++){
                MyJLabel bouton = (MyJLabel) liste[i];
                bouton.setOpaque(true);
//                bouton.setBackground(new Color(39, 97, 45));
                ((MyJLabel) bouton).setEtat("VIDE");
                bouton.setEnabled(true);

                if (liste[i] == liste[27] || liste[i] == liste[36]){
                    bouton.setEtat("WHITE");
                    bouton.setEnabled(false);
                }
                if (liste[i] == liste[28] || liste[i] == liste[35]){
                    bouton.setEtat("BLACK");
                    bouton.setEnabled(false);
                }
            }

            for(int i=0;i<8;i++){
                for(int j=0;j<8;j++){

                    theModel.getLe_Symbole()[i][j] = new String("VIDE");
                    if (theModel.getLe_Symbole()[i][j] == theModel.getLe_Symbole()[3][3] || 
                            theModel.getLe_Symbole()[i][j] == theModel.getLe_Symbole()[4][4]){
                        theModel.getLe_Symbole()[i][j] = "WHITE";
                    }
                    if (theModel.getLe_Symbole()[i][j] == theModel.getLe_Symbole()[3][4] || 
                            theModel.getLe_Symbole()[i][j] == theModel.getLe_Symbole()[4][3]){
                        theModel.getLe_Symbole()[i][j] = "BLACK";
                    }
                }
            }
            theModel.setScoreBlack(2);
            theModel.setScoreWhite(2);
            updateScore();
            theModel.iniTour();
            theModel.iniTourMax();
            theView.setEtat(theModel.getTour());
            theView.setAlerte("");
            theView.setTour(theModel.getTour());
        }
    }
    class EventSouris implements java.awt.event.MouseListener{
        MyJLabel maCase, maCaseInter;
        int ligneXX, ligneYY;
        
        @Override
        public void mouseClicked(MouseEvent e){
            MyJLabel laSource = (MyJLabel)e.getSource();
            if(!laSource.isEnabled()) return;
            int ligneX = laSource.getLigne();
            int ligneY = laSource.getColonne();
            int[][] laCase = laSource.getCase(ligneX, ligneY);
            
            // fonction pour checks les cases autour :
            theModel.plateau = theView.getPlateau();
            String msg1 = theModel.DetecteVide(ligneX, ligneY, laSource);
            if(!msg1.isEmpty()){
                theView.setAlerte(msg1);
            }
            else {
                if (theModel.getTour() %2==1){
                    theModel.setVerifValide("invalide");
                    if (!theModel.getValide().equals(theModel.getVerifValide())){
                        String msg2 = theModel.DetectePionNoir(ligneX,ligneY,laSource);
                        theView.setAlerte(msg2);
                        if(msg2.isEmpty()) theView.setEtatPlayer(1);
                        if (theModel.getValide().equals(theModel.getVerifValide()))
                            System.out.println("Valide, c'est OKK");
                        else 
                            theModel.decTour();
                    }
                }
                else if (theModel.getTour() %2==0){
                    theModel.setVerifValide("invalide");
                    if (!theModel.getValide().equals(theModel.getVerifValide())){
                        String msg2 = theModel.DetectePionBlanc(ligneX,ligneY,laSource);
                        theView.setAlerte(msg2);
                        if(msg2.isEmpty()) theView.setEtatPlayer(0);
                        if (theModel.getValide().equals(theModel.getVerifValide()))
                            System.out.println("Valide, c'est OKK");
                        else 
                            theModel.decTour();
                    }
                }
                else {
                    theModel.decTour();
                    theView.setAlerte("Problème interne !");
                }
            }
            
            theView.setTour(theModel.incTour());
            
            updateScore();
            if (theModel.getTour() > theModel.getTourMax()){
                theView.setEtatFinJeu();

                if (theModel.getScoreBlack()>theModel.getScoreWhite()){
                    JOptionPane.showMessageDialog(theView.getPanel(),
                            "La VICTOIRE appartient au "+theView.getJoueur1(),
                            "Partie Terminer.",
                            JOptionPane.INFORMATION_MESSAGE);
                }
                else {
                    JOptionPane.showMessageDialog(theView.getPanel(),
                            "VICTOIRE de "+theView.getJoueur2(),
                            "Partie Terminer.",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
        }
        @Override
        public void mouseEntered(MouseEvent e){
            MyJLabel laSource = (MyJLabel)e.getSource();
            if(!laSource.isEnabled()) return;
            ligneXX = laSource.getLigne();
            ligneYY = laSource.getColonne();
            
            if (theModel.getTour() %2==1){ //noir
                theModel.tourColor(ligneXX, ligneYY, "WHITE", "BLACK", false);
            }else if(theView.getPlateau() != null){ //blanc
                theModel.tourColor(ligneXX, ligneYY, "BLACK", "WHITE", false);
            }
            
            if(theModel.isCaseSeg()){
                maCase = (MyJLabel)theView.getPlateau().getComponent(ligneXX*8 + ligneYY);
                maCase.setEtat("ORANGE");
                theModel.setLe_Symbole("ORANGE", ligneXX, ligneYY);
            }
        }
        @Override
        public void mouseExited(MouseEvent e){
            if(theModel.getLe_Symbole()[ligneXX][ligneYY].equals("ORANGE")){
                maCase.setEtat("VIDE");
                theModel.setLe_Symbole("VIDE", ligneXX, ligneYY);
                theModel.setCaseSeg(false);
            }
        }
        @Override
        public void mousePressed(MouseEvent e){
        }
        @Override
        public void mouseReleased(MouseEvent e){
        }
    }
}
