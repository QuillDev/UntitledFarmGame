package tech.quilldev.Engine.Entities;

import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.Engine.Entities.DynamicEntities.MultiplayerPlayer;
import tech.quilldev.Engine.Entities.DynamicEntities.Player;
import tech.quilldev.Engine.Entities.StaticEntities.Items.ItemManager;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.GameObject;
import tech.quilldev.Engine.Entities.StaticEntities.Objects.ObjectManager;
import tech.quilldev.Engine.Utilities.Position;

import java.util.ArrayList;
import java.util.UUID;

public class EntityManager {

    private final Player player;
    private final ItemManager itemManager;
    private final ObjectManager objectManager;
    private final ArrayList<Entity> multiplayerEntities;

    public EntityManager(){
        this.player = new Player();
        this.itemManager = new ItemManager();
        this.objectManager = new ObjectManager();
        this.multiplayerEntities = new ArrayList<>();
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

        //TODO Multiplayer manager?
        for(var entity : multiplayerEntities){
            entity.render(batch);
        }

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
        var entityList = new ArrayList<Entity>();
        entityList.add(player);
        entityList.addAll(itemManager.getItems());
        entityList.addAll(objectManager.getGameObjects());
        entityList.addAll(multiplayerEntities);

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
     * Register the multiplayer to the enttiy list
     * @param entity to register
     */
    public void registerPlayer(Entity entity){
        this.multiplayerEntities.add(entity);
    }

    /**
     * Get the object manager
     * @return the object manager
     */
    public ObjectManager getObjectManager() {
        return objectManager;
    }

    /**
     * Get the game objects
     * @return the game object list
     */
    public ArrayList<GameObject> getGameObjects(){
        return this.objectManager.getGameObjects();
    }

    /**
     * Get all object collisions with the given entity
     * @return all collisions with the given entity
     */
    public ArrayList<GameObject> getAllObjectCollisions(Entity entity){
        var list = new ArrayList<GameObject>();

        //for each object in the object list
        for(var object : getGameObjects()){

            //if the object is colliding with the player add it
            if(object.collidingWith(entity)){
                list.add(object);
            }
        }

        return list;
    }

    /**
     * Check if we have that multiplayer entiteis uuid
     * @param uuid to check for
     * @return whether we already have it
     */
    public Entity getEntityWithUUID(UUID uuid){

        //iterate through all of the entities
        for(Entity entity : getAllEntities()){
            if(entity.uuid.equals(uuid)){
                return entity;
            }
        }

        return null;
    }

}
