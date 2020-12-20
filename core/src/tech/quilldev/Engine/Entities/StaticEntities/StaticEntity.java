package tech.quilldev.Engine.Entities.StaticEntities;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Utilities.Position;

public abstract class StaticEntity extends Entity {

    public StaticEntity(Texture texture, Position position){
        super(texture, position);
    }
}
