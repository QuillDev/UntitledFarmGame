package tech.quilldev.Engine.Entities.DynamicEntities;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Utilities.Position;

public class DynamicEntity extends Entity {

    public DynamicEntity(Texture texture, Position position){
        super(texture, position);
    }

    public DynamicEntity(Entity entity){
        super(entity.getTexture(), new Position(entity.getPosition()));
    }
    /**
     * Update the position
     */
    public void update(){
        this.getCollider().updatePosition(this.getPosition());
    }
}
