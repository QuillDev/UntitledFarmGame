package tech.quilldev.Engine.Map.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;

public class QuillTiledMapTile {

    private final QuillCell cell;
    private final int index;

    public QuillTiledMapTile(QuillCell cell, int index){
        this.cell = cell;
        this.index = index;
    }

    /**
     * Get the cell this tile is from
     * @return the cell
     */
    public QuillCell getCell() {
        return cell;
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
        return cell.getTile();
    }
}
