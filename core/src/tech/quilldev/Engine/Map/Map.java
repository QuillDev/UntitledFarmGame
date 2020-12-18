package tech.quilldev.Engine.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.*;
import com.badlogic.gdx.utils.Array;
import tech.quilldev.Engine.Entities.AreaCollider;
import tech.quilldev.Engine.Entities.Entity;
import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.*;

public abstract class Map {

    //the tiled map we're working with
    private final TiledMap map;
    private final ArrayList<ArrayList<Cell>> cellArray;
    private final MapProperties mapProperties;
    private final AreaCollider collider;
    private final ArrayList<EntityCollider> colliders;

    public Map(String mapName){

        //create the map and it's properties
        this.map = new TmxMapLoader().load(mapName);
        this.mapProperties = new MapProperties(this);

        //generate the cell array
        this.cellArray = populateCellArray();
        //the map colliders
        this.collider = new AreaCollider();
        this.colliders = new ArrayList<>();
        generateColliders();
    }

    /**
     * Get the cell at the given position and layer index
     * @param layerIndex the layer index to get the tile at
     * @param position the position of the tile;
     * @return the cell
     */
    public Cell getCellAtPosition(int layerIndex, Position position){
        var cellLayer = getCellLayer(layerIndex);

        //if the cell layer is null, return null
        if(cellLayer == null) {
            return null;
        }

        var index = positionToTileIndex(position);

        //if we're above the cell index
        if(index == null){
            return null;
        }

        //get the cell at the given position
        return cellLayer.get(index);
    }

    /**
     * Cell the cell array at the given layer
     * @param layer to get
     * @return the layer if it exists
     */
    public ArrayList<Cell> getCellLayer(int layer){
        return cellArray.get(layer);
    }

    /**
     * Get the tile at the given position and layer index
     * @param layerIndex the layer index to check
     * @param position to check at
     * @return the tile at that position
     */
    public TiledMapTile getTileAtPosition(int layerIndex, Position position){
        var cell = getCellAtPosition(layerIndex, position);

        //if the cel lis null return null
        if(cell == null){
            return null;
        }

        return cell.getTile();
    }

    /**
     * Get the tiled map
     * @return return the map
     */
    public TiledMap getMap() {
        return map;
    }

    /**
     * Get the tile set for the given index
     * @param index of the tileset to get
     * @return the tile set at that index
     */
    public TiledMapTileSet getTileset(int index){
        return this.map.getTileSets().getTileSet(index);
    }

    /**
     * Get the base layer tile set
     * @return the base layer tile set
     */
    public TiledMapTileSet getTileset(){
        return getTileset(0);
    }

    /**
     * Get the layer at the given index
     * @param layer index of the layer
     * @return the layer you queried for
     */
    public TiledMapTileLayer getLayer(int layer){
        return (TiledMapTileLayer) this.map.getLayers().get(layer);
    }

    //get the base layer
    public TiledMapTileLayer getLayer(){
        return getLayer(0);
    }

    /**
     * Populate this maps cell array
     * @return it's cell array
     */
    private ArrayList<ArrayList<Cell>> populateCellArray(){

        //create the temp cell array
        final ArrayList<ArrayList<Cell>> cellArray = new ArrayList<>();

        //for each layer in the  map get all of the cells and store them here
        for(MapLayer layer : this.map.getLayers()){

            //get the workspace (layer)
            var workSpace = ( (TiledMapTileLayer) layer );

            //create the cell list
            ArrayList<TiledMapTileLayer.Cell> cellList = new ArrayList<>();

            //Iterate through the x and y of the workspace
            for(var x = 0; x < workSpace.getWidth(); x++){
                for(var y = 0; y < workSpace.getHeight(); y++){

                    //get the cell to add
                    var cellToAdd = workSpace.getCell(x, y);

                    //if the cell to add is null, continue
                    if(cellToAdd == null){
                        continue;
                    }

                    cellList.add(cellToAdd);
                }
            }

            //add this cell list to the cell array
            cellArray.add(cellList);
        }

        return cellArray;
    }

    /**
     * Convert the position to a cell array index
     * @param position to get
     * @return the cell index from that position
     */
    private Integer positionToTileIndex(Position position){

        //calculate the x and y of the tile
        var tileX = Math.floor(position.getX() / mapProperties.tileWidth);
        var tileY = Math.floor(position.getY() / mapProperties.tileHeight);

        if(tileX > mapProperties.width-1 || tileX < 0 || tileY > mapProperties.height-1 || tileY < 0){
            return null;
        }

        return (int) (tileX * mapProperties.width + tileY);
    }

    /**
     * Get the map colliders
     * @return the colliders
     */
    public AreaCollider getCollider() {
        return collider;
    }

    public ArrayList<EntityCollider> entityColliders(){
        return this.colliders;
    }

    /**
     * Generate the colliders
     */
    private void generateColliders(){

        //Generate edge colliders
        var top = new Rectangle(0, mapProperties.heightPixels -1, mapProperties.widthPixels, 2);
        var bottom = new Rectangle(0, -1, mapProperties.widthPixels, 1);
        var left = new Rectangle(-1, 0, 1, mapProperties.heightPixels);
        var right = new Rectangle(mapProperties.widthPixels, 0, 1, mapProperties.heightPixels);

        this.collider.addAll(new ArrayList<>(Arrays.asList(top, bottom, left, right)));

        //Generate colliders from wall tiles
        for(int index = 0; index < getCellLayer(0).size(); index++){

            //get the cell from the cell layer
            var cell = getCellLayer(0).get(index);

            //if that cell is null continue;
            if(cell == null){
                continue;
            }

            var tile = cell.getTile();

            //if that cell's tile is null continue
            if(tile == null){
                continue;
            }

            //if the tile doesn't have the wall key continue
            if(!tile.getProperties().containsKey("wall")){
                continue;
            }

            var pos = getPositionFromCellIndex(index);
            this.colliders.add(new EntityCollider(pos[0], pos[1], MathConstants.WORLD_UNIT, MathConstants.WORLD_UNIT));
        }

        //combine all of the colliders and add them to the main collider
        for(EntityCollider collider: colliders){
            this.collider.add(collider);
        }
    }

    private int[] getPositionFromCellIndex(int index){
        return new int[] {
                (index / mapProperties.width) * MathConstants.WORLD_UNIT,
                (index % mapProperties.width) * MathConstants.WORLD_UNIT
        };
    }
}
