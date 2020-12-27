package tech.quilldev.Engine.Network.Client;

import com.badlogic.gdx.Gdx;
import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Network.Packets.Packet;
import tech.quilldev.Engine.Network.Packets.Protocol;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class NetworkClientManager {

    // booleans for managing connection state
    private Socket clientSocket;

    // the connection state of the socket
    private ConnectionState connectionState;

    //Time in seconds before timing out
    private final int timeout = 10;

    //instance of the action manager for game updates
    private final ActionManager actionManager;
    // create the network manager
    public NetworkClientManager(ActionManager actionManager) {

        // start off as a disconnected socket
        this.connectionState = ConnectionState.DISCONNECTED;

        //setup the action manager
        this.actionManager = actionManager;

        //Create the scheduler
        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);
        scheduler.scheduleAtFixedRate(this::keepAlive, 0, 1, TimeUnit.SECONDS);


        // create the socket
        this.clientSocket = null;
    }

    /**
     * Try to connect to the given host and port
     * @param host to connect to
     * @param port of the host
     */
    public void connect(String host, int port) {

        //make sure we're disconnected
        this.disconnect();

        // set the socket state to connecting
        this.connectionState = ConnectionState.CONNECTING;

        //Try to connect
        log("Connecting");

        // address to connect with
        var address = new InetSocketAddress(host, port);

        //close the current socket connection if there is one
        this.closeSocket();

        //start connecting on a new thread
        new Thread( () -> {

            try {
                //try to connect to the new address
                this.clientSocket.connect(address, timeout * 1000);

                log("Connected to host!");
                // if we connect set the connection state to connected
                this.connectionState = ConnectionState.CONNECTED;

            } catch (IOException ignored) {
                this.closeSocket();
                log("Failed to connect to remote host!");
            }
        }).start();
    }

    /**
     * Write a packet to the server synchronously (not on a independent thread)
     * @param data to send to the server as a string
     */
    public void writeSync(String data) {
        if (!connectionState.equals(ConnectionState.CONNECTED)) {
            return;
        }

        // if there's no data, return
        if (data == null || data.length() <= 0) {
            return;
        }

        try {
            // get the output string to write to
            var stream = this.clientSocket.getOutputStream();
            stream.write(data.getBytes());
            stream.flush();

        } catch (SocketException socketException) {
            this.reconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * Try to reconnect to the server
     */
    public void reconnect(){

        //if the connection state isn't disconnected, return
        if(!this.connectionState.equals(ConnectionState.DISCONNECTED)){
            return;
        }
        //if the client socket is null, return
        if(clientSocket == null || clientSocket.getInetAddress() == null){
            return;
        }

        //get the host and port
        var host = this.clientSocket.getInetAddress().getHostName();
        var port = this.clientSocket.getPort();

        //disconnect the current socket
        this.disconnect();

        //try to connect to the port we were connected to
        this.connect(host, port);

        //if we fail to connect, the connection timed out
        if(!this.connectionState.equals(ConnectionState.CONNECTED)){
            GameConsole.log("Reconnection window timed out!");
        }
    }

    /**
     * Write data to the socket asynchronously (on an independent thread)
     * @param data to write to the server
     */
    public void writeAsync(String data) {
        // try to write to the stream on a new thread
        new Thread(() -> this.writeSync(data)).start();
    }

    /**
     * Write the given data with a newline character at the end
     */
    public void writeLineAsync(String data){
        new Thread( () -> this.writeAsync(data + "\n")).start();

    }

    /**
     * Write a protocol with no data
     * @param protocol to send
     */
    public void writeProtocol(Protocol protocol){
        new Thread( () -> this.writeLineAsync(protocol.getPacket())).start();

    }

    /**
     * Write the packet to the socket
     * @param packet to write
     */
    public void writePacket(Packet packet){
        new Thread( () -> this.writeLineAsync(packet.getWriteData())).start();

    }

    /**
     * Send the keep alive packet
     */
    public void keepAlive(){

        if(!connectionState.equals(ConnectionState.CONNECTED)){
            return;
        }

        //send the keep alive packet
        this.writeProtocol(Protocol.KEEP_ALIVE);
    }

    /**
     * Read the socket
     */
    public void readSocket(){
        //read on a new thread
        new Thread( () -> {

            // if the socket is null, return
            if (clientSocket == null) {
                return;
            }

            try {
                var stream = this.clientSocket.getInputStream();

                var available = stream.available();

                //if there are no bytes available, return
                if (available == 0) {
                    return;
                }

                //read all available bytes from the stream
                var bytes = stream.readNBytes(available);

                //byte string builder
                var byteStringBuilder = new StringBuilder();

                //convert all bytes to characters
                for (var b : bytes) {
                    byteStringBuilder.append((char) b);
                }

                //get the data string
                var commands = byteStringBuilder.toString().split("\n");

                //create a list for packets
                var packets = new ArrayList<Packet>();

                //print all commands
                for (var command : commands) {
                    var packet = new Packet(command);

                    //if the packet is malformed, skip it
                    if(packet.isMalformed()){
                        continue;
                    }
                    packets.add(packet);
                }

                //execute a server update with the packets
                Gdx.app.postRunnable( () -> actionManager.serverUpdate(packets));

            }
            catch (SocketException ignored){
                this.reconnect();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
    /**
     * Disconnect the given socket from the server
     */
    public void disconnect() {

        // if we're not connected, just return
        if (!connectionState.equals(ConnectionState.CONNECTED)) {
            return;
        }

        try {
            //close the socket
            this.clientSocket.close();

            // setup the client socket as a fresh new socket
            this.clientSocket = new Socket();

            log("Disconnected from Server!");

        } catch (IOException ignored) {}

    }

    /**
     * Get the connection state
     * @return the connection state
     */
    public ConnectionState getConnectionState() {
        return this.connectionState;
    }

    /**
     * Close the client socket and replace it
     */
    public void closeSocket() {
        try {

            //if there is an existing socket close it
            if(this.clientSocket != null){
                this.clientSocket.close();
                this.connectionState = ConnectionState.DISCONNECTED;
            }

            //override the current socket with a new one
            this.clientSocket = new Socket();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Start listening for incoming data
     */
    public void read() {

        //start the new thread
        new Thread(this::readSocket).start();
    }

    /**
     * Log in a post runnable
     * @param message to log
     */
    private void log(String message){
        Gdx.app.postRunnable( () -> GameConsole.log(message));
    }
}
