
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
    
    public void checkwin()
    {
        
    }
    
    // move is 1 to 9 based on board square
    public void updateBoard(int move)
    {
//        switch(move){
//            case 1: 
//                board[0][0] = setBoard(1);
//        }
        
        /*for (int row = 0; row < board.length; row++) {
            for (int col = 0; col < board[row].length; col++) {
                if (board[row][col] == TicTacToe.X) {
                    board[row][col].setText("X");
                } else if (board[row][col] == TicTacToe.O) {
                    boardButtons[row][col].setText("O");
                } else {
                    boardButtons[row][col].setText("");
                }
            }
        }*/
       
    }
}
