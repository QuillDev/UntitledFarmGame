package tech.quilldev.Engine.Map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import tech.quilldev.Engine.Entities.DynamicEntities.Dummy;
import tech.quilldev.Engine.Entities.DynamicEntities.DynamicEntity;
import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Map.Maps.TestMap;
import tech.quilldev.Engine.Map.Maps.TestMap2;
import tech.quilldev.Engine.Map.Tiles.QuillTiledMapTile;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.Engine.Rendering.Camera2D;
import tech.quilldev.Engine.Utilities.Position;

import java.util.ArrayList;

public class MapManager {

    //Map management
    private final ArrayList<Map> maps;
    private Map currentMap;

    //rendering
    private final OrthogonalTiledMapRenderer tiledMapRenderer;
    private Camera2D camera;

    /**
     * Create the map manager
     */
    public MapManager(){

        //Map list management
        this.maps = new ArrayList<>();
        registerMaps();

        //get the current map
        this.currentMap = maps.get(0);
        this.tiledMapRenderer = new OrthogonalTiledMapRenderer(currentMap.getMap());
    }

    public void render(){
        this.tiledMapRenderer.setView(this.camera);
        this.tiledMapRenderer.render();
    }
    /**
     * Setup the map manager before rendering
     * @param camera to setup
     */
    public void setup(Camera2D camera){
        this.camera = camera;
    }

    /**
     * Get the cells from the given layer
     * @param layer to get cells from
     * @return the cell array from that layer
     */
    public ArrayList<TiledMapTileLayer.Cell> getCellLayer(int layer){
        return this.currentMap.getCellLayer(layer);
    }

    /**
     * Get all tiles on the given layer
     * @param layer the layer to get tiles from
     * @return the tile list
     */
    public ArrayList<QuillTiledMapTile> getTileLayer(int layer){
        var cells = this.getCellLayer(layer);

        //create an arraylist to store the tiles in
        ArrayList<QuillTiledMapTile> tiles = new ArrayList<>();

        //iterate through the cells
        for(int index = 0; index < cells.size(); index++){

            var cell = cells.get(index);

            //get that cell's tile
            var tile = cell.getTile();

            //if there's no tile, continue
            if(tile == null){
                continue;
            }

            //add the tile to the tile list
            tiles.add(new QuillTiledMapTile(cell, index));
        }

        return tiles;
    }

    /**
     * Get the tiles of the given type
     * @param layer to get tiles from
     * @param tileType to get tiles from
     * @return the tiles of that type
     */
    public ArrayList<QuillTiledMapTile> getTilesOfType(int layer, TileType tileType){

        //get the tiles from the given layer
        var tiles = getTileLayer(layer);

        //list for storing the sorted tiles
        var tilesOfType = new ArrayList<QuillTiledMapTile>();

        //for each tile in the tile list
        for(var tile : tiles){

            //if the value of the tile and the id are the same add it
            if(tileType.getValue() == tile.getTile().getId()){
                tilesOfType.add(tile);
            }
        }

        return tilesOfType;
    }

    /**
     * Get the cells from layer 0
     * @return the cells from layer 0
     */
    public ArrayList<TiledMapTileLayer.Cell> getCellLayer(){
        return this.getCellLayer(0);
    }

    /**
     * Get the tile at the given position on the current map
     * @param layer to get the tile from
     * @param position of th tile
     * @return the tile at that position
     */
    public TiledMapTile getTileAtPosition(int layer, Position position){
        return this.currentMap.getTileAtPosition(layer, position);
    }

    /**
     * Get the tile at the given position assuming the base layer
     * @param position to get the tile at
     * @return the tile at that position
     */
    public TiledMapTile getTileAtPosition(Position position){
        return getTileAtPosition(0, position);
    }

    /**
     * Set the current map to that map at that index
     * @param mapIndex to set the map to
     */
    public void setMap(int mapIndex){
        var map = maps.get(mapIndex);

        if(map != null){
            this.currentMap = map;
            this.tiledMapRenderer.setMap(currentMap.getMap());
        }
    }

    /**
     * Get the current map
     * @return the current map
     */
    public Map getCurrentMap() {
        return this.currentMap;
    }

    /**
     * Check whether the tile at the given position is the entered tile type
     * @param position of the tile to check
     * @param tileType to check equality with
     * @param layer to check on
     * @return whether the tile at that layer is the given type
     */
    public boolean tileAtPositionIsType(Position position, TileType tileType, int layer){

        //ger the tile at the given position
        var tile = getTileAtPosition(layer, position);

        //if the tile is null, return false
        if(tile == null){
            return false;
        }

        //return whether the tile at the given index has the same value as the tile type
        return tile.getId() == tileType.getValue();
    }

    /**
     * Checks whether the tile at the given position is the entered tile type assuming the layer is 0
     * @param position the position to check at
     * @param tileType the tile type to check equality with
     * @return whether the tile at that positon is the given type
     */
    public boolean tileAtPositionIsType(Position position, TileType tileType){
        return tileAtPositionIsType(position, tileType, 0);
    }

    /**
     * Get the cell at the given position
     * @param layer the layer to check
     * @param position position to get from
     * @return the cell at that position
     */
    public TiledMapTileLayer.Cell getCellAtPosition(int layer, Position position){
        return this.currentMap.getCellAtPosition(layer, position);
    }

    /**
     * Get the cell at the given position assuming the layer is 0
     * @param position the position to get the cell from
     * @return the cell
     */
    public TiledMapTileLayer.Cell getCellAtPosition(Position position){
        return getCellAtPosition(0, position);
    }

    /**
     * Change the tile at the given position the tile type
     * @param position the position
     * @param tileType the tile type
     * @return whether the tile changed or not
     */
    public boolean changeTileToTileType(Position position, int layer, TileType tileType){
        return changeTileToTileType(getCellAtPosition(layer, position), tileType);
    }

    /**
     * Change the tile to the tile type at the given index
     * @param index to return at
     * @param layer to change at
     * @param tileType to change to
     * @return whether the tile changed
     */
    public boolean changeTileToTileType(int index, int layer, TileType tileType){
        return changeTileToTileType(getCellLayer(layer).get(index), tileType);
    }

    public boolean changeTileToTileType(TiledMapTileLayer.Cell cell, TileType tileType){
        var tileToChangeTo = this.currentMap.getTileset().getTile(tileType.getValue());

        //if either the player tile or the tile to change to are null return false;
        if(cell == null || tileToChangeTo == null) {
            return false;
        }

        //if it's legit set the tile
        cell.setTile(tileToChangeTo);

        return true;
    }

    /**
     * Change the tile at the given position to the tile type assuing layer 0
     * @param position the position of the tile
     * @param tileType the tile type
     * @return whether it changed
     */
    public boolean changeTileToTileType(Position position, TileType tileType){
        return changeTileToTileType(position, 0, tileType);
    }

    /**
     * Register and maps we want to load in the map manager
     */
    private void registerMaps(){
        this.maps.add(new TestMap());
        this.maps.add(new TestMap2());
    }

    /**
     * Get the entity colliders for this map
     * @return the colliders for this map
     */
    public ArrayList<EntityCollider> getEntityColliders(){
        return this.currentMap.entityColliders();
    }

    /**
     * Return the calculated position from the tile index
     * @param tileIndex to get the position from
     * @return the position
     */
    public Position cellIndexToPosition(int tileIndex){
        return getCurrentMap().getPositionFromCellIndex(tileIndex);
    }

    /**
     * Get the map list
     * @return the map list
     */
    public ArrayList<Map> getMaps() {
        return maps;
    }
}
