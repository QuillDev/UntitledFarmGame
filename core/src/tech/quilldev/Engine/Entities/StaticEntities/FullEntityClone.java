package tech.quilldev.Engine.Entities.StaticEntities;

import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Utilities.Position;

public class FullEntityClone extends Entity {

    public FullEntityClone(Entity entity){
        super(entity.getTexture(), new Position(entity.getPosition()));
        this.uuid = entity.uuid;
    }
}
