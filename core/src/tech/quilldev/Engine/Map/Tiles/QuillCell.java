package tech.quilldev.Engine.Map.Tiles;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import tech.quilldev.Engine.Utilities.Position;

public class QuillCell {

    private final Position position;
    private final TiledMapTileLayer.Cell cell;

    public QuillCell(TiledMapTileLayer.Cell cell,  Position position){
        this.cell = cell;
        this.position = position;
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
