package tech.quilldev.Engine.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import tech.quilldev.DebugModes;
import tech.quilldev.Engine.Utilities.Position;

import java.awt.*;

import java.util.ArrayList;
import java.util.Arrays;

public class EntityCollider extends Rectangle {

    private ArrayList<Point> points;
    private final Texture debugTexture = new Texture("textures/selected.png");

    /**
     * Constructor for creating a collider based on an entity
     * @param entity the entity the collider belongs to
     */
    public EntityCollider(Entity entity){
        super();
        //Calc x y width and height
        var position = entity.getPosition();
        var texture = entity.getTexture();

        //set the properties of the collider
        var x = (int) position.getX();
        var y = (int) ( position.getY());
        var height = texture.getHeight();
        var width = texture.getWidth();

        this.x = x;
        this.y = y;
        this.height = height;
        this.width = width;

        this.points = createPoints();
    }

    /**
     * Create a collider using the
     * @param x of the rectangle bottom left
     * @param y of the rectangle bottom left
     * @param height of the rectangle
     * @param width of the rectangle
     */
    public EntityCollider(int x, int y, int height, int width){
        super(x, y, width, height);
        this.points = createPoints();
    }

    /**
     * Constructor for the collider that generates a defauly collider with no params
     */
    public EntityCollider(){
        super();
        this.points = createPoints();
    }

    public void updatePosition(Position position){
        this.x = (int) position.getX();
        this.y = (int) position.getY();
        this.points = createPoints();
    }

    /**
     * Check if this collider is colliding with another collider
     * @param entityCollider the entity collider to check
     * @return the collider
     */
    public boolean collidingWith(EntityCollider entityCollider){
        for(Point point : entityCollider.getPoints()){
            if(this.contains(point)){
                return true;
            }
        }

        return false;
    }

    public void render(Batch batch){
        if(DebugModes.COLLIDERS){
            batch.draw(this.debugTexture, this.x, this.y);
        }

    }
    /**
     * Whether we're colliding with another entity
     * @param entity we're checking for collisions with
     * @return whether we're colliding with them
     */
    public boolean collidingWith(Entity entity){
        return this.collidingWith(entity.getCollider());
    }

    //check if we're colliding with the given area
    public boolean collidingWith(AreaCollider area){
        return area.collidingWithEntityCollider(this);
    }

    /**
     * Get the points array
     * @return the points array
     */
    public ArrayList<Point> getPoints() {
        return points;
    }

    /**
     * Get the edge points of the collider
     * @return the edge points of the collider
     */
    public ArrayList<Point> createPoints(){
        var p1 = new Point(this.x, this.y);
        var p2 = new Point(this.x + this.width, y);
        var p3 = new Point(this.x + this.width, this.y + this.height);
        var p4 = new Point(this.x , this.y + this.height);

        return new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
    }
}
