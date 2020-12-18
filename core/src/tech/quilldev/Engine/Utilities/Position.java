package tech.quilldev.Engine.Utilities;

import tech.quilldev.MathConstants;

public class Position {

    //the x and y value of the position
    private float x;
    private float y;

    /**
     * Create a new position with an x and y value
     * @param x value of the position
     * @param y value of the position
     */
    public Position(float x, float y){
        this.x = x;
        this.y = y;
    }

    /**
     * Create a position from another position
     * @param position to clone
     */
    public Position(Position position){
        this.x = position.getX();
        this.y = position.getY();
    }

    /**
     * Default position constructor
     */
    public Position(){
        this.x = 0;
        this.y = 0;
    }



    /**
     * Get the world units in X
     * @return the world units in X
     */
    public float getXWorldUnits(){
        return this.x / MathConstants.WORLD_UNIT;
    }

    /**
     * Get the world units in Y
     * @return the world units in Y
     */
    public float getYWorldUnits(){
        return this.y / MathConstants.WORLD_UNIT;
    }

    /**
     * Get the X world units truncated to an integer
     * @return the X world units
     */
    public int getXWorldUnitsTruncated(){
        return (int) Math.floor(getXWorldUnits());
    }

    /**
     * Get the Y world units as an integer
     * @return the y world units
     */
    public int getYWorldUnitsTruncated(){
        return (int) Math.floor(getYWorldUnits());
    }

    /**
     * Add the other position to the current one
     * @param position to add to this one
     */
    public void addPosition(Position position){
        this.x += position.getX();
        this.y += position.getY();
    }

    /**
     * Add to the x value
     * @param x value to add
     */
    public void addX(float x){
        this.x += x;
    }

    /**
     * Add the y value
     * @param y value to add
     */
    public void addY(float y){
        this.y += y;
    }

    /**
     * Subtracts the position from the current one
     * @param position to subtract from this one
     */
    public void subtractPosition(Position position){
        this.x -= position.getX();
        this.y -= position.getY();
    }

    //Get the x of the position
    public float getX() {
        return x;
    }

    //get the y from the position
    public float getY() {
        return y;
    }

    /**
     * Set the x value of the position
     * @param x the x value of the position
     */
    public void setX(float x){
        this.x = x;
    }

    /**
     * Set the y value of the position
     * @param y the y value of the position
     */
    public void setY(float y){
        this.y = y;
    }
}
