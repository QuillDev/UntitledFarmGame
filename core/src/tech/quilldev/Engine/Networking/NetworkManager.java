package tech.quilldev.Engine.Networking;

import tech.quilldev.Engine.Networking.NetworkChildren.NetworkClient;
import tech.quilldev.Engine.Networking.NetworkChildren.NetworkServer;

public class NetworkManager {

    private final NetworkClient client;
    private final NetworkServer server;

    public NetworkManager(){
        //ORDER IS IMPORTANT, MAKE THE CLIENT BEFORE THE SERVER!
        this.server = new NetworkServer();
        this.client = new NetworkClient();
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
}
