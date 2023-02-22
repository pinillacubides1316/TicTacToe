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
        setVisible(true);
        
        TicTacToe ttt = new TicTacToe();
        int activePlayer = ttt.getActivePlayer();
        
        
        
        // ======= button 1 ======= 
        buttons[0][0].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            int activePlayer = ttt.getActivePlayer();
            
            if(activePlayer == 1)
            {
                buttons[0][0].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[0][0].setText("O");
            }  
            
            // put this for each button
            int move = 1;
            ttt.updateBoard(1);
        }

        });
        
        // ======= button 2 =======
        buttons[0][1].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
           if(activePlayer == 1)
            {
                buttons[0][1].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[0][1].setText("O");
            }
        }

        });
        
        // ======= button 3 =======
        buttons[0][2].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[0][2].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[0][2].setText("O");
            }
        }

        });
        
        // ======= button 4 =======
        buttons[1][0].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[1][0].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[1][0].setText("O");
            }
        }

        });
        
        // ======= button 5 =======
        buttons[1][1].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[1][1].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[1][1].setText("O");
            }
        }

        });
        
        // ======= button 6 =======
        buttons[1][2].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[1][2].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[1][2].setText("O");
            }
        }

        });
        
        buttons[2][0].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[2][0].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[2][0].setText("O");
            }
        }

        });
        
        // ======= button 8 =======
        buttons[2][1].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[2][1].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[2][1].setText("O");
            }
        }

        });
        
        // ======= button 9 =======
        buttons[2][2].addActionListener(new ActionListener (){

        public void actionPerformed (ActionEvent e)
        {
            if(activePlayer == 1)
            {
                buttons[2][2].setText("X");
            }
            else if(activePlayer == 2)
            {
                buttons[2][2].setText("O");
            }
        }

        }); 

    }

    public static void main(String[] args) {
        new TTTBoardGUI();    
    }
}
