package tech.quilldev.Engine.Entities.DynamicEntities;

import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Utilities.Position;

public class Dummy extends DynamicEntity {

    /**
     * Create a new dummy (Texture-less entity)
     * @param entity to base the dummy on
     */
    public Dummy(Entity entity){
        super(null, new Position(entity.getPosition()));
        this.collider = new EntityCollider(entity.getCollider());
    }
    //Make sure that if we accidentally call render it does nothing
    public void render(){}
}
