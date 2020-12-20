package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Item;
import tech.quilldev.Engine.Entities.StaticEntities.StaticEntity;
import tech.quilldev.Engine.Utilities.Position;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class GameObject extends StaticEntity {

    public ObjectType objectType;
    private final ArrayList<Item> drops;
    protected boolean passable = true;

    /**
     * Vanilla game object constructor
     * @param texture to assign to object
     * @param position to assign to object
     * @param objectType for identifying the type of object
     */
    public GameObject(Texture texture, Position position, ObjectType objectType) {
        super(texture, position);

        //setup the object
        this.objectType = objectType;
        this.drops = new ArrayList<>();
        registerDrops();
    }

    /**
     * Constructor for cloning objects
     * @param object to clone
     * @param position to move object to
     */
    public GameObject(GameObject object, Position position){
        super(object.getTexture(), position);
        this.drops = new ArrayList<>();
        this.objectType = object.objectType;
    }

    public abstract void registerDrops();

    /**
     * Add all of the items given as drops
     * @param items to add as drops
     */
    public void addDrops(Item... items){
        this.drops.addAll(Arrays.asList(items));
    }

    /**
     * add all of the given items as drops
     * @param items to add
     */
    public void addDrops(ArrayList<Item> items){
        this.drops.addAll(items);
    }

    /**
     * Get the drops of the object
     * @return the drops
     */
    public ArrayList<Item> getDrops() {
        return drops;
    }

    /**
     * Return whether this is passable
     * @return whether it's passable or not
     */
    public boolean isPassable(){
        return this.passable;
    }
}
