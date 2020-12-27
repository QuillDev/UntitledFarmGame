package tech.quilldev.Engine.Actions.Networking.Recieve;

import org.json.JSONException;
import org.json.JSONObject;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.DynamicEntities.MultiplayerPlayer;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Network.Packets.Packet;
import tech.quilldev.Engine.Utilities.Position;

import java.util.UUID;

public class EntityMovePacketAction extends Action {

    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public EntityMovePacketAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(Packet packet){

        var entityManager = gameManager.entityManager;

        //try to get json from the packet
        try {
            var json = new JSONObject(packet.data);
            var uuid = UUID.fromString(json.getString("uuid"));
            var x = json.getFloat("x");
            var y = json.getFloat("y");

            //create the player
            var entity = entityManager.getEntityWithUUID(uuid);

            //if the entity is null, create a player for it and register it
            if(entity == null){
                var newPlayer = new MultiplayerPlayer(uuid, new Position(x, y));
                entityManager.registerPlayer(newPlayer);
                return true;
            }

            //if hte uuids are the same return
            if(entity.uuid.equals(entityManager.getPlayer().getUuid())){
                return false;
            }

            //set the entities position to the new one
            entity.setPosition(new Position(x, y));
        }
        catch (JSONException ignored){
            return false;
        }

        return false;
    }
}
