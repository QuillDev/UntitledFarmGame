package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.DynamicEntities.DynamicEntity;
import tech.quilldev.Engine.Utilities.Position;

public class Item extends DynamicEntity {

    //the type of the item
    private final ItemType itemType;
    public int stackCount;

    //set the max stack size of the object
    public int maxStackSize = 99;
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
     * add the given items stack count to this items stack count
     * @param item to merge with
     */
    public void mergeStacks(Item item){
        this.stackCount += item.stackCount;

        System.out.println(item.stackCount);
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
        if(this.itemType == null){
            return false;
        }
        return itemType.equals(testType);
    }
}
