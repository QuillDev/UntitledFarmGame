package tech.quilldev.Engine.Entities;

import tech.quilldev.Engine.Utilities.Position;
import tech.quilldev.MathConstants;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;

public class EntityCollider extends Rectangle2D.Float {

    //list of the colliders points
    private ArrayList<Point2D.Float> points;

    /**
     * Constructor for creating a collider based on an entity
     * @param entity the entity the collider belongs to
     */
    public EntityCollider(Entity entity){
        super();

        //Calc x y width and height
        var position = entity.getPosition();

        this.x = position.x;
        this.y = position.y;

        //if the texture is null use world units
        var nullTexture = ( entity.getTexture() == null );
        this.height = nullTexture ? MathConstants.WORLD_UNIT : entity.getTexture().getHeight();
        this.width = nullTexture ? MathConstants.WORLD_UNIT : entity.getTexture().getWidth();

        this.createPoints();
    }

    public EntityCollider(EntityCollider entityCollider){
        this.x = (float) entityCollider.getX();
        this.y = (float) entityCollider.getY();
        this.width = (float) entityCollider.getWidth();
        this.height = (float) entityCollider.getHeight();
        this.createPoints();
    }
    /**
     * Create a collider using the
     * @param x of the rectangle bottom left
     * @param y of the rectangle bottom left
     * @param height of the rectangle
     * @param width of the rectangle
     */
    public EntityCollider(float x, float y, float height, float width){
        super(x, y, width, height);
        this.createPoints();
    }

    /**
     * Update the position of the collider
     * @param position to change to
     */
    public void updatePosition(Position position){
        this.x = position.x;
        this.y = position.y;
        this.createPoints();
    }

    /**
     * Set the width and height of the collider
     * @param width of the collider
     * @param height of the collider
     */
    public void setDimensions(float width, float height){
        this.height = height;
        this.width = width;
        this.createPoints();
    }

    /**
     * Check if this collider is colliding with another collider
     * @param entityCollider the entity collider to check
     * @return the collider
     */
    public boolean collidingWith(EntityCollider entityCollider){
        for(var point : entityCollider.getPoints()){
            if(this.contains(point)){
                return true;
            }
        }

        return false;
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
    public ArrayList<Point2D.Float> getPoints() {
        return points;
    }

    /**
     * Create the colliders edge points
     */
    private void createPoints(){
        var p1 = new Point2D.Float(this.x, this.y);
        var p2 = new Point2D.Float(this.x + this.width, this.y);
        var p3 = new Point2D.Float(this.x + this.width, this.y + this.height);
        var p4 = new Point2D.Float(this.x , this.y + this.height);

        this.points = new ArrayList<>(Arrays.asList(p1, p2, p3, p4));
    }

    /**
     * Get the position of the collider
     * @return the colliders position
     */
    public Position getPosition(){
        return new Position(this.x, this.y);
    }

    @Override
    public String toString() {
        return String.format("EntityCollider : {X: %s, Y: %s, W: %s, H: %s}", this.x, this.y, this.width, this.height);
    }
}
