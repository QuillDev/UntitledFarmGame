package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Scythe extends Item{

    /**
     * Constructor for a new item
     * @param position of the item
     */
    public Scythe(Position position) {
        super(new Texture("textures/item_scythe.png"), position, ItemType.SCYTHE);
    }
}
