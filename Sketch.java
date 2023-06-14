import processing.core.*;

public class Sketch extends PApplet {

  static boolean barriers = false;

  PImage island;
  PImage mainMenu;
  Canoe canoe;
  boolean isMainMenu = true;
  boolean isGame = false;
  Gate gate;

  boolean isLeftKeyPressed; // flag for left arrow key
  boolean isRightKeyPressed; // flag for right arrow key

  static int score; // player's score

  public void settings() {
    size(800, 400); // adjust the window size as needed
  }

  public void setup() {
    canoe = new Canoe(this, loadImage("images/canoe.png"), width / 2, height - 100, 5, 0);

    gate = new Gate(this, loadImage("images/island.png"), width / 2, height - 200, 100, 60);

    mainMenu = loadImage("images/MainMenu.png");
    score = 0;
  }
public void draw() {
  if (mousePressed) {
    // System.out.println("X: "+ pmouseX);
    // System.out.println("Y: "+ pmouseY);
  }

  if (isMainMenu) {
    image(mainMenu, 0, 0);
    // 99,194 - 368,308
  }
  if (isGame) {
    if ((canoe.getX() >= 800 || canoe.getX() <= -10 || canoe.getY() >= 410 || canoe.getY() <= -10) && barriers) {
      isGame = false;
      System.out.println("Game Over");
    }
    background(85, 107, 207); // white background

    // Move the canoe
    if (!isRightKeyPressed && isLeftKeyPressed) {
      canoe.angle += 0.05;
    } else if (isRightKeyPressed && !isLeftKeyPressed) {
      canoe.angle -= 0.05;
    }



    canoe.x += canoe.speed * PApplet.cos(canoe.angle);
    canoe.y -= canoe.speed * PApplet.sin(canoe.angle);

    // Wrap the canoe around the screen
    if (canoe.x < 0) {
      canoe.x = width;
    } else if (canoe.x > width) {
      canoe.x = 0;
    }

    // Check collision with the gate
    if (canoe.getX() > gate.getX() && canoe.getX() < gate.getX() + gate.getWidth()
        && canoe.getY() > gate.getY() && canoe.getY() < gate.getY() + gate.getHeight()) {
      score++;

      // 20% chance to switch window
      if (random(0, 1) < 0.2) {
        isGame = false;
        isMainMenu = true;
      } else {
        double canoeAngleD = canoe.getAngle() +0.1;
        float canoeAngleF = (float) canoeAngleD;
        double canoeSpeedD = canoe.getSpeed() +0.1;
        float canoeSpeedF = (float) canoeSpeedD;
        canoe.setAngle(canoeAngleF);
        canoe.setSpeed(canoeSpeedF);
        gate.setX(random(0, width - gate.getWidth()));
        gate.setY(random(0, height / 2 - gate.getHeight()));
      }
    }

    gate.display();

    canoe.display();

    // Display the score
    fill(0);
    textSize(24);
    text("Score: " + score, 10, 30);
  }
}      
public void keyPressed() {
    barriers = true;
    if (keyCode == LEFT) {
      isLeftKeyPressed = true;
    } else if (keyCode == RIGHT) {
      isRightKeyPressed = true;
    }
  }

  public void keyReleased() {
    barriers = true;
    if (keyCode == LEFT) {
      isLeftKeyPressed = false;
    } else if (keyCode == RIGHT) {
      isRightKeyPressed = false;
    }
  }

  public void mousePressed() {
    if (mouseX >= 98 && mouseX <= 369 && mouseY >= 194 && mouseY <= 307) {
      if (!isGame && isMainMenu) {
        isGame = true;
        isMainMenu = false;
      }
    }
  }
}