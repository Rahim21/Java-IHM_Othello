
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
/**
 * @author Allicio Mikael
 */
public class OthelloView extends JFrame {
    
    private static final long serialVersionUID = 4573488679200829551L;
    
    private JPanel panel, plateau, menu;
    private JLabel titre, etat, nbrTour, scoreJoueur1, scoreJoueur2, alerte;
    private JTextField[] score = new JTextField[2];
    private JButton passerTour, restart, quitter;
    
    private String joueur1 = "Player 1", joueur2 = "Player 2";
    
    // constructor
    public OthelloView(){
        // title dimension .... for Frame
        windowInfo();
        // création des différentes éléments de la fenêtre
        intialization();
        // Construction du plateau 
        //constructionPlateau(le_Symbole);
    }
    
    private void windowInfo(){
        this.setTitle("Othello Swing - Allicio");
        this.setSize(new Dimension(900, 600));
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
    }
    private void intialization(){
        // ••••••••••••••• JPanel panel •••••••••••••••
        panel = new JPanel();
        panel.setBackground(Color.DARK_GRAY);
        panel.setLayout(new BorderLayout());

        // ••••••••••••••• JPanel plateau •••••••••••••••
        plateau = new JPanel();
        plateau.setLayout(new GridLayout(8,8));
        plateau.setBackground(new Color(39, 97, 45));

        // ••••••••••••••• JLabel titre •••••••••••••••
        titre = new JLabel("Bienvenue sur mon Othello !");
        titre.setPreferredSize(new Dimension(600, 50));
        titre.setForeground(Color.WHITE); // couleur du texte
        titre.setFont(titre.getFont().deriveFont(25.0f)); // taille du texte


        // ••••••••••••••• ImageIcon icon •••••••••••••••
        final ImageIcon icon = new ImageIcon("src/background.gif");
        Image img = icon.getImage();


        // ••••••••••••••• JPanel menu •••••••••••••••
        menu = new JPanel(){                                                             // initialisation JPanel menu
            Image img = icon.getImage();                                                 // ajout d'une image
            // initialiseur d'instance
            {setOpaque(false);}
            public void paintComponent(Graphics graphics)
            {
                graphics.drawImage(img, 0, 0, this);
                super.paintComponent(graphics);
            }
        };
        menu.setPreferredSize(new Dimension(300, 600));                     // taille du JPanel menu
        menu.setLayout(new GridBagLayout());
        GridBagConstraints monContenu = new GridBagConstraints();


        // ••••••••••••••• JLabel etat : Etat du jeu •••••••••••••••
        etat = new JLabel();
        etat.setFont(titre.getFont().deriveFont(20.0f));

        monContenu.gridy = 0;                                                           // emplacement 0 dans le menu GridBagLayout
        monContenu.insets = new Insets(30,0,0,0);

        menu.add(etat, monContenu);


        // ••••••••••••••• SCORE •••••••••••••••
        for(int i = 0; i < 2; i++){

            score[i] = new JTextField();
            score[i].setMinimumSize(new Dimension(40, 30));
            score[i].setPreferredSize(new Dimension(40, 30));
            score[i].setEditable(false);
            score[i].setForeground(Color.red);
            score[i].setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        }


        // ••••••••••••••• JLabel scoreJoueur1 : Score Black •••••••••••••••
        scoreJoueur1 = new JLabel(joueur1 + " : ");
        score[0].setBackground(Color.black);
        scoreJoueur1.setFont(scoreJoueur1.getFont().deriveFont(20.0f));

        monContenu.gridy = 1;                                                           // emplacement 1 dans le menu GridBagLayout
        monContenu.insets = new Insets(50,0,0,0);

        menu.add(scoreJoueur1, monContenu);
        menu.add(score[0], monContenu);


        // ••••••••••••••• JLabel scoreJoueur2 : Score White •••••••••••••••
        scoreJoueur2 = new JLabel(joueur2 + " : ");

        score[1].setBackground(Color.white);
        scoreJoueur2.setFont(titre.getFont().deriveFont(20.0f));

        monContenu.gridy = 2;                                                           // emplacement 2 dans le menu GridBagLayout
        monContenu.insets = new Insets(10,0,0,0);

        menu.add(scoreJoueur2, monContenu);
        menu.add(score[1], monContenu);


        // ••••••••••••••• JLabel alerte : message règles •••••••••••••••
        alerte = new JLabel();
        alerte.setFont(alerte.getFont().deriveFont(20.0f));
        alerte.setForeground(new Color(255, 0, 0));

        monContenu.gridy = 3;                                                           // emplacement 3 dans le menu GridBagLayout
        monContenu.insets = new Insets(30,0,0,0);

        menu.add(alerte, monContenu);

        // ••••••••••••••• JLabel nbrTour : nombre de tours •••••••••••••••
        nbrTour = new JLabel("Tour : 0");
        nbrTour.setFont(nbrTour.getFont().deriveFont(20.0f));

        monContenu.gridy = 4;                                                           // emplacement 4 dans le menu GridBagLayout
        monContenu.insets = new Insets(50,0,0,0);

        menu.add(nbrTour, monContenu);

        // ••••••••••••••• JButton passerTour : bouton pour passer son tour •••••••••••••••
        passerTour = new JButton("Passer Tour");                                          // initialisation Bouton "Rejouer"
        passerTour.setForeground(Color.WHITE);                                             // couleur du texte
        passerTour.setFont(passerTour.getFont().deriveFont(20.0f));                             // taille du texte
        passerTour.setPreferredSize(new Dimension(180, 50));                   // taille du bouton
        passerTour.setBackground(new Color(22, 169, 201));                        // couleur du bouton

        monContenu.gridy = 5;                                                           // emplacement 5 dans le menu GridBagLayout
        monContenu.insets = new Insets(10,0,0,0);                 // 250px au dessus

        menu.add(passerTour, monContenu);


        // ••••••••••••••• JButton restart : bouton Rejouer •••••••••••••••
        restart = new JButton("Rejouer");                                          // initialisation Bouton "Rejouer"
        restart.setForeground(Color.WHITE);                                             // couleur du texte
        restart.setFont(restart.getFont().deriveFont(20.0f));                             // taille du texte
        restart.setPreferredSize(new Dimension(180, 50));                   // taille du bouton
        restart.setBackground(new Color(22, 169, 201));                        // couleur du bouton

        monContenu.gridy = 6;                                                           // emplacement 6 dans le menu GridBagLayout
        monContenu.insets = new Insets(20,0,0,0);                 // 250px au dessus

        menu.add(restart, monContenu);                                                  // ajouter dans le menu


        // ••••••••••••••• JButton quitter : bouton Quitter •••••••••••••••
        quitter = new JButton("Quitter");                                           // initialisation Bouton "Quitter"
        quitter.setForeground(Color.WHITE);                                             // couleur du texte
        quitter.setFont(quitter.getFont().deriveFont(20.0f));                             // taille du texte
        quitter.setPreferredSize(new Dimension(180, 50));                   // taille du bouton
        quitter.setBackground(new Color(3, 108, 153));                         // couleur du bouton

        quitter.addActionListener(e -> {                                                // ActionListener pour fermer la fenêtre
            this.dispose();
        });

        monContenu.gridy = 7;                                                          // emplacement 7 dans le menu GridBagLayout
        monContenu.insets = new Insets(20,0,0,0);                 // 20px au dessus
        menu.add(quitter, monContenu);                                                 // ajouter dans le menu


        // ••••••••••••••• Placement des composants sur le Panel BorderLayout •••••••••••••••
        panel.add(titre,BorderLayout.NORTH);                                           // barre titre au dessus
        titre.setHorizontalAlignment(JLabel.CENTER);                                   // texte aligné horizontalement
        panel.add(plateau);                                                            // plateau au centre
        panel.add(menu,BorderLayout.WEST);
        
        
        this.add(panel);
        this.setVisible(true);
        getContentPane();
    }
    
