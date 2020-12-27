package tech.quilldev.Engine.Network.Packets;

import org.json.JSONObject;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Network.Protocol;

import java.util.UUID;

public class EntityMovePacket extends Packet{

    public EntityMovePacket(Entity entity){
        super(Protocol.ENTITY_MOVE);

        var uuid = entity.getUuid();
        var x = entity.getPosition().getX();
        var y = entity.getPosition().getY();

        //create the data
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("uuid", uuid);
        jsonObject.put("x", x);
        jsonObject.put("y", y);

        //set this packets data
        this.setData(jsonObject.toString());
    }
}
