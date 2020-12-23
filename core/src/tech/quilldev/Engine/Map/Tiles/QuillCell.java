package tech.quilldev.Engine.Map.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import tech.quilldev.Engine.Utilities.Position;

public class QuillCell {

    private final Position position;
    private final TiledMapTileLayer.Cell cell;
    private final int index;

    /**
     * Create a new cell by using a cell and a position
     * @param cell to hold the cell with
     * @param position of the cells BL corner
     */
    public QuillCell(TiledMapTileLayer.Cell cell,  Position position, int index){
        this.cell = cell;
        this.position = position;
        this.index = index;
    }

    /**
     * Get the currnet cells index
     * @return the index of the cell
     */
    public int getIndex(){
        return this.index;
    }

    /**
     * Set the tile of the cell to the given one
     * @param tile to set to
     */
    public void setTile(TiledMapTile tile){
        this.cell.setTile(tile);
    }

    /**
     * Get the tile
     * @return the cell tile
     */
    public TiledMapTile getTile(){
        return this.cell.getTile();
    }

    /**
     * Get the position of the cell
     * @return the position of the cell
     */
    public Position getPosition() {
        return position;
    }
}
