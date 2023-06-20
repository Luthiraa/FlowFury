import processing.core.PApplet;
import processing.core.PImage;

/**
 * The Canoe class represents a canoe object.
 * It provides methods for displaying and manipulating the canoe.
 */
public class Canoe {
    private PApplet parent;
    private PImage image;
    public float x;
    public float y;
    public float speed;
    public float angle;

    /**
     * Constructs a new Canoe object.
     *
     * @param parent the PApplet instance
     * @param image  the image to be displayed for the canoe
     * @param x      the x-coordinate of the canoe's position
     * @param y      the y-coordinate of the canoe's position
     * @param speed  the speed of the canoe
     * @param angle  the angle of the canoe's rotation
     */
    public Canoe(PApplet parent, PImage image, float x, float y, float speed, float angle) {
        this.parent = parent;
        this.image = image;
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    /**
     * Constructs a new Canoe object without the PApplet instance.
     *
     * @param x     the x-coordinate of the canoe's position
     * @param y     the y-coordinate of the canoe's position
     * @param speed the speed of the canoe
     * @param angle the angle of the canoe's rotation
     */
    public Canoe(float x, float y, float speed, float angle) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.angle = angle;
    }

    /**
     * Displays the canoe on the screen.
     * The canoe is drawn with the provided image, at the current position and
     * rotation angle.
     */
    public void display() {
        // Draw the canoe
        parent.pushMatrix();
        parent.translate(x, y);
        parent.rotate(angle);
        parent.image(image, -image.width / 2, -image.height / 2);
        parent.popMatrix();
        parent.resetMatrix();
    }

    /**
     * Returns the speed of the canoe.
     *
     * @return the speed of the canoe
     */
    public float getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed of the canoe.
     *
     * @param speed the new speed value for the canoe
     */
    public void setSpeed(float speed) {
        this.speed = speed;
    }

    /**
     * Sets the angle of the canoe's rotation.
     *
     * @param angle the new rotation angle for the canoe
     */
    public void setAngle(float angle) {
        this.angle = angle;
    }

    /**
     * Returns the x-coordinate of the canoe's position.
     *
     * @return the x-coordinate of the canoe's position
     */
    public float getX() {
        return x;
    }

    /**
     * Returns the y-coordinate of the canoe's position.
     *
     * @return the y-coordinate of the canoe's position
     */
    public float getY() {
        return y;
    }

    /**
     * Sets the x-coordinate of the canoe's position.
     *
     * @param x the new x-coordinate value for the canoe's position
     */
    public void setX(float x) {
        this.x = x;
    }

    /**
     * Sets the y-coordinate of the canoe's position.
     *
     * @param y the new y-coordinate value for the canoe's position
     */
    public void setY(float y) {
        this.y = y;
    }

    /**
     * Returns the angle of the canoe.
     *
     * @return the angle of the canoe
     */
    public float getAngle() {
        return angle;
    }
}