
import java.io.*;
import java.util.ArrayList;

/**
 * This class overrides some of the methods defined in the abstract superclass
 * in order to give more functionality to the client.
 */
public class ChatClient extends AbstractClient {
    //Instance variables **********************************************

    /**
     * The interface type variable. It allows the implementation of the display
     * method in the client.
     */
    ChatIF clientUI;
    
    // instance of TicTacToeGUI
    TTTBoardGUI tttBoard;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the chat client.
     *
     * @param host The server to connect to.
     * @param port The port number to connect on.
     * @param clientUI The interface type variable.
     */
    public ChatClient(String host, int port, ChatIF clientUI)
            throws IOException {
        super(host, port); //Call the superclass constructor
        this.clientUI = clientUI;
        //openConnection();
    }

    //Instance methods ************************************************
    /**
     * This method handles all data that comes in from the server.
     *
     * @param msg The message from the server.
     */
    public void handleMessageFromServer(Object msg) {
        // if the message from the server is an Envelope 
        if(msg instanceof Envelope){
            // sends them to a handle server command method
            HandleCommandFromServer((Envelope)msg);
        }else{
            clientUI.display(msg.toString());
        }
    }
    
    //Handle the commands from server
    public void HandleCommandFromServer(Envelope env){
        //if the envelope has an Id of “who”
        if (env.getId().equals("who"))
        {
            // extract the arraylist from the envelope
            ArrayList<String> envContents = new ArrayList<String>((ArrayList)env.getContents());
            String senderRoom = env.getArgs();
            
            //Display the sender Room
            clientUI.display("List of users in the room: " + senderRoom);
            clientUI.display("_______________________________________");
            //loop through all the users list 
            for(int i = 0; i < envContents.size(); i++)
            {
                String currentUser = envContents.get(i);
                clientUI.display(currentUser);
            }
        }
        //if the envelope has an Id of “usersConnected”
        if (env.getId().equals("usersConnected"))
        {
            // extract the arraylist from the envelope
            ArrayList<String> envContents = new ArrayList<String>((ArrayList)env.getContents());
            // add items to the JComboBox
            // loop through all the users list 
            for(int i = 0; i < envContents.size(); i++)
            {
                String currentUser = envContents.get(i);
                
                //clientUI.display(currentUser);
            }
        }
        
        // when client receives the ttt envelope command, unpack the tictactoe object
        // and then call the processTicTacToe method
        if(env.getId().equals("ttt"))
        {
            String msgId = env.getId();
            TicTacToe ttt = (TicTacToe)env.getContents();
            processTicTacToe(ttt);
        }
        
        // id player 2 decline the invitation
        if(env.getId().equals("tttDecline"))
        {
            // get the TicTacToe Object
            TicTacToe ttt = (TicTacToe)env.getContents();
            
            processTicTacToe(ttt);
            
        }
        
        // id player 2 Accepts the invitation
        if(env.getId().equals("tttAccept"))
        {
            // get the TicTacToe Object
            TicTacToe ttt = (TicTacToe)env.getContents();
            
            processTicTacToe(ttt);
            
        }
    }

    /**
     * This method handles all data coming from the UI
     *
     * @param message The message from the UI.
     */
    public void handleMessageFromClientUI(String message) {

        if (message.charAt(0) == '#') {

            handleClientCommand(message);

        } else {
            try {
                sendToServer(message);
            } catch (IOException e) {
                clientUI.display("Could not send message to server.  Terminating client.......");
                quit();
            }
        }
    }

    /**
     * This method terminates the client.
     */
    public void quit() {
        try {
            closeConnection();
        } catch (IOException e) {
        }
        System.exit(0);
    }

    public void connectionClosed() {

        System.out.println("Connection closed");

    }

    protected void connectionException(Exception exception) {

        System.out.println("Server has shut down");

    }

