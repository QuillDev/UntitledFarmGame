package tech.quilldev.Engine.Actions.Networking.PacketActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

public class KeepAlivePacketAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public KeepAlivePacketAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(Packet packet){
        System.out.println("Got Keep Alive Packet!");
        System.out.println(this.gameManager.getNetworkManager().isHost());
        return true;
    }
}
