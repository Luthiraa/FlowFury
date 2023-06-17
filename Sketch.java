import processing.core.*;

public class Sketch extends PApplet {

  static boolean barriers = false;
  boolean isEnteringUsername = false;
  PImage island;
  public leaderboard leaderboard;
  PImage mainMenu;
  Canoe canoe;
  boolean isMainMenu = true;
  boolean isGame = false;
  boolean isGameOver = false;
  boolean rndFact = false;
  Gate gate;
  String text;
  String username = "";
  boolean isLeftKeyPressed; // flag for left arrow key
  boolean isRightKeyPressed; // flag for right arrow key

  static int score; // player's score

  public void settings() {
    size(800, 400);
    text = loadTextFromFile("randomBoatFacts.txt");  // adjust the window size as needed
  }

  public void setup() {
    canoe = new Canoe(this, loadImage("images/canoe.png"), width / 2, height - 100, 5, 0);

    gate = new Gate(this, loadImage("images/island.png"), width / 2, height - 200, 100, 60);

    mainMenu = loadImage("images/MainMenu.png");
    score = 0;
  }
  private String loadTextFromFile(String filePath) {
    String[] lines = loadStrings(filePath);
    StringBuilder sb = new StringBuilder();
    for (String line : lines) {
      sb.append(line).append("\n");
    }
    return sb.toString();
  }

  public int getRandomNumber(int min, int max) {
    return (int) random(min, max + 1);
  }

  public void draw() {
    if (score==25){
      System.out.println("You reached a score of 25");
    }
    if (mousePressed) {
      // System.out.println("X: "+ pmouseX);
      // System.out.println("Y: "+ pmouseY);
    }

    switch (getCurrentState()) {
      case MAIN_MENU:
        drawMainMenu();
        break;
      case GAME:
        updateGame();
        break;
      case GAME_OVER:
        drawGameOver();
        break;
    }
  }

private void drawMainMenu() {
  image(mainMenu, 0, 0);
  // 99,194 - 368,308
  
  if (isEnteringUsername) {
    // Display username input field
    fill(255);
    textSize(20);
    textAlign(CENTER);
    text("Enter username:", width / 2, height / 2 - 50);
    rectMode(CENTER);
    rect(width / 2, height / 2, 200, 30);
    fill(0);
    text(username, width / 2, height / 2);
  }
}

  private void updateGame() {
    if ((canoe.getX() >= 800 || canoe.getX() <= -10 || canoe.getY() >= 410 || canoe.getY() <= -10) && barriers) {
      setGameOver();
      System.out.println("Game Over");
    }
    background(85, 107, 207); // white background

    // Move the canoe
    if (!isRightKeyPressed && isLeftKeyPressed) {
      canoe.angle += 0.09;
    } else if (isRightKeyPressed && !isLeftKeyPressed) {
      canoe.angle -= 0.09;
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
        rndFact = true;
        background(255);
        textAlign(CENTER, CENTER);
        fill(0);
        text(text, width / 2, height / 2);
      } else {
        double canoeAngleD = canoe.getAngle() + 0.1;
        float canoeAngleF = (float) canoeAngleD;
        double canoeSpeedD = canoe.getSpeed() + 0.1;
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

private void drawGameOver() {
  background(0); // Set background color to black

  // Draw game over screen
  fill(255);
  textSize(40);
  textAlign(CENTER);
  text("Game Over", width / 2, height / 2 - 50);
  
  // Draw play again button
  fill(0, 255, 0); // Green color
  rectMode(CENTER);
  rect(width / 2, height / 2 + 50, 200, 60); // Button dimensions
  
  // Draw play again text
  fill(255);
  textSize(24);
  text("Play Again", width / 2, height / 2 + 55);
}
  private enum GameState {
    MAIN_MENU,
    GAME,
    GAME_OVER
  }
  
  private GameState getCurrentState() {
    if (isMainMenu) {
      return GameState.MAIN_MENU;
    } else if (isGame) {
      return GameState.GAME;
    } else if (isGameOver) {
      return GameState.GAME_OVER;
    } else {
      throw new IllegalStateException("Unknown game state.");
    }
  }
  
  private void setMainMenu() {
    isMainMenu = true;
    isGame = false;
    isGameOver = false;
  }
  
  private void setGame() {
    isMainMenu = false;
    isGame = true;
    isGameOver = false;
  }
  
  private void setGameOver() {
    isMainMenu = false;
    isGame = false;
    isGameOver = true;
    leaderboard.addScore(username, score);
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

public void mouseClicked() {
    if (mouseX >= 98 && mouseX <= 369 && mouseY >= 194 && mouseY <= 307) {
    if (!isGame && isMainMenu) {
      // Enable username input
      isEnteringUsername = true;
      username = "";
    }
  }
  if (getCurrentState() == GameState.GAME_OVER) {
    // Check if the mouse is inside the play again button
    if (mouseX >= width / 2 - 100 && mouseX <= width / 2 + 100 &&
        mouseY >= height / 2 && mouseY <= height / 2 + 60) {
      // Reset the game and play again
      resetGame();
    }
  }
}
private void resetGame() {
  score = 0;
  setMainMenu();
}


public void keyTyped() {
  if (isEnteringUsername) {
    // Capture the username input
    if (keyCode == BACKSPACE) {
      if (username.length() > 0) {
        username = username.substring(0, username.length() - 1);
      }
    } else if (keyCode == ENTER || key == '\n') {
      // Start the game when the Enter key is pressed
      isEnteringUsername = false;
      setGame();
    } else {
      username += key;
    }
  }
}

}
