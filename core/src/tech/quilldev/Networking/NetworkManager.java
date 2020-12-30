package tech.quilldev.Networking;

import tech.quilldev.Engine.GameManager;

public class NetworkManager {


    private final GameManager gameManager;

    /**
     * Create the network manager
     * @param gameManager to use with the network manager
     */
    public NetworkManager(GameManager gameManager){
        this.gameManager = gameManager;
    }
}
