package tech.quilldev.Engine.Actions.Networking.Senders;

import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.FullEntityClone;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packets.EntityUpdatePacket;
import tech.quilldev.Engine.Utilities.Position;

public class SendPlayerUpdatePacket extends Action {

    private Position lastPos = new Position();
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public SendPlayerUpdatePacket(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(){

        // get the player
        var player = gameManager.entityManager.getPlayer();

        //get the players current pos
        if(player.getPosition().equals(lastPos)){
            return false;
        }

        //set last pos
        this.lastPos = new Position(player.getPosition());

        //clone the player
        var clone = new FullEntityClone(player);

        //the packet to write
        var packet = new EntityUpdatePacket(clone);

        //write the packet to the clients socket
        this.gameManager.getNetworkManager().getClient().writePacket(packet);

        return true;
    }
}
