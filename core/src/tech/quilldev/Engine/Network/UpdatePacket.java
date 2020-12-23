package tech.quilldev.Engine.Network;


import org.json.JSONException;
import org.json.JSONObject;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;

import java.util.UUID;

public class UpdatePacket {

    private float playerX;
    private float playerY;
    private UUID uuid;

    public UpdatePacket(Player player){
        //set packet data
        this.playerX = player.getPosition().getX();
        this.playerY = player.getPosition().getY();
        this.uuid = player.getUuid();
    }

    public UpdatePacket(UUID uuid, float playerX, float playerY) {
        this.uuid = uuid;
        this.playerX = playerX;
        this.playerY = playerY;
    }

    /**
     * Try to build the update packet from a json string
     * @param jsonString packet string
     * @return the reconstructed update packet
     */
    public static UpdatePacket buildFromJsonString(String jsonString){
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            var uuid = UUID.fromString(jsonObject.get("uuid").toString());
            var playerX  = Float.parseFloat(jsonObject.get("x").toString());
            var playerY  = Float.parseFloat(jsonObject.get("y").toString());
            return new UpdatePacket(uuid, playerX, playerY);

        } catch (JSONException ignored) {}

        return null;
    }
    public float getPlayerX() {
        return playerX;
    }

    public float getPlayerY() {
        return playerY;
    }

    public UUID getUuid() {
        return uuid;
    }

    /**
     * Get this object as a json string
     * @return the json string of this object
     */
    public String getJson(){
        var json = new JSONObject();
        json.put("x", this.playerX);
        json.put("y", this.playerY);
        json.put("uuid", this.uuid.toString());
        return json.toString();
    }
}
