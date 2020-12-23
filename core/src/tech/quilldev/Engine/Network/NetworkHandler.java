package tech.quilldev.Engine.Network;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.net.Socket;

import com.badlogic.gdx.utils.GdxRuntimeException;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.GameManager;
import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;

public class NetworkHandler {

    private final GameManager gameManager;
    private Socket socket;
    public boolean connecting;

    public NetworkHandler(GameManager gameManager){
        this.gameManager = gameManager;
        this.connecting = false;
        connect();
    }

    /**
     * Try to connect to the network address
     */
    public void connect() {

        //if we're trying to connect, just return
        if(connecting){
            return;
        }

        new Thread(() -> {
            try {
                //set connecting to false
                connecting = true;

                //try to connect
                socket = Gdx.net.newClientSocket(Net.Protocol.TCP, "localhost", 2069, null);
            }
            catch (GdxRuntimeException ignored){}
            finally {
                //set connecting to false after we're done with this attempt
                connecting = false;
            }
        }).start();
    }

    /**
     * Receive data from the server
     */
    public void updateClient(){
        Gdx.app.postRunnable(() -> {
            if(!socketReady()){
                return;
            }
            //TODO Lag compensation here
            try {
                //get any server responses
                var stream = this.socket.getInputStream();

                //read all of the available bytes
                var available = stream.available();
                var bytes = stream.readNBytes(available);
                var stringBuilder = new StringBuilder();

                //covert bytes to strings
                for(var b : bytes){
                    stringBuilder.append((char) b);
                }

                //get all server updates
                var updates = stringBuilder.toString().split("\n");

                //apply all of the available updates
                for(var update : updates){
                    this.applyClientUpdate(update);
                }
            }catch (SocketException socketException) {
                this.socket.dispose();
            }
            catch (IOException ignored) {}
        });
    }

    /**
     * Send data to the server
     */
    public void updateServer(){

        //if the socket is disconnected return
        if(!socketReady()){
            return;
        }

        Gdx.app.postRunnable(() -> {

            try {
                var player = gameManager.entityManager.getPlayer();
                var packet = new UpdatePacket(player).getJson();
                // write to the socket
                socket.getOutputStream().write(packet.getBytes(StandardCharsets.UTF_8));

            } catch (SocketException socketException){
                this.socket.dispose();
            } catch (IOException ignored){}
        });
    }

    /**
     * Apply client updates from the json string
     * @param jsonString to make updates from
     */
    private void applyClientUpdate(String jsonString){

        //the packet we got
        var serverPacket = UpdatePacket.buildFromJsonString(jsonString);

        //if the packet was trash, just return
        if(serverPacket == null) {
            return;
        }

        gameManager.entityManager.serverUpdate(serverPacket);
    }
    /**
     * Dispose of the socket after we're done with it
     */
    public void dispose(){
        this.socket.dispose();
    }

    /**
     * Check if the socket is ready
     * @return whether the socket is ready
     */
    public boolean socketReady(){
        if(this.socket == null){
            return false;
        }

        return this.socket.isConnected();
    }
}

