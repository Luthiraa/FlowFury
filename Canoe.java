import processing.core.PApplet;
import processing.core.PImage;

public class Canoe {
  private PApplet parent;
  private PImage image;
  public float x;
  public float y;
  public float speed;
  public float angle;


  public Canoe(PApplet parent, PImage image, float x, float y, float speed, float angle) {
    this.parent = parent;
    this.image = image;
    this.x = x;
    this.y = y;
    this.speed = speed;
    this.angle = angle;
  }



  public void display() {
    // Draw the canoe
    parent.pushMatrix();
    parent.translate(x, y);
    parent.rotate(angle);
    parent.image(image, -image.width / 2, -image.height / 2);
    parent.popMatrix();
    parent.resetMatrix();
  }

  public float getSpeed() {
      return this.speed;
  }

  public void setSpeed(float speed) {
      this.speed = speed;
  }

  public void setAngle(float angle) {
      this.angle = angle;
  }
  public float getX() {
    return x;
  }

  public float getY() {
    return y;
  }

  public void setX(float x) {
    this.x = x;
  }

  public void setY(float y) {
    this.y = y;
  }

  public float getAngle() {
    return angle;
  }
}
