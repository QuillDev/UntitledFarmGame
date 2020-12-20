package tech.quilldev.Engine.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemManager;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectManager;
import tech.quilldev.Engine.Entities.StaticEntities.StaticEntity;

import java.util.ArrayList;

public class EntityManager {

    private final Player player;
    private final ItemManager itemManager;
    private final ObjectManager objectManager;

    public EntityManager(){
        this.player = new Player();
        this.itemManager = new ItemManager();
        this.objectManager = new ObjectManager();
    }

    /**
     * Update the states of all entities
     */
    public void update(){
        this.player.update();
    }

    /**
     * Render all of the entities in our entityList
     * @param batch the batch to render to
     */
    public void render(Batch batch){

        //render all items in the item manager
        this.itemManager.render(batch);

        //render the objects in the object manager
        this.objectManager.render(batch);

        //render the player last so they're above any items
        this.player.render(batch);
    }

    /**
     * Get the player from the entityManager
     * @return the player
     */
    public Player getPlayer(){
        return this.player;
    }

    /**
     * Get all of the worlds entities
     * @return all of the entities
     */
    public ArrayList<Entity> getAllEntities(){
        var entityList = new ArrayList<Entity>(this.itemManager.getItems());
        entityList.add(player);
        entityList.addAll(objectManager.getGameObjects());

        return entityList;
    }

    /**
     * Get the item manager
     * @return the item manager
     */
    public ItemManager getItemManager() {
        return itemManager;
    }

    /**
     * Get the object manager
     * @return the object manager
     */
    public ObjectManager getObjectManager() {
        return objectManager;
    }
}
