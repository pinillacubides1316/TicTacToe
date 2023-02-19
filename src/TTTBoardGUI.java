/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.*;
/**
 *
 * @author adria
 */
public class TTTBoardGUI extends JFrame{
    private JButton[][] buttons = new JButton[3][3];
    private JPanel panel = new JPanel(new GridLayout(3, 3));

    public TTTBoardGUI() {
        super("Tic-Tac-Toe");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);

        // Initialize the buttons
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                buttons[row][col] = new JButton("");
                panel.add(buttons[row][col]);
            }
        }

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        new TTTBoardGUI();
    }
}
