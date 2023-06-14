import processing.core.PApplet;
import processing.core.PImage;

public class Gate {
  private PApplet parent;
  private PImage image;
  private float x;
  private float y;
  private float width;
  private float height;

  public Gate(PApplet parent, PImage image, float x, float y, float width, float height) {
    this.parent = parent;
    this.image = image;
    this.x = x;
    this.y = y;
    this.width = width;
    this.height = height;
  }

  public void display() {
    // Draw the gate
    parent.image(image, x, y);
  }

  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public void setX(float x) {
    this.x = x;
  }

  public void setY(float y) {
    this.y = y;
  }
}
