package tech.quilldev.Engine.Map;

import com.badlogic.gdx.graphics.Texture;
import tech.quilldev.Engine.Entities.StaticEntities.StaticEntity;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.Engine.Utilities.Position;

public class Tile extends StaticEntity {

    //the type of tile
    public TileType tileType;

    public Tile(Texture texture, TileType tileType){
        super(texture, new Position(0, 0));
        this.tileType = tileType;
    }

    public Tile(Texture texture, Position position, TileType tileType){
        super(texture, position);
        this.tileType = tileType;
    }

    public Tile(Tile tile, Position position) {
        super(tile.getTexture(), position);
        this.tileType = tile.tileType;
    }
}
