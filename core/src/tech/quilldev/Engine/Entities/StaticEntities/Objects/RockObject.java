package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.StaticEntities.Items.Rock;
import tech.quilldev.Engine.Utilities.Position;

public class RockObject extends GameObject{

    public RockObject() {
        super(new Texture("textures/object_rock.png"), new Position(), ObjectType.ROCK);
        this.passable = false;
    }

    @Override
    public void registerDrops() {
        var rok = new Rock();
        rok.setStackCount(10);
        addDrops(
                rok
        );
    }
}
