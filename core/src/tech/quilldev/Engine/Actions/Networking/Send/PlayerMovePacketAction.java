package tech.quilldev.Engine.Actions.Networking.Send;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Network.Packets.EntityMovePacket;
import tech.quilldev.Engine.Utilities.Position;

public class PlayerMovePacketAction extends Action {

    //save the last position
    private Position lastPos;

    public PlayerMovePacketAction(GameManager gameManager){
        super(gameManager);
        this.lastPos = new Position();
    }

    public boolean execute(){
        var player = gameManager.entityManager.getPlayer();

        //if the player hasn't moved, return false
        if(lastPos.approx(player.getPosition())){
            return false;
        }

        //write the packet to the manager
        var networkManager = gameManager.networkManager;
        var packet = new EntityMovePacket(player);

        //write the packets
        networkManager.getClientManager().writePacket(packet);

        //set the last pos to the current pos
        this.lastPos = new Position(player.getPosition());

        return false;
    }
}
