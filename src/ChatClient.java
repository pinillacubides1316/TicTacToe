
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
            for(int i = 0; i<envContents.size();i++)
            {
                String currentUser = envContents.get(i);
                clientUI.display(currentUser);
            }
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
            //#pm Adri this is my message
            
            //user cannot send pm if they are not connected
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
        
        if (message.indexOf("#who")>=0) {
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
    }

}
//End of ChatClient class
