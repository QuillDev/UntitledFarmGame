package tech.quilldev.Engine.Map.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class QuillTiledMapTile {

    private final TiledMapTile tile;
    private final int index;

    public QuillTiledMapTile(TiledMapTile tiledMapTile, int index){
        this.tile = tiledMapTile;
        this.index = index;
    }

    /**
     * Get the index of the tile
     * @return index of the tile
     */
    public int getIndex() {
        return index;
    }

    /**
     * Get the tile from this wrapped
     * @return the tile
     */
    public TiledMapTile getTile() {
        return tile;
    }
}
