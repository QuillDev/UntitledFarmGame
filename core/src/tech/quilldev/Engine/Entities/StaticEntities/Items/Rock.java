package tech.quilldev.Engine.Entities.StaticEntities.Items;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Utilities.Position;

public class Rock extends Item{

    /**
     * Constructor for a new item
     * @param position of the item
     */
    public Rock(Position position) {
        super(new Texture("textures/item_rock.png"), position, ItemType.ROCK);
    }

    public Rock() {
        super(new Texture("textures/item_rock.png"), new Position(), ItemType.ROCK);
    }
}
