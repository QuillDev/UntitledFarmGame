package tech.quilldev.Engine.Entities.DynamicEntities;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

import java.util.UUID;

public class MultiplayerPlayer extends DynamicEntity {

    /**
     * Create a new dynamic entity with the specified position and texture
     * @param position to create the entity with
     */
    public MultiplayerPlayer(UUID uuid, Position position) {
        super(new Texture("textures/character.png"), position);
        this.uuid = uuid;
    }
}
