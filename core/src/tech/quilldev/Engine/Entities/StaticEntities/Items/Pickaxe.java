package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Pickaxe extends Item {

    /**
     * Constructor for a new item
     * @param position of the item
     */
    public Pickaxe(Position position) {
        super(new Texture("textures/item_pickaxe.png"), position, ItemType.PICKAXE);
    }

    /**
     * Constructor for a new item
     */
    public Pickaxe() {
        super(new Texture("textures/item_pickaxe.png"), new Position(), ItemType.PICKAXE);
    }
}
