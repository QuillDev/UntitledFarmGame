package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Seeds;
import tech.quilldev.Engine.Utilities.Position;

public class TallGrassObject extends GameObject {

    /**
     * Create a new tall grass game object at the given position
     * @param position to create the object at
     */
    public TallGrassObject( Position position) {
        super(new Texture("textures/grass.png"), position, ObjectType.TALL_GRASS);
    }

    /**
     * Create a tall grass object with a default position
     */
    public TallGrassObject(){
        super(new Texture("textures/grass.png"), new Position(), ObjectType.TALL_GRASS);
    }
    @Override
    public void registerDrops() {
        addDrops(
                new Seeds(this.getPosition())
        );
    }
}
