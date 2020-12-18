package tech.quilldev.Engine.Entities;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;

public class AreaCollider extends Area {

    /**
     * Check if we're colliding with the given entity
     * @param entityCollider the entity to check collision with
     * @return whether we've collided
     */
    public boolean collidingWithEntityCollider(EntityCollider entityCollider){
        for(Point point : entityCollider.getPoints()){
            if(this.contains(point)){
                return true;
            }
        }

        return false;

    }

    /**
     * Check if we're colliding with the given entity
     * @param entity to check collisions with
     * @return whether we're colliding
     */
    public boolean collidingWithEntity(Entity entity){
        return collidingWithEntityCollider(entity.getCollider());
    }

    public void add(Shape shape){
        this.add(new Area(shape));
    }

    /**
     * Add all of the shapes to the area
     * @param shapes to add
     */
    public void addAll(ArrayList<Shape> shapes){
        for(Shape shape : shapes){
            this.add(shape);
        }
    }
}
