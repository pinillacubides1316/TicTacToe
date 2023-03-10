
import java.io.*;
import java.util.ArrayList;


public class EchoServer extends AbstractServer {
    //Class variables *************************************************

    /**
     * The default port to listen on.
     */
    //Final is a variable that once you declare it, it will never can be changed
    final public static int DEFAULT_PORT = 5555;

    //Constructors ****************************************************
    /**
     * Constructs an instance of the echo server.
     *
     * @param port The port number to connect on.
     */
    
    public EchoServer(int port) {

        super(port);

        try {
            this.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }

    }

    //Instance methods ************************************************
    /**
     * This method handles any messages received from the client.
     *
     * @param msg The message received from the client.
     * @param client The connection from which the message originated.
     */
    public void handleMessageFromClient(Object msg, ConnectionToClient client) {
        System.out.println("Message received: " + msg + " from " + client);
        //check if the message is a command(envelope)
        if(msg instanceof Envelope)
        {
            //handle command from client
            handleCommandFromClient((Envelope)msg, client);
        }else{
            //default handking of normal messages
            //this.sendToAllClients(client.getInfo("userId")+": "+msg);
            this.sendToAllClientsInRoom(msg, client);
        }
    }
    
    public void sendToAllClientsInRoom(Object msg, ConnectionToClient sender){
        Thread[] clientThreadList = getClientConnections();
        String userId = sender.getInfo("userId").toString();
        String room = sender.getInfo("room").toString();
        
        // loop through all clients
        for(int i = 0; i< clientThreadList.length; i++){
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientRoom = currentClient.getInfo("room").toString();
            String senderRoom = sender.getInfo("room").toString();
            
            // if client[i] has the same room as sender then send the message
            if(currentClientRoom.equals(senderRoom))
            {
                try{
                    currentClient.sendToClient(room +" >> " + userId+": "+ msg);
                }catch(Exception ex){
                    
                }
            }
        }
    }
    
// sends a yell to everyone in all the rooms
    public void sendToAllClientsInAllRooms(Object msg, ConnectionToClient sender){
        Thread[] clientThreadList = getClientConnections();
        String userId = sender.getInfo("userId").toString();
        
        // loop through all clients
        
        for(int i = 0; i< clientThreadList.length; i++)
        {
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            
            // Send message to all rooms
            try{
                currentClient.sendToClient("Yell >> " + userId + ": " + msg);
            }catch(Exception ex){
            }
        }
    }
    
    // only sends to clients in the same room
    public void sendToAClient(Object msg, ConnectionToClient sender, String pmTarget){
        Thread[] clientThreadList = getClientConnections();
        String userId = sender.getInfo("userId").toString();
        
        // loop through all clients
        for(int i = 0; i< clientThreadList.length; i++){
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            
            // if client[i] has the user name that matches the PMs target
            if(currentClientUserId.equals(pmTarget))
            {
                try{
                    currentClient.sendToClient("PM >> " + userId+": "+ msg);
                }catch(Exception ex){
                    
                }
            }
        }
    }
    
    // find all users in the same room and create an ArrayList<String>
    public void SameRoomUsersList(Object msg, ConnectionToClient sender){
        Thread[] clientThreadList = getClientConnections();
        String userId = sender.getInfo("userId").toString();
        String senderRoom = sender.getInfo("room").toString();
        ArrayList<String> usersList = new ArrayList<String>();
        
        // loop through all clients connections
        for(int i = 0; i < clientThreadList.length; i++){
            // get current client userId and room
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            String currentClientRoom = currentClient.getInfo("room").toString();
            
            // if client[i] has the same room as sender then send the message
            if(currentClientRoom.equals(senderRoom))
            {
                try{
                    usersList.add(currentClientUserId);
                }catch(Exception ex){
                    
                }
            }
        }
        //build an envelope with Id of "who" and the Array<String> Users list
        Envelope env = new Envelope("who",senderRoom,usersList);
        try{
            // send the envelope to the Client
            sender.sendToClient(env);
        }catch(Exception ex){
        }
    }
    
    // this methods returns a list of connected users 
    public void getAllUsersList(Object msg, ConnectionToClient client){
        Thread[] clientThreadList = getClientConnections();
        ArrayList<String> usersList = new ArrayList<String>();
        
        // loop through all clients connections
        for(int i = 0; i < clientThreadList.length; i++){
            // get current client userId and room
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            try{
                usersList.add(currentClientUserId);
            }catch(Exception ex){

            }
        }
        
        //build an envelope with Id of "usersConnected" and the Array<String> Users list
        Envelope env = new Envelope("usersConnected","",usersList);
        
        // loop through the client connections
        // this second for loop will keep ALL clients updated with the current
        // users connected to the server.
        for(int i = 0; i < clientThreadList.length; i++)
        {
            
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            try{
                
                // send the envelope to the current client
                currentClient.sendToClient(env);
            }
            catch(Exception ex){
            }
        }
    }
    
