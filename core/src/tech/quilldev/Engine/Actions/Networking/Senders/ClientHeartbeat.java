package tech.quilldev.Engine.Actions.Networking.Senders;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Networking.NetworkUtils.Packets.KeepAlivePacket;
import tech.quilldev.Engine.Networking.NetworkUtils.Protocol;

public class ClientHeartbeat extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public ClientHeartbeat(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(){
        var network = gameManager.getNetworkManager();

        network.getClient().writePacket(new Packet(Protocol.KEEP_ALIVE, gameManager.entityManager.getPlayer().uuid.toString()));

        return false;
    }
}
