package tech.quilldev.Engine.Entities.DynamicEntities;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Utilities.Position;

public class DynamicEntity extends Entity {

    /**
     * Create a new dynamic entity with the specified position and texture
     * @param texture to create the entity with
     * @param position to create the entity with
     */
    public DynamicEntity(Texture texture, Position position){
        super(texture, position);
    }

    /**
     * Update the entity
     */
    public void update(){
        this.getCollider().updatePosition(this.getPosition());
    }

    /**
     * Set the position of the entity
     * @param position to set it to
     */
    public void setPosition(Position position){
        super.setPosition(position);
        this.update();
    }

    /**
     * Add the given position to the position
     * @param position to add
     */
    public void addPosition(Position position){
        this.getPosition().addPosition(position);
        this.update();
    }

}
