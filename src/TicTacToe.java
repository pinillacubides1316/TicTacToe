
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serializable;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author adria
 */
public class TicTacToe implements Serializable{
    
    // declare variables
    String player1;
    String player2;
    int activePlayer;
    int gameState;
    char[][] board;

    // constructor

    public TicTacToe() {
    }

    public TicTacToe(int gameState) {
        this.gameState = gameState;
    }
    

    public TicTacToe(String player1, String player2, int activePlayer, int gameState, char[][] board) {
        this.player1 = player1;
        this.player2 = player2;
        this.activePlayer = activePlayer;
        this.gameState = gameState;
        this.board = board;
    }
    
    // getters and setters
    public String getPlayer1() {
        return player1;
    }

    public void setPlayer1(String player1) {
        this.player1 = player1;
    }

    public String getPlayer2() {
        return player2;
    }

    public void setPlayer2(String player2) {
        this.player2 = player2;
    }

    public int getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }

    public int getGameState() {
        return gameState;
    }

    public void setGameState(int gameState) {
        this.gameState = gameState;
    }

    public char[][] getBoard() {
        return board;
    }

    public void setBoard(char[][] board) {
        this.board = board;
    }
    
    // check if the move is a winning move 
    public void checkwin()
    {
        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2]) {
                
                //change the gamestate to 4 (won)
                setGameState(4);
            }
        }

        // Check columns
        for (int j = 0; j < 3; j++) {
            if (board[0][j] == board[1][j] && board[1][j] == board[2][j]) {
                
                //change the gamestate to 4 (won)
                setGameState(4);
            }
        }

        // Check diagonals
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2]) {
            
            //change the gamestate to 4 (won)
            setGameState(4);
        }
        if (board[0][2] == board[1][1] && board[1][1] == board[2][0]) {
            
            //change the gamestate to 4 (won)
            setGameState(4);
        }
    }
    
    // move is 1 to 9 based on board square
    public void updateBoard(int move)
    {
        int row = 0;
        int col = 0;
        switch(move){
            case 1: 
                row = 0;
                col = 0;
            break;
            case 2: 
                row = 0;
                col = 1;
            break;
            case 3: 
                row = 0;
                col = 2;
            break;
            case 4: 
                row = 1;
                col = 0;
            break;
            case 5: 
                row = 1;
                col = 1;
            break;
            case 6: 
                row = 1;
                col = 2;
            break;
            case 7: 
                row = 2;
                col = 0;
            break;
            case 8: 
                row = 2;
                col = 1;
            break;
            case 9: 
                row = 2;
                col = 2;
            break;
        }
        
        // if the active player is 1 place an X
        if (getActivePlayer()==1) {
            board[row][col] = 'X';
        } 
        // otherwise an “O”
        else {
            board[row][col] = 'O';
        }
        
    }
}
