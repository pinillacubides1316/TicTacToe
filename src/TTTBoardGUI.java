/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**
 *
 * @author adria
 */
public class TTTBoardGUI extends JFrame{
    JButton[][] buttons = new JButton[3][3];
    JPanel panel = new JPanel(new GridLayout(3, 3));
    
    // instance of TicTacToe
    private TicTacToe game;

    // save the TicTacToe object to the TicTacToeConsole
    public void saveGame(TicTacToe game) {
        this.game = game;
    }
    
    
    public TTTBoardGUI() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        // Initialize the buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("-");
                panel.add(buttons[row][col]);
            }
        }
            
        add(panel);
        
        //setVisible(true);

        // ======= button 1 ======= 
        buttons[0][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // if the active player is 1 place an X 
                if(activePlayer == 1)
                {
                    buttons[0][0].setText("X");
                }
                // otherwise an “O”
                else
                {
                    buttons[0][0].setText("O");
                }  

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 1;
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();
                
                // send the TicTacToe object to the server
                
            }
        });
        
        // ======= button 2 =======
        buttons[0][1].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                 {
                     buttons[0][1].setText("X");
                 }
                 else if(activePlayer == 2)
                 {
                     buttons[0][1].setText("O");
                 }

                // update the Board with an "X" for player 1 or "O" for player 2
                 int move = 2;
                 game.updateBoard(move);
                 
                 // send the TicTacToe object to the server
                 
            }
        });
        
        // ======= button 3 =======
        buttons[0][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[0][2].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[0][2].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 3;
                game.updateBoard(move);
            }
        });
        
        // ======= button 4 =======
        buttons[1][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[1][0].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[1][0].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 4;
                game.updateBoard(move);
            }
        });
        
        // ======= button 5 =======
        buttons[1][1].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[1][1].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[1][1].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 5;
                game.updateBoard(move);
            }
        });
        
        // ======= button 6 =======
        buttons[1][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[1][2].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[1][2].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 6;
                game.updateBoard(move);
            }
        });
        
        buttons[2][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[2][0].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[2][0].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 7;
                game.updateBoard(move);
            }
        });
        
        // ======= button 8 =======
        buttons[2][1].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[2][1].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[2][1].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 8;
                game.updateBoard(move);
            }
        });
        
        // ======= button 9 =======
        buttons[2][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                if(activePlayer == 1)
                {
                    buttons[2][2].setText("X");
                }
                else if(activePlayer == 2)
                {
                    buttons[2][2].setText("O");
                }

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 9;
                game.updateBoard(move);
            }
        }); 
    }
    
    
    /*
    public static void main(String[] args) {
        new TTTBoardGUI();    
    }*/
}
