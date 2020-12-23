package tech.quilldev.Engine.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Map.Maps.TestMap;
import tech.quilldev.Engine.Map.Tiles.QuillCell;
import tech.quilldev.Engine.Map.Tiles.QuillTiledMapTile;
import tech.quilldev.Engine.Map.Tiles.TileType;
import tech.quilldev.Engine.Rendering.Camera2D;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

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

        this.changeTileToTileType(new Position(), TileType.CARROT);
        var rockPos = new Position(24 * MathConstants.WORLD_UNIT, 0);
        this.changeTileToTileType(rockPos, TileType.CARROT);
        System.out.println(this.tileAtPositionIsType(rockPos, TileType.ROCK));
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
     * Get the cells of the current tiled map
     * @return the maps cells
     */
    public ArrayList<QuillCell> getCells(){
        return this.getCurrentMap().getCells();
    }

    /**
     * Get all tiles on the given layer
     * @return the tile list
     */
    public ArrayList<QuillTiledMapTile> getTileLayer(){

        var cells = getCells();

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
     * @param tileType to get tiles from
     * @return the tiles of that type
     */
    public ArrayList<QuillTiledMapTile> getTilesOfType(TileType tileType){

        //get the tiles from the given layer
        var tiles = getTileLayer();

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
     * Get the tile at the given position on the current map
     * @param position of th tile
     * @return the tile at that position
     */
    public TiledMapTile getTileAtPosition(Position position){
        return this.currentMap.getTileAtPosition(position);
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
     * Get the tile layer from the current map
     * @param name of the tile layer to get
     * @return the layer
     */
    public MapLayer getLayerByName(String name){
        return this.currentMap.getMap().getLayers().get(name);
    }

    /**
     * Get the maps objects
     * @return the maps objects
     */
    public MapObjects getObjects(String name){
        //get the object layer
        var objLayer = this.getLayerByName(name);

        //if the obj layer is null, return a default position
        if(objLayer == null){
            return null;
        }

        return objLayer.getObjects();
    }

    /**
     * Get the spawn position on the map
     * @return the maps spawn position
     */
    public Position getSpawnPosition() {

        //get the objects layer
        var objects = this.getObjects("Objects");

        //if the objects are null, return a new position
        if (objects == null || objects.getCount() == 0){
            return new Position();
        }

        //get the spawn object
        var spawnObject = objects.get("spawn");

        //if the spawn object is null, return a default position
        if(spawnObject == null){
            return new Position();
        }

        //get the properties
        var properties = spawnObject.getProperties();

        //if properties is null, return a default position
        if(properties == null || !properties.containsKey("x") || !properties.containsKey("y")) {
            return new Position();
        }

        var x = Float.parseFloat(properties.get("x").toString());
        var y = Float.parseFloat(properties.get("y").toString());

        return new Position(x, y);
    }

    /**
     * Check whether the tile at the given position is the entered tile type
     * @param position of the tile to check
     * @param tileType to check equality with
     * @return whether the tile at that layer is the given type
     */
    public boolean tileAtPositionIsType(Position position, TileType tileType){

        //ger the tile at the given position
        var tile = getTileAtPosition(position);

        //if the tile is null, return false
        if(tile == null){
            return false;
        }

        //return whether the tile at the given index has the same value as the tile type
        return tile.getId() == tileType.getValue();
    }

    /**
     * Get the cell at the given position
     * @param position position to get from
     * @return the cell at that position
     */
    public QuillCell getCellAtPosition(Position position){
        return this.currentMap.getCellAtPosition(position);
    }

    /**
     * Change the tile at the given position the tile type
     * @param position the position
     * @param tileType the tile type
     * @return whether the tile changed or not
     */
    public boolean changeTileToTileType(Position position, TileType tileType){
        return changeTileToTileType(getCellAtPosition(position), tileType);
    }

    /**
     * Change the tile of the given cell to the specified tile tpye
     * @param cell to change the tile of
     * @param tileType to change to
     * @return whether the tile changed or not.
     */
    public boolean changeTileToTileType(QuillCell cell, TileType tileType){
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
     * Register and maps we want to load in the map manager
     */
    private void registerMaps(){
        this.maps.add(new TestMap());
    }

    /**
     * Get the entity colliders for this map
     * @return the colliders for this map
     */
    public ArrayList<EntityCollider> getEntityColliders(){
        return this.currentMap.getColliders();
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
