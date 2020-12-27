package tech.quilldev.Engine.Entities.StaticEntities.Items;

import tech.quilldev.Engine.Utilities.Position;

public class CloneItem extends Item{
    /**
     * Constructor for a new item
     * @param item item to clone
     * @param position of the item
     */
    public CloneItem(Item item, Position position) {
        super(item.getTexture(), new Position(position), item.getItemType());
        this.stackCount = item.getStackCount();
    }

    public CloneItem(Item item, int stackCount){
        super(item.getTexture(), new Position(), item.getItemType());
        this.setStackCount(stackCount);
    }

    /**
     * Create a clone item from the given item
     * @param item to clone
     */
    public CloneItem(Item item){
        super(item.getTexture(), new Position(item.getPosition()), item.getItemType());
    }


}