    public void handleClientCommand(String message) {

        if (message.equals("#quit")) {
            clientUI.display("Shutting Down Client");
            quit();
        }

        if (message.equals("#logoff")) {
            clientUI.display("Disconnecting from server");
            try {
                closeConnection();
            } catch (IOException e) {
            }
        }

        if (message.indexOf("#setHost") >= 0) {

            if (isConnected()) {
                clientUI.display("Cannot change host while connected");
            } else {
                setHost(message.substring(8, message.length()));
            }
        }

        if (message.indexOf("#setPort") >= 0) {
            //#setport 5556
            if (isConnected()) {
                clientUI.display("Cannot change port while connected");
            } else {
                setPort(Integer.parseInt(message.substring(8, message.length()).trim()));
            }
        }
        
        //We want it passes a user name: #login user
        if (message.indexOf("#login")>=0) {
            //#loging Adri
            if (isConnected()) {
                clientUI.display("already connected");
            } else {

                try {
                    String userId = message.substring(6,message.length()).trim();
                    Envelope env = new Envelope("login","",userId);
                    
                    openConnection();
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }
        
        //We want to join to different rooms
        if (message.indexOf("#join")>=0) {
            //#join room
            if (!isConnected()) {
                clientUI.display("Not connected. Could not join the room");
            } else {

                try {
                    String roomName = message.substring(5,message.length()).trim();
                    
                    //create a new envelope with the id of join and the contents of the room
                    Envelope env = new Envelope("join","",roomName);
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }

        //We want to send PMs to a specific user
        if (message.indexOf("#pm")>=0) {
            //#pm Adri this is my message
            
            //user cannot send pm if they are not connected
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send pm");
            } else {

                try {
                    // parse out #pm
                    String targetAndMessage = message.substring(3,message.length()).trim();
                    
                    //get the first argument which is the target
                    String pmTarget = targetAndMessage.substring(0, targetAndMessage.indexOf(" "));
                    
                    //get the second argument which is the pm client wishes to send
                    String pmContent = targetAndMessage
                            .substring(targetAndMessage.indexOf(" "),targetAndMessage.length())
                            .trim();
                    
                    //create a new envelope with the id of join and the contents of the room
                    Envelope env = new Envelope("pm",pmTarget,pmContent);
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }
        
        //We want to send a #yell to all the rooms
        if (message.indexOf("#yell") >= 0) {
            //#yell Hi this is my message
            
            //user cannot send yell if they are not connected
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send a yell");
            } else {

                try {
                    // parse out #yell
                    String yellMessage = message.substring(5,message.length()).trim();
                    
                    //create a new envelope with the id of join and the contents of the room
                    Envelope env = new Envelope("yell","",yellMessage);
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }
        
        if (message.indexOf("#who") >= 0) {
            //#who - command to returnn a list of users in the same room
            
            //user cannot send pm if they are not connected
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send pm");
            } else {

                try {                    
                    // sends an Envelope to the server with only an Id (of “who”)
                    Envelope env = new Envelope("who","","");
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to find others in room.");
                }
            }
        }
        
        if(message.indexOf("#usersConnected") >= 0){
            // cannot send any message if not clients connected 
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send a message");
            } else {
                try {
                    //create a new envelope with the id of join and the contents of the room
                    Envelope env = new Envelope("usersConnected","","");
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }
        
        if(message.indexOf("#ttt ") >= 0){
            // cannot send any message if not clients connected 
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send a message");
            } else {
                try {
                    // get players 1 and 2
                    String player1and2 = message.substring(4,message.length()).trim();
                    String player1 = player1and2.substring(0, player1and2.indexOf(" "));
                    String player2 = player1and2.substring(player1and2.indexOf(" "), player1and2.length()).trim();
                    char[][] emptyBoard = new char[3][3];
                
                    // create an instance of the Tic Tac Toe object
                    // Initial state sets player 1 to the inviting player, player 2 is in the User List combo box.  **** 
                    // Active player is 2, game state is 1 (invite).  The board is empty.
                    TicTacToe ttt = new TicTacToe(player1,player2,2,1,emptyBoard);
                    // ============== Solve the user list combo box=========================

                    //Display the TicTacToe board 
                    //tttBoard = new TTTBoardGUI();
                    //tttBoard.setVisible(true);
                    //ticTacToeBoard(true);

                    // create an envelope with the ttt object and send it to the server
                    Envelope env = new Envelope("ttt","",ttt);
                    
                    //send to the server
                    sendToServer(env);
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
            }
        }
        
        if(message.equals("#tttDecline"))
        {
            // cannot send any message if not clients connected 
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send a message");
            }
            else 
            {
                try 
                {
                    // create an envelope with the ttt object and send it to the server
                    Envelope env = new Envelope("tttDecline","", "");

                    // send the envelope to the server
                    sendToServer(env);
                } 
                catch (IOException e) 
                {
                    clientUI.display("failed to connect to server.");
                }
            } 
        }
        
        if(message.equals("#tttAccept"))
        {
            // cannot send any message if not clients connected 
            if (!isConnected()) {
                clientUI.display("Not connected. Could not send a message");
            } else {
                
                try{
                    // display the board
                    TTTBoardGUI tttB = new TTTBoardGUI();
                    tttB.setVisible(true);

                    // send the accept command to the server
                    Envelope env = new Envelope("tttAccept", "", "");
                    
                    //send to the server
                    sendToServer(env);
                    
                } catch (IOException e) {
                    clientUI.display("failed to connect to server.");
                }
                
            }
        }
    }
    
    public void processTicTacToe(TicTacToe ttt){
        
        // extract all the attributes from TicTacToe
        //TicTacToe ttt = (TicTacToe)env.getContents();
        String player1 = ttt.getPlayer1();
        String player2 = ttt.getPlayer2();
        int activePlayer = ttt.getActivePlayer();
        int gameState = ttt.getGameState();
        char[][] board = ttt.getBoard();
       
        
        // invite state
        if(gameState == 1)
        {
            // display an invite message
            clientUI.display("You have been invited to \nplay TicTacToe with " +player1+" \n#tttAccept to accept, \n#tttDecline to decline.");
            //String message = ("You have been invited to \nplay TicTacToe with " +player1+" \n#tttAccept to accept, \n#tttDecline to decline.");
            //env = new Envelope("tttInvite",player2,message);
            try{
                //sendToServer(message);
                //sendToServer(env);
            }catch(Exception e){
                
            }
        }
        
        // decline state
        else if(gameState == 2)
        {
            // display message that the game was declined
            clientUI.display("Your game was declined.");
            //String message = ("Your game was declined.");

            // hide the tictactoe board
            //tttB.setVisible(false);
        }
        
        // playing state
        else if(gameState == 3)
        {
            // display "your turn" message
            clientUI.display("Your turn to play TicTacToe");
            
            // save the TicTacToe object to the TicTacToeConsole??????????????
            ttt = new TicTacToe(player1,player2,ttt.getActivePlayer(),ttt.getGameState(),ttt.getBoard());
            
            // use UpdateBoardMethod to adjust button text properties?????????????
            ttt.updateBoard(1);
        }
        
        // win / lose state
        else if(gameState == 4)
        {
            // display you lost message
            clientUI.display("You have lost.");
            
            // hide the board
            tttBoard.setVisible(false);
        }
        
    }
    
    
}
//End of ChatClient class
