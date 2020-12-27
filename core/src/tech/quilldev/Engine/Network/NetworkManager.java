package tech.quilldev.Engine.Network;

import com.badlogic.gdx.Gdx;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.Engine.Network.Client.ConnectionState;
import tech.quilldev.Engine.Network.Client.NetworkClientManager;
import tech.quilldev.Engine.Network.Server.ServerManager;

public class NetworkManager {

    //Get our client and server managers
    private final NetworkClientManager clientManager;
    private final ServerManager serverManager;
    private boolean hosting = false;

    /**
     * Create a new network manager
     * @param actionManager needed for executing client side action updates
     */
    public NetworkManager(ActionManager actionManager){
        this.clientManager = new NetworkClientManager(actionManager);
        this.serverManager = new ServerManager();
    }

    /**
     * Connect to the given connection
     * @param host to connect to
     * @param port to connect to
     */
    public void connect(String host, int port){
        this.clientManager.connect(host, port);
    }

    /**
     * Disconnect from the server
     */
    public void disconnect(){
        this.clientManager.disconnect();
    }

    /**
     * Stop hosting the server
     */
    public void stopHosting(){
        if(!hosting){
            return;
        }

        //stop the server and disconnect
        this.serverManager.stop();
        this.clientManager.disconnect();
    }

    /**
     * Start hosting a server if we can
     */
    public void host() {

        //if we can't host, say so
        if (!canHost()) {
            GameConsole.log("Cannot host a server while connected to another server!");
            return;
        }

        new Thread( () -> {
            //start the local server
            serverManager.start();
            clientManager.connect("localhost", DebugModes.PORT);

            //log that we connected
            Gdx.app.postRunnable(() -> {
                GameConsole.log(String.format("Server Started @ %s", serverManager.getSocketAddress()));
            });

        }).start();

        this.hosting = true;
    }

    /**
     * Whether we're currently hosting a server
     * @return whether we're hosting a server
     */
    public boolean isHosting(){
        return this.hosting;
    }
    /**
     * If our client is disconnected, then we can start hosting
     * @return whether we can host
     */
    private boolean canHost(){
        return this.clientManager.getConnectionState().equals(ConnectionState.DISCONNECTED);
    }

    /**
     * Check if we're connected
     * @return whether we're connected ot not
     */
    public boolean connected(){
        return this.clientManager.getConnectionState().equals(ConnectionState.CONNECTED);
    }

    /**
     * Get the network client manager
     * @return the client manager
     */
    public NetworkClientManager getClientManager() {
        return clientManager;
    }
}
