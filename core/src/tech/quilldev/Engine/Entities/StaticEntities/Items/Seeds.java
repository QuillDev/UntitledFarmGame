package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Seeds extends Item {
    /**
     * Constructor for a new items
     * @param position of the item
     */
    public Seeds(Position position) {
        super(new Texture("textures/item_seeds.png"), position, ItemType.SEEDS);
    }

    /**
     * Constructor for a new items
     */
    public Seeds() {
        super(new Texture("textures/item_seeds.png"), new Position(), ItemType.SEEDS);
    }
}
