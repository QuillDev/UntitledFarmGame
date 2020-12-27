package tech.quilldev.Engine.Actions.Networking.Send;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Network.Packets.EntityMovePacket;

public class PacketUpdate extends Action {


    public PacketUpdate(GameManager gameManager){
        super(gameManager);
    }

    public boolean execute(){
        return false;
    }
}
