package tech.quilldev.Engine.Entities.StaticEntities.Items;

public class HeldItem extends Item {

    /**
     * Create a new held item
     * @param item to create from the held item
     */
    public HeldItem(Item item) {
        super(item.getTexture(), item.getPosition(), item.getItemType());
    }

    /**
     * Update the items collider
     */
    public void update(){
        this.getCollider().updatePosition(getPosition());
    }
}
