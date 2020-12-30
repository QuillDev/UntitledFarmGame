package tech.quilldev.Engine.Actions.Networking.PacketActions;

import org.json.JSONException;
import org.json.JSONObject;
import tech.quilldev.Engine.Actions.Action;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectDictionary;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectType;
import tech.quilldev.Engine.GameManager;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Utilities.Position;

import java.util.UUID;

public class SpawnObjectPacketAction extends Action {
    /**
     * Constructor for new actions
     * @param gameManager main game manager
     */
    public SpawnObjectPacketAction(GameManager gameManager) {
        super(gameManager);
    }

    public boolean execute(Packet packet){

        //get the raw data from the packet
        var rawData = packet.getData();

        try {
            var json = new JSONObject(rawData);
            var x = json.getFloat("x");
            var y = json.getFloat("y");
            var type = json.getString("type");

            var objType = ObjectType.getTypeFromString(type);

            //if the obj type came back null, return false
            if(objType == null){
                return false;
            }

            //create a clone object based on the reconstructed data
            var cloneObject = ObjectDictionary.cloneObjectOfType(objType, new Position(x, y));

            //if clone object is null, return false
            if(cloneObject == null){
                return false;
            }

            //spawn the object
            this.gameManager.entityManager.getObjectManager().registerObjects(cloneObject);
            System.out.println("Got entity spawn packet");
        } catch (JSONException ignored) {}

        return true;
    }
}
