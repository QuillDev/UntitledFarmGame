package tech.quilldev.Engine.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.*;
import tech.quilldev.Engine.Entities.AreaCollider;
import tech.quilldev.Engine.Entities.EntityCollider;
import tech.quilldev.Engine.Map.Tiles.QuillCell;
import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

import java.util.ArrayList;

public abstract class Map {

    //the tiled map we're working with
    private final TiledMap map;
    private final ArrayList<QuillCell> cells;
    private final MapProperties mapProperties;
    private final AreaCollider collider;
    private final ArrayList<EntityCollider> colliders;

    public Map(String mapName){

        //create the map and it's properties
        this.map = new TmxMapLoader().load(mapName);
        this.mapProperties = new MapProperties(this);

        //generate the cell array
        this.cells = populateCellArray();
        //the map colliders
        this.collider = new AreaCollider();
        this.colliders = new ArrayList<>();
        generateColliders();
    }

    /**
     * Get the cell array for the current tiled map
     * @return the cell array for the map
     */
    public ArrayList<QuillCell> getCells() {
        return cells;
    }

    /**
     * Get the cell at the given position and layer index
     * @param position the position of the tile;
     * @return the cell
     */
    public QuillCell getCellAtPosition(Position position){

        var index = positionToTileIndex(position);

        //if we're above the cell index
        if(index == null){
            return null;
        }

        //get the cell at the given position
        return cells.get(index);
    }

    /**
     * Get the tile at the given position and layer index
     * @param position to check at
     * @return the tile at that position
     */
    public TiledMapTile getTileAtPosition(Position position){
        var cell = getCellAtPosition(position);

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
     * Get the layers from the map
     * @return the maps layers
     */
    public MapLayers getLayers(){
        return this.getMap().getLayers();
    }

    /**
     * Get the layer by the name
     * @param name of the layer to get
     * @return the layer with the given name if it exists
     */
    public MapLayer getLayerByName(String name){
        return this.getLayers().get(name);
    }

    /**
     * Populate this maps cell array
     * @return it's cell array
     */
    private ArrayList<QuillCell> populateCellArray(){

        //get the tiles layer
        var tileLayer = (TiledMapTileLayer) this.getLayerByName("Tiles");

        //create a cells array
        var cells = new ArrayList<QuillCell>();


        // LOAD THE MAP STARTING AT THE BOTTOM LEFT
        // (0, 4), (1, 4), (2, 4),  (3, 4),  (4, 4)
        // (0, 3), (1, 3), (2, 3),  (3, 3),  (4, 3)
        // (0, 2), (1, 2), (2, 2),  (3, 2),  (4, 2)
        // (0, 1), (1, 1), (2, 1),  (3, 1),  (4, 1)
        // (0, 0), (1, 0), (2, 0),  (3, 0),  (4, 0)
        // Example of what the map array would look like

        //get the cells from that layer
        for(int y = 0; y < mapProperties.height; y++){
            for(int x = 0; x < mapProperties.width; x++){

                //calculate the tile index
                var index = y * mapProperties.width + x;

                //get the cell
                var cell = tileLayer.getCell(x, y);
                var position = new Position(x * MathConstants.WORLD_UNIT, y * MathConstants.WORLD_UNIT);

                //add the cell to the cell array
                cells.add(new QuillCell(cell, position, index));
            }
        }
        return cells;
    }

    /**
     * Get the collider layer of the map
     * @return the maps collider layer
     */
    private MapLayer getColliderLayer(){
        return this.getLayerByName("Colliders");
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

        return (int) (tileY * mapProperties.width + tileX);
    }

    /**
     * Get the map colliders
     * @return the colliders
     */
    public AreaCollider getCollider() {
        return collider;
    }

    public ArrayList<EntityCollider> getColliders(){
        return this.colliders;
    }

    /**
     * Generate the colliders
     */
    private void generateColliders() {

        //Create colliders for impassable cells
        for(var cell : getCells()){
            var tile = cell.getTile();

            //if the tile is null, continue
            if(tile == null){
                continue;
            }

            //if the tile has the "wall" key generate a collider for it
            if(tile.getProperties().containsKey("wall")){

                //get things for constructing the colliders properties
                var pos = cell.getPosition();
                var texture = tile.getTextureRegion().getTexture();

                //create the collider
                var collider = new EntityCollider(pos.x, pos.y, texture.getWidth(), texture.getHeight());
                this.addCollider(collider);
            }

        }

        var colliderLayer = getColliderLayer();

        //if there's no collider layer return
        if(colliderLayer == null){
            return;
        }

        //get the objects of the collider layer
        var mapObjects = colliderLayer.getObjects();

        for(var obj : mapObjects){
            var properties = obj.getProperties();

            //get rectangle properties
            var xProp = properties.get("x");
            var yProp = properties.get("y");
            var widthProp = properties.get("width");
            var heightProp = properties.get("height");

            //if any of those properties are null, continue;
            if(xProp == null || yProp == null || widthProp == null || heightProp == null){
                continue;
            }

            //get the properties
            var x = Float.parseFloat(xProp.toString());
            var y = Float.parseFloat(yProp.toString());
            var width = Float.parseFloat(widthProp.toString());
            var height = Float.parseFloat(heightProp.toString());

            //create a collider from the properties
            var collider = new EntityCollider(x, y, width, height);
            this.addCollider(collider);
        }
    }

    /**
     * Add a collider to this maps collider list
     * @param collider to add
     */
    private void addCollider(EntityCollider collider){
        this.colliders.add(collider);
        this.collider.add(collider);
    }

    /**
     * Get the position from the given cell index
     * @param index of the cell
     * @return the cells position
     */
    public Position getPositionFromCellIndex(int index){
        var cell = this.getCells().get(index);

        //return that the position was null
        if(cell == null){
            return null;
        }

        return cell.getPosition();
    }
}
