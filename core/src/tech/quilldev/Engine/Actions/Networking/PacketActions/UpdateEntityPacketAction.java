package tech.quilldev.Engine.Actions.Networking.PacketActions;

import org.json.JSONException;
import org.json.JSONObject;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.DynamicEntities.MultiplayerPlayer;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Utilities.Position;

import java.util.UUID;

public class UpdateEntityPacketAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public UpdateEntityPacketAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(Packet packet){

        var json = new JSONObject(packet.getData());
        var uuid = UUID.fromString(json.getString("uuid"));
        var x = json.getFloat("x");
        var y = json.getFloat("y");

        System.out.println("EntityMovePacket: {\n\tuuid:" + uuid + "\n" + "\tx: " + x + " y: " + y + "\n}");
        var entityManager = gameManager.entityManager;
        var match = entityManager.getEntityWithUUID(uuid);

        if(match == null){
            System.out.println("match is null");
            return  false;
        }

        //if we get the player return false
        if(match.getUuid().equals(entityManager.getPlayer().getUuid())){
            return false;
        }

        //otherwise set the position of the match to the given x and y
        match.setPosition(new Position(x, y));
        /**
        try {
        } catch (JSONException ignored){
            return false;
        }*/
        return false;
    }
}
