import processing.core.*;

public class Sketch extends PApplet {
  static boolean barriers = false;

  PImage island;
  PImage mainMenu;
  PImage canoe;
  float canoeX; // canoe's x-coordinate
  float canoeY; // canoe's y-coordinate
  float canoeSpeed; // canoe's horizontal speed
  float canoeAngle; // canoe's rotation angle
  boolean isMainMenu = true;
  boolean isGame = false;
  float gateX; // gate's x-coordinate
  float gateY; // gate's y-coordinate
  float gateWidth; // gate's width
  float gateHeight; // gate's height

  boolean isLeftKeyPressed; // flag for left arrow key
  boolean isRightKeyPressed; // flag for right arrow key

  static int score; // player's score

  public void settings() {
    size(800, 400); // adjust the window size as needed
  }

  public void setup() {
    canoeX = width / 2;
    canoeY = height - 100;
    canoeSpeed = 5;
    canoeAngle = 0;

    gateX = width / 2;
    gateY = height - 200;
    gateWidth = 100;
    gateHeight = 60;
    canoe = loadImage("images/canoe.png");
    island = loadImage("images/island.png");

    mainMenu = loadImage("images/MainMenu.png");
    score = 0;
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
      if ((canoeX >= 800 || canoeX <= -10 || canoeY >= 410 || canoeY <= -10) && barriers == true) {
        isGame = false;
        System.out.println("Game Over");
      }
      background(85, 107, 207); // white background

      // Move the canoe
      if (isLeftKeyPressed && !isRightKeyPressed) {
        canoeAngle += 0.05;
      } else if (isRightKeyPressed && !isLeftKeyPressed) {
        canoeAngle -= 0.05;
      }

      canoeX += canoeSpeed * cos(canoeAngle);
      canoeY -= canoeSpeed * sin(canoeAngle);

      // Wrap the canoe around the screen
      if (canoeX < 0) {
        canoeX = width;
      } else if (canoeX > width) {
        canoeX = 0;
      }

      // Check collision with the gate
        // Check collision with the gate
      if (canoeX > gateX && canoeX < gateX + gateWidth && canoeY > gateY && canoeY < gateY + gateHeight) {
        score++;

        // 20% chance to switch window
        if (random(0, 1) < 0.2) {
          isGame = false;
          isMainMenu = true;
        } else {
          canoeAngle += 0.01;
          canoeSpeed += 0.3;
          gateX = random(0, width - gateWidth);
          gateY = random(0, height / 2 - gateHeight);
        }
      }

      
      image(island, gateX,gateY);
      // rect(gateX, gateY, gateWidth, gateHeight);

      // Draw the obstacles (island and rock) on top of the gates


      // Draw the canoe
      pushMatrix();
      translate(canoeX, canoeY);
      rotate(canoeAngle);
      image(canoe, -canoe.width / 2, -canoe.height / 2);
      popMatrix();
      resetMatrix();

      // Display the score
      fill(0);
      textSize(24);
      text("Score: " + score, 10, 30);
    }
  }

  public void mousePressed() {
    System.out.println(mouseX);
    System.out.println(mouseY);
    if (mouseX >= 98 && mouseX <= 369 && mouseY >= 194 && mouseY <= 307) {
      isGame = true;
    }
  }
}