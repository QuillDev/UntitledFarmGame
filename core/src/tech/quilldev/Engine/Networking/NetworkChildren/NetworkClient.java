package tech.quilldev.Engine.Networking.NetworkChildren;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.GdxRuntimeException;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

public class NetworkClient {

    // the client socket currently being used
    private Socket clientSocket;

    // store the internal server address for future reference
    private String internalAddress;

    //get the action manager
    private final ActionManager actionManager;

    public NetworkClient(ActionManager actionManager){

        //setup the action manager
        this.actionManager = actionManager;

        // Create an executor service
        ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1);

        //By default connect to our internal server
        this.connectToInternalServer();

        //start the read thread
        scheduler.scheduleAtFixedRate(this::readData, 1000, 50, TimeUnit.MILLISECONDS);
    }

    /**
     * Try to connect to the given host
     * 
     * @param host address to connect to
     * @param port to connect to
     */
    public void connectSync(String host, int port) {
        try {

            //Log that we're attempting to connect
            log("Attempting to connect to @" + host + ":" + port);

            // try to connect to the given socket
            var tempSocket = Gdx.net.newClientSocket(Protocol.TCP, host, port, null);

            // if we connected, log connected
            if (tempSocket.isConnected()) {

                //dispose of the client socket just in case
                if(this.clientSocket != null){
                    this.clientSocket.dispose();
                }
                this.clientSocket = tempSocket;

                log("Connected to @" + this.clientSocket.getRemoteAddress());
            }

        } catch (GdxRuntimeException e) {

            //Log that we failed to connect remotely
            log("Failed to connect to remote socket, falling back to internal server..");

            // fallback to the local server if a connection could not be made!
            this.connectToInternalServer();
        }
    }

    /**
     * Write the given packet to the clients output
     * @param packet to send
     */
    public void writePacket(Packet packet){
        if(this.clientSocket == null){
            return;
        }


        Gdx.app.postRunnable( () -> {
            SocketHelper.writePacket(this.clientSocket, packet);
        });
    }

    // Create the read thread which constantly scans for data as it comes in
    public void readData() {
        try {

            //if the client socket is null, return
            if(this.clientSocket == null){
                return;
            }

            //data to add to the socket helper
            var packets = SocketHelper.readPackets(this.clientSocket);

            //handle the packets
            actionManager.handlePackets(packets);

        } catch (Exception ignored) {}
    }

    /**
     * Connect to the server on another thread (useful for connecting to remote
     * places without freezing the game)
     * 
     * @param host to connect to
     * @param port to connect to
     */
    public void connectAsync(String host, int port) {
        new Thread(() -> this.connectSync(host, port)).start();
    }

    /**
     * Connect to our internal server
     */
    public void connectToInternalServer() {
        this.connectSync("localhost", DebugModes.PORT);
        this.internalAddress = this.clientSocket.getRemoteAddress();
    }

    /**
     * Check whether we're hosting the server
     * @return whether we're hosting the server
     */
    public boolean isHost(){
        return this.internalAddress.equals(this.clientSocket.getRemoteAddress());
    }

    /**
     * Disconnecting really just connects to our internal server
     */
    public void disconnect() {

        // if we're trying to disconnect from the interal server, block it!
        if (this.clientSocket.getRemoteAddress().equals(this.internalAddress)) {
            log("Cannot disconnect from internal server!");
            return;
        }

        // connect to the internal server
        this.connectToInternalServer();
    }

    /**
     * Get the client socket
     * @return the client socket
     */
    public Socket getClientSocket(){
        return this.clientSocket;
    }

    /**
     * Log the given message to the game console
     * @param message to log
     */
    private void log(String message){
        Gdx.app.postRunnable( () -> {
            GameConsole.log(String.format("[%s] %s", this.getClass().getSimpleName(), message));
        });

    }
}
