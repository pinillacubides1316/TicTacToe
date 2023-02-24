/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;


/**
 *
 * @author adria
 */
public class GUIConsole extends JFrame implements ChatIF{
    
    final public static String host = "localhost";
    // define the player 1
    String player1;
    /**
     * The default port to listen on.
     */
    //Final is a variable that once you declare it, it will never can be changed
    final public static int DEFAULT_PORT = 5555;
    
    // instance  - class properties
    ChatClient client;
    EchoServer server;

    
    // buttons
    private JButton closeB = new JButton("Close");
    private JButton openB = new JButton("Open Connection");
    private JButton sendB = new JButton("Send");
    private JButton quitB = new JButton("Quit");
    private JButton loginB = new JButton("Login");
    private JButton tictactoeB = new JButton("Tic Tac Toe");

    // textfields
    private JTextField portTxF = new JTextField("5555");
    private JTextField hostTxF = new JTextField("127.0.0.1");
    private JTextField messageTxF = new JTextField("");
    private JTextField loginTxF = new JTextField("");

    // labels
    private JLabel emptyLB = new JLabel("", JLabel.RIGHT);
    private JLabel portLB = new JLabel("Port: ", JLabel.RIGHT);
    private JLabel hostLB = new JLabel("Host: ", JLabel.RIGHT);
    private JLabel messageLB = new JLabel("Message: ", JLabel.RIGHT);
    private JLabel loginLB = new JLabel("Login: ", JLabel.RIGHT);
    private JLabel userListLB = new JLabel("User List: ", JLabel.RIGHT);
    

    // main chat area
    private JTextArea messageList = new JTextArea();
    
    private JComboBox<String> usersListCB = new JComboBox<>();
    ArrayList<String> usersList;

    // constructor
    public GUIConsole ( String host, int port) //call the class we are extending - Jframe for this class
    {
        // set window properties
        super("Simple Chat GUI");
        setSize(300, 500);
        

        // creating a layout if the main window
        setLayout( new BorderLayout(5,5));
        JPanel bottom = new JPanel();
        add( "Center", messageList );
        messageList.append(">> Open the connection");
        add( "South" , bottom);

        // layout of the bottom jframe
        bottom.setLayout( new GridLayout(9,2,5,5));
        bottom.add(hostLB); 		
        bottom.add(hostTxF);
        
        bottom.add(portLB); 		
        bottom.add(portTxF);
        
        bottom.add(loginLB);          
        bottom.add(loginTxF);
        
        bottom.add(emptyLB);
        bottom.add(loginB);   

        bottom.add(messageLB);          
        bottom.add(messageTxF);
        
        bottom.add(userListLB);
        bottom.add(usersListCB);
                
        bottom.add(openB); 
        bottom.add(sendB);
        
        bottom.add(tictactoeB);
        bottom.add(closeB); 		
        bottom.add(quitB);
        
        
        // event Handler to open the connection
        openB.addActionListener(new ActionListener()
        {
            public void actionPerformed (ActionEvent e)
            {
                // open the client connection
                openConnection();    
                messageList.append("\n>> Connection opened!\n");
            }
        });
        
        // event Handler to quit
        quitB.addActionListener(new ActionListener ()
        {
            public void actionPerformed (ActionEvent e)
            {
                // if the client is not connected, quit. Else handle the command
                if (client==null)
                {
                    System.exit(0);
                    
                }else{
                    client.handleClientCommand("#quit");
                }
                
            }
        });

        // event Handler to send messages
        sendB.addActionListener( new ActionListener() 
        {
            public void actionPerformed(ActionEvent e)
            {
                send(messageTxF.getText());
                //display(messageTxF.getText()+"\n" );
            }
        });
        
        // event Handler to login
        loginB.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                // build a login command and send it to the server
                client.handleMessageFromClientUI("#login "+loginTxF.getText());
                
                player1 = loginTxF.getText();
                //playersListCB.addItem(player1);
                
                if(client != null){
                    // loop to update the Jombobox (Users Connected)
                    client.handleMessageFromClientUI("#usersConnected");
                }
                
                // display a message for the user when is connected
                send(" is connected!");
            }

        });
        
        // event handler - create an instance of the Tic Tac Toe object
        // and display the TicTacToe Board
        tictactoeB.addActionListener( new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                String player2 = "pp";
                //player2 = getUserFromCB();
                
                client.handleClientCommand("#ttt " + player1 + " " + player2);

            }

        });
        
        /*EchoServer server = new EchoServer(5555);
        String players = server.getAllUsersList();
        playersListCB = new JComboBox<>(players);*/
        
        // event Handler to populate the combo box with the user's list
        /*playersListCB.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e){
                
            }
        });*/
        
        // make the window visible
        setVisible(true);

    }
    
    /**
    * This method overrides the method in the ChatIF interface. It displays a
    * message onto the screen.
    *
    * @param message The string to be displayed.
    */
    public void display( String message ){
        messageList.insert(message + "\n", 0);
    }
    
    public static void main(String[] args){
        //EchoServer server;
        GUIConsole chat = new GUIConsole(host, DEFAULT_PORT); 
        
    }
    
    
    // gathers text fromm the messageTxf and sends it to the 
    // server via client.handleMessageFromClient
    public void send(String message){

        client.handleMessageFromClientUI(message);
    }
    
    // opens the client connection
    public void openConnection(){
        // creates a new instance of ChatClient
        try {
            client = new ChatClient(host, DEFAULT_PORT, this);
            
        } catch (IOException exception) {
            System.out.println("Error: Can't setup connection!!!!"
                    + " Terminating client.");
            System.exit(1);
        }
    }
}
