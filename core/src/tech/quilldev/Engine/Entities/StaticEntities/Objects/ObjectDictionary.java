package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import tech.quilldev.Engine.Utilities.Position;

import java.util.HashMap;

public class ObjectDictionary {

    private static HashMap<ObjectType, GameObject> objectMap;

    /**
     * Create the object dictionary
     */
    public ObjectDictionary(){
        objectMap = new HashMap<>();

        //Register all objects
        objectMap.put(ObjectType.ROCK, new RockObject());
        objectMap.put(ObjectType.TALL_GRASS, new TallGrassObject());
    }

    /**
     * Get the object from the given type
     * @param type of the object to get
     * @return the game object
     */
    public static GameObject getObject(ObjectType type){
        return objectMap.get(type);
    }

    /**
     * Clone the object and set the position to the given one
     * @param type of object to clone
     * @param position to clone it at
     * @return the new object at the position
     */
    public static GameObject cloneObjectOfType(ObjectType type, Position position){
        var object = getObject(type);

        //if the object is null, return null
        if(object == null){
            return null;
        }

        return new CloneObject(object, position);
    }
}
