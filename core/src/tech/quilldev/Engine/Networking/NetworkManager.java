package tech.quilldev.Engine.Networking;

import tech.quilldev.Engine.Actions.ActionManager;
import tech.quilldev.Engine.Networking.NetworkChildren.NetworkClient;
import tech.quilldev.Engine.Networking.NetworkChildren.NetworkServer;

public class NetworkManager {

    private final NetworkClient client;
    private final NetworkServer server;

    public NetworkManager(ActionManager actionManager){
        //ORDER IS IMPORTANT, MAKE THE CLIENT BEFORE THE SERVER!
        this.server = new NetworkServer();
        this.client = new NetworkClient(actionManager);
        this.server.setHostClient(this.client);
    }

    /**
     * Get the client manager from the network manager
     * @return the client manager
     */
    public NetworkClient getClient() {
        return client;
    }

    /**
     * Get the server manager from the network manager
     * @return the server manager
     */
    public NetworkServer getServer() {
        return server;
    }

    /**
     * Get whether we're the current host of the server
     * @return whether we're the current host of the server
     */
    public boolean isHost(){
        return this.getClient().isHost();
    }
}
