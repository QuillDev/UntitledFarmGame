package tech.quilldev.Engine.Networking.NetworkChildren;

import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net.Protocol;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.badlogic.gdx.utils.GdxRuntimeException;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Console.GameConsole;

public class NetworkServer {

    private ServerSocket server;
    private final SocketHints hints = new SocketHints();

    // create the socket list
    private final ArrayList<Socket> clients = new ArrayList<>();

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

        //start the data read thread
        this.readThread.start();

        //schedule for heartbeat packets every second
        scheduler.scheduleAtFixedRate(this::heartbeat, 1, 1, TimeUnit.SECONDS);
    }

    /**
     * Send heartbeat packets out to all sockets
     */
    private void heartbeat(){
        for(var client : clients){
            try {
                SocketHelper.writeLine(client, "hb");
            }catch(Exception e){

                //if it fails, remove the client
                this.clients.remove(client);

                //print the stack trace
                e.printStackTrace();
            }
        }
    }

    /**
     * Read incoming data in the given thread "forever"
     */
    private final Thread readThread = new Thread( () -> {
        while (true) {
            for(var client : clients){

                //if the client is null, continues
                if(client == null) { continue; }
                try {


                    //get data from the clinet
                    var data = SocketHelper.readData(client);

                    //if the data was null, continue
                    if(data == null){
                        continue;
                    }

                } catch(Exception e){
                    e.printStackTrace();
                }

            }
        }
    });

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
            return Gdx.net.newServerSocket(Protocol.TCP, "localhost", DebugModes.PORT, null);
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
}