    // set scores, tour, etat
    public void setScoreBlack(int scoreVal){
        score[0].setText("" + scoreVal);
    }
    public void setScoreWhite(int scoreVal){
        score[1].setText("" + scoreVal);
    }
    public void setTour(int nbr){
        nbrTour.setText("Tour : " + nbr);
    }
    public void setEtat(int tour){
        etat.setText((tour%2==1 ? joueur1 : joueur2)+" commence");
    }
    public void setEtat2(int tour){
        etat.setText("Tour : " + (tour % 2 == 1 ? joueur1 : joueur2));
    }
    public void setEtatPlayer(int nbr){
        if(nbr == 0)
            etat.setText(joueur1+" a votre tour");  // for black
        else
            etat.setText(joueur2+" a votre tour"); // for white
    }
    public void setEtatFinJeu(){
        etat.setText("FIN DU JEU");
        Component[] components = plateau.getComponents();
        for (Component component : components) {
            component.setEnabled(false);
        }
    }
    public void setAlerte(String msg){
        alerte.setText(msg);
    }
    
    // event listerner for buttons
    public void eventSourisListener(MouseListener l, MyJLabel maCase){
        if(maCase != null)
            maCase.addMouseListener(l);
    }
    public void passerTourListener(ActionListener l){
        passerTour.addActionListener(l);
    }
    public void restartListener(ActionListener l){
        restart.addActionListener(l);
    }
    
    // setters and getters
    public JPanel getPanel() {
        return panel;
    }
    public JPanel getPlateau() {
        return plateau;
    }
    public String getJoueur1() {
        return joueur1;
    }
    public String getJoueur2() {
        return joueur2;
    }
}
