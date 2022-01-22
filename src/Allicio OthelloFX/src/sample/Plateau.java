package sample;

import javafx.scene.control.Alert;

public class Plateau {
    static final int EMPTY = -1;
    static final int BLACK = 0;
    static final int WHITE = 1;
    static final int boardSize = 8;
    int player;
    int opposing;
    int board[][];
    boolean can_reverse[][];
    int surrounding[][];
    int num_black = 0, num_white = 0;

    public Plateau() {
        board = new int[boardSize][boardSize];
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                board[i][j] = EMPTY;
            }
        }
        player = BLACK;
        opposing = WHITE;
        can_reverse = new boolean[3][3];
        surrounding = new int[3][3];
        NewGameBoard();
    }

    public void NewGameBoard() {
        board[3][3] = BLACK;
        board[3][4] = WHITE;
        board[4][3] = WHITE;
        board[4][4] = BLACK;

    }


    public String countBlack() {
        int count = 0;
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if(board[i][j] == BLACK) count++;
            }
        }
        num_black = count;
        return count + "";
    }

    public String countWhite() {
        int count = 0;
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if(board[i][j] == WHITE) count++;
            }
        }
        num_white = count;
        return count + "";
    }

    public void swapPlayer() {
        int temp = player;
        player = opposing;
        opposing = temp;
    }


    public boolean isValid(int x, int y) {
        if(x >= 0 && x < boardSize && y >= 0 && y < boardSize)
            return true;
        return false;
    }

    public boolean determineReverse(final int x, final int y) {
        boolean hasReverse = false;
        for(int i = x  - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if(!isValid(i,j)) can_reverse[i - (x - 1)][j - (y - 1)] = false;
                else if(i != x || j != y) {
                    can_reverse[i - (x - 1)][j - (y - 1)] = isReverseChain(x, y, i - x, j - y);
                    if(can_reverse[i - (x - 1)][j - (y - 1)]) hasReverse = true;
                }
            }
        }
        return hasReverse;
    }

    public boolean isReverseChain(final int x, final int y, final int dx, final int dy) {
        int tempX = x + dx;
        int tempY = y + dy;
        if(!isValid(tempX, tempY) || board[tempX][tempY] != opposing) {
            return false;
        }

        while(board[tempX][tempY] == opposing) {
            if(!isValid(tempX + dx, tempY + dy))
                return false;
            tempX += dx;
            tempY += dy;
        }
        return board[tempX][tempY] == player;
    }

    public void reverseChain(int x, int y, int dx, int dy) {
        if(!isValid(x,y)) return;
        if(!isValid(x+dx,y+dy)) return;
        if(board[x+dx][y+dy] != opposing) return;
        board[x + dx][y + dy] = player;
        reverseChain(x + dx, y + dy, dx, dy);
    }

    public void reverse(int x, int y) {
        board[x][y] = player;
        for(int i = x-1; i <= x+1; i++) {
            for(int j = y-1; j <= y+1; j++) {
                if(isValid(i,j) && (i != x || j != y) && can_reverse[i-(x-1)][j-(y-1)])
                    reverseChain(x,y,i-x,j-y);
             }
        }
    }


    public void isGameEnd() {
        countBlack();
        countWhite();
        if(num_black + num_white == 64 || num_black == 0 ||
                num_white == 0) {
            showWinner();
        } else if(!canMove()) {
            swapPlayer();
            showTurnAgain();
            if(!canMove())
                showWinner();
        }
    }

    public void showTurnAgain() {
        String turn = (player == BLACK) ? "Joueur 1" : "[Joueur 2]";
        String opposeTurn = (player == BLACK) ? "Joueur 2" : "[Joueur 1]";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Il n'y a pas de place pour le retourner");
        alert.setHeaderText(null);
        alert.setContentText(opposeTurn + "Il n'y a pas de position réversible, donc à nouveau" + turn);
        alert.showAndWait();
    }

    public void showWinner() {
        String winner = (num_black > num_white) ? "Joueur 1" : "Joueur 2";
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bravo !");
        alert.setHeaderText(null);
        alert.setContentText(winner + " gagne !");
        alert.showAndWait();
    }

    public void determineSurrounding(final int x, final int y) {
        for(int i = x - 1; i <= x + 1; i++) {
            for(int j = y - 1; j <= y + 1; j++) {
                if(isValid(i, j))
                    surrounding[i - (x-1)][j - (y-1)] = board[i][j];
            }
        }
    }

    public boolean adjacentOpposing() {
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if((i != 1 || j != 1) && surrounding[i][j] != EMPTY && surrounding[i][j] != player)
                    return true;
            }
        }
        return false;
    }

    public boolean canMove() {
        for(int i = 0; i < boardSize; i++) {
            for(int j = 0; j < boardSize; j++) {
                if(board[i][j] == EMPTY) {
                    determineSurrounding(i,j);
                    if(adjacentOpposing() && determineReverse(i,j))
                        return true;
                }
            }
        }
        return false;
    }
}
