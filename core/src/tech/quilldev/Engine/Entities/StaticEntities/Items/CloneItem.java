package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class CloneItem extends Item{
    /**
     * Constructor for a new item
     * @param item item to clone
     * @param position of the item
     */
    public CloneItem(Item item, Position position) {
        super(item.getTexture(), new Position(position), item.getItemType());
    }
}
