package tech.quilldev.Engine.Network.Server;

import com.badlogic.gdx.Gdx;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Network.Packets.Packet;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

public class SocketManager {

    private ArrayList<QuillSocket> sockets;
    private ServerSocket serverSocket;
    private boolean active = false;

    public SocketManager() {

        //create a list for sockets
        this.sockets = new ArrayList<>();

        //start accepting new connections
        System.out.println("CREATED SOCKET MANAGER");
    }

    Thread acceptConnections = new Thread( () -> {

        log("Listening for new Connections!");

        //start listening for new connections
        while (true) {

            //if sockets are null, return
            if(this.sockets == null){ return; }
            try {
                // the socket to accept
                var socket = this.serverSocket.accept();

                // if the socket is connected print out a connection message!
                if (socket.isConnected()) {

                    // create a quillsocket from that socket
                    var qSocket = new QuillSocket(socket);

                    // add the socket to the socket list
                    this.sockets.add(qSocket);

                    //log our new connection
                    log("New Connection: @" + qSocket.getAddress());
                }
            } catch (IOException ignored) { }
        }
    });

    /**
     * Handle any data that passes through the server
     */
    public void handle() {
        this.receiveData();
    }

    /**
     * Check whether sockets are alive, or na!
     */
    public void checkLifelines() {

        // if there are no sockets, return
        if (sockets.size() == 0) {
            return;
        }

        //Make a new thread and check whether sockets are alive
        new Thread(() -> {

            System.out.printf("Checking connections of %s sockets\n", this.sockets.size());

            // Remove dead sockets
            for (var socket : sockets) {
                // if the socket is dead, disconnect it
                if (!socket.alive()) {
                    disconnectSocket(socket);
                }
            }
        }).start();
    }

    /**
     * Send a packet to all other sockets besides the one it came from
     * @param socket it came from
     */
    public void sendPacketToOtherSockets(QuillSocket socket, Packet packet){
        for(QuillSocket qSocket : this.sockets){

            //if we're on the same socket, skip
            if(socket.getAddress().equals(qSocket.getAddress())){
                continue;
            }

            //get the packets write data
            qSocket.writeLineAsync(packet.getWriteData());
        }
    }
    /**
     * Receive data from all sockets & process it
     */
    public void receiveData() {

        new Thread(() -> {
            // read data from all of the sockets
            for (var socket : sockets) {

                // read all of the sockets
                var packets = socket.readSocketSync();

                //send the packet to all other sockets
                for(var packet : packets){
                    sendPacketToOtherSockets(socket, packet);
                }
            }
        }).start();
    }

    /**
     * Get the host name of the server
     * @return the host name
     */
    public String getHostName(){
        if(this.serverSocket == null){
            return null;
        }

        return this.serverSocket.getInetAddress().getHostName();
    }

    /**
     * Get the host name of the server
     * @return the host name
     */
    public Integer getPort(){
        if(this.serverSocket == null){
            return null;
        }

        return this.serverSocket.getLocalPort();
    }
    /**
     * Disconnect the given socket
     *
     * @param socket to disconnect
     */
    private void disconnectSocket(QuillSocket socket) {
        socket.close();
        sockets.remove(socket);

        //Print that they disconnected
        log("SOCKET DISCONNECTED: " + socket.getAddress());
    }

    /**
     * Start the socket manager
     */
    public void start(){

        //if active is true, return
        if(this.active){
            return;
        }

        //set active to true
        this.active = true;

        //if the server socket is null, set it
        if(this.serverSocket == null){
            this.serverSocket = tryCreateServerSocket();
        }

        //start accepting connections
        this.acceptConnections.start();
    }

    /**
     * Stop the socket manager
     */
    public void stop(){

        //if active is false, return
        if(!this.active){
            return;
        }

        //stop accepting connections
        this.acceptConnections.interrupt();

        //set active to false
        this.active = false;
    }

    /**
     * Try to create a server socket at the given port
     * @return the port
     */
    public ServerSocket tryCreateServerSocket() {
        try {
            // try to create the socket
            return new ServerSocket(DebugModes.PORT);
        }

        // if we failed to create the socket print that we failed.
        catch (IOException e) {
            e.printStackTrace();
            this.stop();
            return null;
        }
    }

    /**
     * Log a message to the game console
     * @param message to log
     */
    public void log(String message){
        //Print that they disconnected
        Gdx.app.postRunnable( () -> GameConsole.log(message) );
    }
}