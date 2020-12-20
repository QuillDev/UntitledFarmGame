package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Entities.EntityCollider;

import java.util.ArrayList;
import java.util.Arrays;

public class ObjectManager {

    //the item list
    private final ArrayList<GameObject> gameObjects;

    //Creates the item manager
    public ObjectManager(){
        this.gameObjects = new ArrayList<>();
    }

    /**
     * Get the first entity in the item list we're colliding with
     * @param entity the entity to check collisions with
     * @return the entity we're colliding with
     */
    public GameObject getFirstCollision(Entity entity){
        for(var gameObject : gameObjects){
            if(gameObject.collidingWith(entity)){
                return gameObject;
            }
        }

        return null;
    }

    /**
     * get the first collision with the entity collider
     * @param entityCollider the collider to check
     * @return the entity collider
     */
    public GameObject getFirstCollision(EntityCollider entityCollider){
        for(var gameObject : gameObjects){
            if(entityCollider.collidingWith(gameObject)){
                return gameObject;
            }
        }

        return null;
    }

    /**
     * Render all of the items to the sprite batch
     * @param batch the batch to render to
     */
    public void render(Batch batch){
        for(var gameObject: gameObjects){
            gameObject.render(batch);
        }
    }

    /**
     * Register items to the item manager
     * @param gameObjects to remove
     */
    public void registerObjects(GameObject... gameObjects){
        //add all items to the item list
        this.gameObjects.addAll(Arrays.asList(gameObjects));
    }

    /**
     * Get the item array list
     * @return the items list
     */
    public ArrayList<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Remove all of the items given
     * @param gameObjects to remove
     */
    public void removeObjects(GameObject... gameObjects){
        this.gameObjects.removeAll(Arrays.asList(gameObjects));
    }
}
