package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.DynamicEntities.DynamicEntity;
import tech.quilldev.Engine.Utilities.Position;

public class Item extends DynamicEntity {

    //the type of the item
    private final ItemType itemType;
    private int stackCount;

    /**
     * Constructor for a new item
     * @param texture of the item
     * @param position of the item
     * @param itemType of the item
     */
    public Item(Texture texture, Position position, ItemType itemType) {
        super(texture, position);
        this.itemType = itemType;
        this.stackCount = 1;
    }

    /**
     * Get the stack count
     * @return the stack count
     */
    public int getStackCount() {
        return stackCount;
    }

    /**
     * Set the stack count to the given value
     * @param stackCount the amount to set to
     */
    public void setStackCount(int stackCount) {
        this.stackCount = stackCount;
    }

    /**
     * Get the type of the item
     * @return the item type
     */
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Check for item equivalency (whether items are equal)
     * @param testType the type of the second item
     * @return whether the types are the same
     */
    public boolean isType(ItemType testType){
        return itemType.equals(testType);
    }
}
