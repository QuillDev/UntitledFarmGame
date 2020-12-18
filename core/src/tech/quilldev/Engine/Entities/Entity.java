package tech.quilldev.Engine.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Utilities.Position;

public abstract class Entity {

    //texture and pos of the entity
    private Position position;
    private Texture texture;
    private final EntityCollider collider;

    /**
     * Constructor for new entities
     * @param texture the texture of the entity
     * @param position the position of the entity
     */
    public Entity(Texture texture, Position position){
        this.texture = texture;
        this.position = position;
        this.collider = new EntityCollider(this);
    }

    /**
     * Constructor for new entities
     * @param texture the texture of the entity
     * @param x coordinate of the position
     * @param y coordinate of the position
     */
    public Entity(Texture texture, float x, float y){
        this.texture = texture;
        this.position = new Position(x, y);
        this.collider = new EntityCollider(this);
    }

    /**
     * Render this entity to the batch
     * @param batch the batch to render to
     */
    public void render(Batch batch) {
        batch.draw(this.texture, position.getX(), position.getY());
    }

    /**
     * Checking if the entity is colliding with another entity
     * @param entity the entity to check collisions with
     * @return whether the entities are colliding
     */
    public boolean colliding(Entity entity){
        return this.collider.collidingWith(entity);
    }

    /**
     * Set this texture to the one given
     * @param texture to set it to
     */
    public void setTexture(Texture texture){
        this.texture = texture;
    }

    // Get this entities texture
    public Texture getTexture() {
        return texture;
    }
    /**
     * Set the position to a new position
     * @param position to set it to
     */
    public void setPosition(Position position) {
        this.position = new Position(position.getX(), position.getY());
    }

    // Get the position of this entity
    public Position getPosition() {
        return position;
    }

    /**
     * Get the collider
     * @return this entities collider
     */
    public EntityCollider getCollider() {
        return collider;
    }
}
