import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Gate class represents a gate object.
 * It provides methods for displaying and manipulating the gate.
 */
public class Gate {
    private PApplet parent;
    private PImage image;
    private float x;
    private float y;
    private float width;
    private float height;

    /**
     * Constructs a new Gate object.
     *
     * @param parent the PApplet instance
     * @param image  the image to be displayed for the gate
     * @param x      the x-coordinate of the gate's position
     * @param y      the y-coordinate of the gate's position
     * @param width  the width of the gate
     * @param height the height of the gate
     */
    public Gate(PApplet parent, PImage image, float x, float y, float width, float height) {
        this.parent = parent;
        this.image = image;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Displays the gate on the screen.
     * The gate is drawn with the provided image at the current position.
     */
    public void display() {
        // Draw the gate
        parent.image(image, x, y);
    }

    /**
     * Returns the x-coordinate of the gate's position.
     *
     * @return the x-coordinate of the gate's position
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the gate's position.
     *
     * @return the y-coordinate of the gate's position
     */
    public float getY() {
        return y;
    }

    /**
     * Returns the width of the gate.
     *
     * @return the width of the gate
     */
    public float getWidth() {
        return width;
    }

    /**
     * Returns the height of the gate.
     *
     * @return the height of the gate
     */
    public float getHeight() {
        return height;
    }

    /**
     * Sets the x-coordinate of the gate's position.
     *
     * @param x the new x-coordinate value for the gate's position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the gate's position.
     *
     * @param y the new y-coordinate value for the gate's position
     */
    public void setY(float y) {
        this.y = y;
    }
}
