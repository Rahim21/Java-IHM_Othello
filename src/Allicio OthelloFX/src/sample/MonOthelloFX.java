package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Control;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class MonOthelloFX extends Control {
    // connecter avec la vue et le contrôleur
    @FXML TextArea BlackNum;
    @FXML TextArea WhiteNum;
    @FXML Text GameState;
    @FXML AnchorPane gamePane;
    @FXML AnchorPane infoPane;
    Plateau plateau;
    int i,j;
    Circle[][] stones;
    boolean Started = false;

    public void gameStart() {
        if(!Started) {
            plateau = new Plateau();
            stones = new Circle[8][8];
            newTurn();
            applyBoard();
            Started = true;
        }
        else {
            DestroyBoard();
            Started = false;
            gameStart();
        }
    }

    public void applyBoard() {
        for(int i = 0; i < plateau.boardSize; i++) {
            for(int j = 0; j < plateau.boardSize; j++) {
                if(plateau.board[i][j] != plateau.EMPTY) {
                    if(plateau.board[i][j] == plateau.BLACK) {
                        gamePane.getChildren().remove(stones[i][j]);
                        stones[i][j] = new Circle(310 + 90 * j, 135 + 90 * i, 40);
                        stones[i][j].setFill(Color.BLACK);
                        gamePane.getChildren().add(stones[i][j]);
                    }
                    else if(plateau.board[i][j] == plateau.WHITE) {
                        gamePane.getChildren().remove(stones[i][j]);
                        stones[i][j] = new Circle(310 + 90 * j, 135 + 90 * i, 40);
                        stones[i][j].setFill(Color.WHITE);
                        gamePane.getChildren().add(stones[i][j]);
                    }
                }
            }
        }
    }

    public void setMouseAction() {
        gamePane.setOnMouseClicked(event -> {
            if(event.getSceneX() < 265 || event.getSceneX() > 985 ||
                    event.getSceneY() < 90 || event.getSceneY() > 810) {
                return;
            }
            for(i = 0; i < plateau.boardSize; i++) {
                if(event.getSceneX() <= 355 + 90*i)
                    break;
            }
            for(j = 0; j < plateau.boardSize; j++) {
                if(event.getSceneY() <= 180 + 90*j)
                    break;
            }

            if(plateau.board[j][i] != plateau.EMPTY)
                return;
            else {
                plateau.board[j][i] = plateau.player;

                if(!plateau.determineReverse(j,i)) {
                    plateau.board[j][i] = plateau.EMPTY;
                    return;
                }
            }
            plateau.reverse(j,i);
            applyBoard();
            plateau.swapPlayer();
            newTurn();
            plateau.isGameEnd();
            newTurn();
        });
    }

    public void newTurn() {
        if(plateau.player == plateau.BLACK) {
            GameState.setText("Tour : Joueur 1");
            BlackNum.setText(plateau.countBlack());
            WhiteNum.setText(plateau.countWhite());
        }

        else if(plateau.player == plateau.WHITE) {
            GameState.setText("Tour : Joueur 2");
            BlackNum.setText(plateau.countBlack());
            WhiteNum.setText(plateau.countWhite());
        }

    }


    public void DestroyBoard() {
        for(int x = 0; x < plateau.boardSize; x++) {
            for(int y = 0; y < plateau.boardSize; y++) {
                if(stones[x][y] != null)
                    gamePane.getChildren().remove(stones[x][y]);
            }
        }
        plateau.player = plateau.BLACK;
    }
}
