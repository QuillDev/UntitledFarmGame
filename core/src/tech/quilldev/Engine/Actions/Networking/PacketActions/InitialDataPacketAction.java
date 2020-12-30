package tech.quilldev.Engine.Actions.Networking.PacketActions;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;

public class InitialDataPacketAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public InitialDataPacketAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(Packet packet){
        return true;
    }
}
