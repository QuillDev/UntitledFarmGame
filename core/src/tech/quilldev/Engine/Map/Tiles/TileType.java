package tech.quilldev.Engine.Map.Tiles;

public enum TileType {
    AIR(1),
    PLANTED_SOIL(2),
    GROWING(3),
    CARROT(4),
    DIRT(5),
    GRASS(6),
    SOIL(7),
    WATER_1(8),
    WATER_2(9),
    WATER_ANIMATED(10),
    FOLIAGE(11)
    ;

    //the value of the tile type
    private final int value;

    //constructor for tile types
    TileType(int value){
        this.value = value;
    }

    /**
     * Get the value of hte enum
     * @return the enums value
     */
    public int getValue() {
        return value;
    }
}
