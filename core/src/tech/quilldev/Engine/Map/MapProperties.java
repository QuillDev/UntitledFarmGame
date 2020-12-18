package tech.quilldev.Engine.Map;

import tech.quilldev.MathConstants;

public class MapProperties {

    //map properties

    //world size
    public int height;
    public int width;

    //tile sizes
    public int tileWidth;
    public int tileHeight;

    //World unit scaled properties

    //tiles in world units
    public int tileHeightWorldUnits;
    public int tileWidthWorldUnits;

    //map in world units
    public int heightWorldUnits;
    public int widthWorldUnits;

    public int heightPixels;
    public int widthPixels;

    public MapProperties(Map map){

        //the base layer of the map
        var baseLayer = map.getLayer();

        //generate the properties
        this.height = baseLayer.getHeight();
        this.width = baseLayer.getWidth();
        this.tileWidth = baseLayer.getTileWidth();
        this.tileHeight = baseLayer.getTileHeight();
        this.tileHeightWorldUnits = this.tileHeight / MathConstants.WORLD_UNIT;
        this.tileWidthWorldUnits = this.tileWidth / MathConstants.WORLD_UNIT;
        this.heightWorldUnits = this.height / MathConstants.WORLD_UNIT;
        this.widthWorldUnits = this.width / MathConstants.WORLD_UNIT;
        this.widthPixels = this.width * this.tileWidth;
        this.heightPixels = this.height * this.tileHeight;
    }
}