    // server receives a #ttt command, and it unpacks the TicTacToe object 
    public void processTicTacToe( TicTacToe ttt, ConnectionToClient client ) {

        // extract all the attributes from TicTacToe
        //TicTacToe ttt = (TicTacToe)env.getContents();
        String player1 = ttt.getPlayer1();
        String player2 = ttt.getPlayer2();
        int activePlayer = ttt.getActivePlayer();
        int gameState = ttt.getGameState();
        char[][] board = ttt.getBoard();
        
        
        // If GameState is 1 (invite)
        if (gameState == 1) {
            // Create an instance of the game to the userInfo (eg. setInfo(???ttt???,ticTacToe) ) for both players;
            setPlayersToTTT(player1, player2, ttt);
            
            // Send an envelope with the TicTacToe object to player 2
            sendEnvToPlayer("ttt", player2, ttt, client );
            
        }
        // If GameState is 2 (decline)
        else if (gameState == 2) {
            // Send the envelope with the TicTacToe object to player 1
            sendEnvToPlayer("ttt", player1, ttt, client );
        }
        // If GameState is 3 (playing)
        else if (gameState == 3) {
            // Save the instance of TicTacToe to the userInfo for both players
            setPlayersToTTT(player1, player2, ttt);
            
            // Swap the active player
            swapActivePlayer(ttt);
            
            // send the envelope with the TicTacToe object to the active player
            sendEnvToActivePlayer("ttt",ttt, client);
            
        }
        // If GameState is 4 (won)
        else if (gameState == 4) {
            // Swap the active player
            swapActivePlayer(ttt);

            // Send an envelope with the TicTacToe object to the active player
            sendEnvToActivePlayer("ttt",ttt, client);
        }
    }

    
    // add an instance of the game in the players
    public void setPlayersToTTT(String player1, String player2, TicTacToe ticTacToe){
        Thread[] clientThreadList = getClientConnections();
        
        // loop through all clients connections
        for(int i = 0; i < clientThreadList.length; i++){
            // get current client userId and room
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            
            // if client[i] has the same room as sender then send the message
            if(currentClientUserId.equals(player1) || currentClientUserId.equals(player2))
            {
                try{
                    currentClient.setInfo("ttt", ticTacToe);
                }catch(Exception ex){
                    
                }
            }
        }
    }
    
    // send an instance of TicTacToe to a player
    public void sendEnvToPlayer(String id, String player, TicTacToe ticTacToe, ConnectionToClient sender){
        Thread[] clientThreadList = getClientConnections();
        // loop through all clients
        for(int i = 0; i< clientThreadList.length; i++){
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            
            // if client[i] has the user name that matches the PMs target
            if(currentClientUserId.equals(player))
            {
                try{
                    // create an env
                    Envelope env = new Envelope(id, "", ticTacToe);
                    
                    // send the env to client id ???tttDecline???,??????,ttt
                    currentClient.sendToClient(env);
                }catch(Exception ex){
                    
                }
            }
        }
    }
    
    // Swap the active player
    public void swapActivePlayer(TicTacToe ticTacToe){
        // get the active player
        int activePlayer = ticTacToe.getActivePlayer();
        if(activePlayer==1)
        {
            ticTacToe.setActivePlayer(2);
        }else{
            ticTacToe.setActivePlayer(1);
        }
        
    }
    
    // send an envelope to the active player
    public void sendEnvToActivePlayer(String envId, TicTacToe ticTacToe,ConnectionToClient client){
        Thread[] clientThreadList = getClientConnections();
        String player1 = ticTacToe.getPlayer1();
        String player2 = ticTacToe.getPlayer2();
        int activePlayer = ticTacToe.getActivePlayer();
        String currentPlayer;
        
        // define who is the active player
        if(activePlayer == 1)
        {
           currentPlayer = player1;
        }else{
            currentPlayer = player2;
        }
        // loop through all clients connections
        for(int i = 0; i < clientThreadList.length; i++){
            // get current client userId and room
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            
            // if client[i] is the active player, send an envelope with the ticTacToe Object
            if(currentClientUserId.equals(currentPlayer))
            {
                try{
                    Envelope env = new Envelope(envId,"",ticTacToe);
                    currentClient.sendToClient(env);
                }catch(Exception ex){
                    
                }
            }
        }
    }
    
