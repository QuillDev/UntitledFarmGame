package tech.quilldev.Engine.Networking.NetworkChildren;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Networking.NetworkUtils.Protocol;

public class NetworkServer {

    //Setup sockets
    private ServerSocket server;
    private final SocketHints hints = new SocketHints();

    // create the socket list
    private final ArrayList<Socket> clients = new ArrayList<>();

    //setup the host client
    private NetworkClient hostClient;

    /**
     * Create the server
     */
    public NetworkServer() {

        //create the scheduler
        ScheduledThreadPoolExecutor scheduler = new ScheduledThreadPoolExecutor(1);

        // create the server socket
        this.server = this.createServerSocket();

        //Log that we started our internal server
        log("Started Internal Server @127.0.0.1:" + DebugModes.PORT);

        // start the accept thread
        this.acceptThread.start();
        scheduler.scheduleAtFixedRate(this::read, 0, 7, TimeUnit.MILLISECONDS);
    }

    /**
     * Send packets to all connected clients
     * @param packet to send to all clients
     */
    public void sendPacket(Packet packet){
        Gdx.app.postRunnable( () -> {
            var tempClients = new ArrayList<>(clients);

            //write the packet to the given client
            for(var client : tempClients){
                var status = SocketHelper.writePacket(client, packet);

                //If we failed a write then remove the socket
                if(!status){
                    this.clients.remove(client);
                }
            }
        });
    }

    /**
     * Read packets from the clients & redistribute them to other clients
     */
    public void read() {
        Gdx.app.postRunnable( () -> {
            for(var client : this.clients){
                //if the client is null, continues
                if(client == null) { continue; }
                try {
                    //get data from the client
                    var packets = SocketHelper.readPackets(client);

                    //send the packet to all sockets
                    for(var packet : packets){
                        this.sendPacket(packet);
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the client accepting thread for taking in new connections
     */
    private final Thread acceptThread = new Thread( () -> {

        //never stop looping until the thread is interrupted
        while(true){

            //if the server is null, just continue
            if(server == null){ continue; }

            //accept the client connection to the server
            var client = server.accept(hints);

            //if the client is connected, add it to the clients list
            if(client.isConnected()){

                //add the client to the clients list
                clients.add(client);

                //write initial data to the socket
                SocketHelper.writePacket(client, new Packet(Protocol.INITIAL_DATA, "some shit"));

                //log the new client connection when it occurs
                log(String.format("Added new client connection @%s", client.getRemoteAddress()));
            }
        }
    });

    /**
     * Create the server socket
     */
    public ServerSocket createServerSocket() {
        try {
            return Gdx.net.newServerSocket(Net.Protocol.TCP, "localhost", DebugModes.PORT, null);
        } catch(GdxRuntimeException ignored){
            DebugModes.PORT++;
            return createServerSocket();
        }
    }

    /**
     * Log the message from the class to the console
     * @param message to log
     */
    private void log(String message){
        Gdx.app.postRunnable( () -> {
            GameConsole.log(String.format("[%s] %s", this.getClass().getSimpleName(), message));
        });

    }

    /**
     * Get the host socket
     * @return the host socket
     */
    public Socket getHostSocket(){

        //if the host client is null, return null
        if(this.hostClient == null){
            return null;
        }

        return hostClient.getClientSocket();
    }

    /**
     * Set the host client to the passed network client
     * @param networkClient to pass to the server
     */
    public void setHostClient(NetworkClient networkClient){
        this.hostClient = networkClient;
    }
}
