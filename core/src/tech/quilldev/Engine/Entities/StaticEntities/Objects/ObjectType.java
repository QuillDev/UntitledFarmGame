package tech.quilldev.Engine.Entities.StaticEntities.Objects;

import tech.quilldev.Engine.Utilities.Position;

public enum ObjectType {
    TALL_GRASS,
    ROCK;

    public static ObjectType getTypeFromString(String query){
        for(var type : ObjectType.values()){
            if(type.name().equals(query)){
                return type;
            }
        }

        return null;
    }
}
