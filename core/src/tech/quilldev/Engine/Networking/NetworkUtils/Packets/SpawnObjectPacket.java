package tech.quilldev.Engine.Networking.NetworkUtils.Packets;

import org.json.JSONObject;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.GameObject;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Networking.NetworkUtils.Protocol;

public class SpawnObjectPacket extends Packet {

    public SpawnObjectPacket(GameObject object) {
        super(Protocol.SPAWN_OBJECT, "");
        genData(object);
    }

    /**
     * Generate the packets data from the object
     * @param object to generate based off of
     */
    public void genData(GameObject object){
        var json = new JSONObject();
        json.put("x", object.getPosition().getX());
        json.put("y", object.getPosition().getY());
        json.put("type", object.objectType);

        this.setData(json.toString());
    }
}
