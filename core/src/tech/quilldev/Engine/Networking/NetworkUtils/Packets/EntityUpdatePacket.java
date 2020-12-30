package tech.quilldev.Engine.Networking.NetworkUtils.Packets;

import org.json.JSONObject;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Networking.NetworkUtils.Packet;
import tech.quilldev.Engine.Networking.NetworkUtils.Protocol;

public class EntityUpdatePacket extends Packet {

    public EntityUpdatePacket(Entity entity) {
        super();
        this.protocol = Protocol.ENTITY_UPDATE;
        genData(entity);
    }

    /**
     * Generate the data for the entity
     * @param entity to generate data for
     */
    public void genData(Entity entity){
        var json = new JSONObject();
        json.put("uuid", entity.getUuid());
        json.put("x", entity.getPosition().getX());
        json.put("y", entity.getPosition().getY());

        this.setData(json.toString());
    }
}
