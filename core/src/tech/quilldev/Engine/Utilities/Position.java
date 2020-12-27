package tech.quilldev.Engine.Utilities;

import tech.quilldev.Engine.Console.GameConsole;
import tech.quilldev.MathConstants;

import java.util.Objects;

public class Position {

    //the x and y value of the position
    public float x;
    public float y;

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
     * Whether the positions are approximately the same (within 2 decimal places)
     * @param position to check approx with
     * @return the position
     */
    public boolean approx(Position position){
        return this.getApproxPosition().equals(position.getApproxPosition());
    }

    public Position getApproxPosition(){
        return new Position(Math.round(this.x * 100f) / 100f, Math.round(this.y * 100f) / 100f);
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


    /**
     * Randomize the position a bit
     * @return the jiggled position
     */
    public Position jiggle(){

        var unit = MathConstants.WORLD_UNIT /2f;
        this.x +=  ( Math.random() * unit ) - ( unit / 2f);
        this.y +=  ( Math.random() * unit ) - ( unit / 2f);

        GameConsole.log(toString());
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Position position = (Position) o;
        return Float.compare(position.x, x) == 0 && Float.compare(position.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return String.format("[X: %s, Y: %s]", Math.round(this.x * 100f) / 100f, Math.round(this.y * 100f) / 100f);
    }
}
