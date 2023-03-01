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
    // instance  - class properties
    ChatClient client;
    
    JButton[][] buttons = new JButton[3][3];
    JPanel panel = new JPanel(new GridLayout(3, 3));
    
    // instance of TicTacToe
    private TicTacToe game;
    
    // save the TicTacToe object to the TicTacToeConsole
    public void saveGame(TicTacToe game) {
        this.game = game;
    }
    
    public TicTacToe getGame() {
        return game;
    }
    
    // save the client object to be able to access to it
    public void saveClient(ChatClient client) {
        this.client = client;
    }
    
    
    public TTTBoardGUI() {
        super("Tic-Tac-Toe");
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


        // ======= button 1 ======= 
        buttons[0][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 1;
                
                // set the text in the button according to the player
                updateBoardText(0,0);
                
                // update the board[][]
                game.updateBoard(move);

                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 2 =======
        buttons[0][1].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                 int move = 2;
                
                // set the text in the button according to the player
                updateBoardText(0,1);
                
                // update the board[][]
                game.updateBoard(move);
                 
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
                 
            }
        });
        
        // ======= button 3 =======
        buttons[0][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 3;
                
                // set the text in the button according to the player
                updateBoardText(0,2);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 4 =======
        buttons[1][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 4;
                
                // set the text in the button according to the player
                updateBoardText(1,0);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 5 =======
        buttons[1][1].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 5;
                
                // set the text in the button according to the player
                updateBoardText(1,1);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 6 =======
        buttons[1][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 6;
                
                // set the text in the button according to the player
                updateBoardText(1,2);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        buttons[2][0].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 7;
                
                // set the text in the button according to the player
                updateBoardText(2,0);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 8 =======
        buttons[2][1].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 8;
                
                // set the text in the button according to the player
                updateBoardText(2,1);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        });
        
        // ======= button 9 =======
        buttons[2][2].addActionListener(new ActionListener (){

            public void actionPerformed (ActionEvent e)
            {
                int activePlayer = game.getActivePlayer();

                // update the Board with an "X" for player 1 or "O" for player 2
                int move = 9;
                
                // set the text in the button according to the player
                updateBoardText(2,2);
                
                // update the board[][]
                game.updateBoard(move);
                
                // check if the move is a winning move
                game.checkwin();

                // send the TicTacToe object to the server
                if(client.isConnected())
                {
                    //client.sendToServer(env); // client is null *******
                    client.handleMessageFromClientUI("#tttPlaying");
                }
            }
        }); 
    }
    
    // set the text according to the player
    public void updateBoardText(int row, int col)
    {
        if(game.getActivePlayer()==1){
            buttons[row][col].setText("X");
        }else{
            buttons[row][col].setText("O");
        }
        
    }

}