    // get the TicTacToe Object from the user info 
    public TicTacToe getTicTacToeObj(ConnectionToClient client)
    {
        Thread[] clientThreadList = getClientConnections();
        String userId = client.getInfo("userId").toString();
        TicTacToe ttt = new TicTacToe();
        // loop through all clients connections
        for(int i = 0; i < clientThreadList.length; i++){
            // get current client userId and room
            ConnectionToClient currentClient = ((ConnectionToClient) clientThreadList[i]);
            String currentClientUserId = currentClient.getInfo("userId").toString();
            
            // if client[i] is the active player, get the ticTacToe Object
            if(currentClientUserId.equals(userId))
            {
                try{
                    ttt = (TicTacToe)currentClient.getInfo("ttt");
                }catch(Exception ex){
                    
                }
            }
        }
        return ttt;
    }
    
    public void handleCommandFromClient(Envelope env, ConnectionToClient client)
    {
        String id = env.getId();
        
        if(id.equals("login"))
        {
            //setInfo
            client.setInfo("userId", env.getContents().toString());
        }
        // join command contains room contents
        if(id.equals("join"))
        {
            client.setInfo("room", env.getContents().toString());
        }
        
        // send message only to target whose userID == env.args
        if(id.equals("pm"))
        {
            String message = env.getContents().toString();
            String target = env.getArgs();
            sendToAClient(message,client,target);
        }
        
        // get the message to be send to everyone in all the rooms
        if(id.equals("yell"))
        {
            String message = env.getContents().toString();
            sendToAllClientsInAllRooms(message,client);
        }
        
        // send back an envelope with an ArrayList<String> containing all members
        // of the same room the sender is in
        if(id.equals("who"))
        {
            String msgId = env.getId().toString();
            SameRoomUsersList(msgId,client);
        }
        
        // get a list of all the users connected to populate the ComboBox
        if(id.equals("usersConnected"))
        {
            String msgId = env.getId().toString();
            getAllUsersList(msgId,client);
        }
        
        // get the TicTacToe object and send it to be processed.
        if(id.equals("ttt"))
        {
            String msgId = env.getId().toString();
            TicTacToe ttt = (TicTacToe)env.getContents();
            
            processTicTacToe(ttt,client);
        }
        
        // if the player2 Declines to play
        if(id.equals("tttDecline"))
        {
            // extract the TicTacToe object from the userInfo
            TicTacToe ticTacToe = new TicTacToe();
            ticTacToe = getTicTacToeObj(client);
            
            // change the TicTacToe object gamestate to 2 (declined)
            ticTacToe.setGameState(2);
            
            // Send the object (in an envelope) to Player1
            processTicTacToe(ticTacToe,client);
        }
        
        // if the player2 Accepts to play
        if(id.equals("tttAccept"))
        {
            // extract TicTacToe object from userInfo
            TicTacToe ticTacToe = new TicTacToe();
            ticTacToe = getTicTacToeObj(client);
            
            // change TicTacToe object to gameState 3 (playing)
            ticTacToe.setGameState(3);
            
            // send object in envelope back to Player1
            processTicTacToe(ticTacToe,client);
        }

    }
    
    

    /**
     * This method overrides the one in the superclass. Called when the server
     * starts listening for connections.
     */
    protected void serverStarted() {
        System.out.println("Server listening for connections on port " + getPort());
    }

    /**
     * This method overrides the one in the superclass. Called when the server
     * stops listening for connections.
     */
    protected void serverStopped() {
        System.out.println("Server has stopped listening for connections.");
    }

    //Class methods ***************************************************
    /**
     * This method is responsible for the creation of the server instance (there
     * is no UI in this phase).
     *
     * @param args[0] The port number to listen on. Defaults to 5555 if no
     * argument is entered.
     */
    public static void main(String[] args) {
        int port = 0; //Port to listen on

        //port = DEFAULT_PORT; //Set port to 5555

        // get the port number from the command line arguments
        try{
            port = Integer.parseInt(args[0]);
            
        }catch(ArrayIndexOutOfBoundsException aioobe){
            port = DEFAULT_PORT;
        }
        
        EchoServer sv = new EchoServer(port);

        try {
            //sv.listen(); //Start listening for connections
        } catch (Exception ex) {
            System.out.println("ERROR - Could not listen for clients!");
        }

    }

    protected void clientConnected(ConnectionToClient client) {

        System.out.println("<Client Connected:" + client + ">");

    }

    //override client exception so that it only calls client disconnected on an IOException
    synchronized protected void clientException(ConnectionToClient client, Throwable exception) {

        if (exception instanceof IOException) {
            clientDisconnected(client);
        }
    }

    //overrides hook method to display message to console
    synchronized protected void clientDisconnected(ConnectionToClient client) {

        System.out.println("<Client Disconnected: " + client + ">");
    }
}
//End of EchoServer class
