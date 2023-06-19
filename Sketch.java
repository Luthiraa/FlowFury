//import nessecary libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import processing.core.*;


/**
The Sketch class represents a game sketch using the Processing library.
It extends the PApplet class.
*/
public class Sketch extends PApplet {
 
  //initilaize certain variables, and wcreate new instances of instance variables
  static boolean barriers = false;
  //boolean variable to check if the usere is entering their username
  boolean isEnteringUsername = false;
  //initialize the island objetc (instanitied from the gate class)
  PImage island;
  // instantiate a leaderboard object form the leaderboard class
  leaderboard leaderboard;
   //initialize the mainMenu object from PImage
  PImage mainMenu;
   // instantiate a canoe object form the canoe class
  Canoe canoe;
  //set of boolean statements to control the different screens and flow of the game
  boolean isMainMenu = true;
  boolean isGame = false;
  boolean isGameOver = false;
  boolean rndFact = false;
  // instantiate a gate object form the gate class
  Gate gate;
  //initialize integer attempt as 0
  int attempts = 0;
  // initialzie a string for the username
  String username = "";
  // flag for left arrow key
  boolean isLeftKeyPressed; 
  // flag for right arrow key
  boolean isRightKeyPressed; 
  private int countdownTimer = 5;
  //countdown
  private boolean isCountdownActive = false;
  // private long to hold a value for the countdown
  private long countdownStartTime;
  // player's score
  static int score; 

  public void settings() {
    size(800, 400);
    // adjust the window size as needed
  }

  public void setup() {
    canoe = new Canoe(this, loadImage("images/canoe.png"), width / 2, height - 100, 5, 0);

    gate = new Gate(this, loadImage("images/island.png"), width / 2, height - 200, 100, 60);

    mainMenu = loadImage("images/MainMenu.png");
    score = 0;
  }

  
  /** 
   * @param min
   * @param max
   * @return int
   */
  public int getRandomNumber(int min, int max) {
    return (int) random(min, max + 1);
  }

  public void mouseClicked() {
      if (getCurrentState() == GameState.GAME_OVER) {
    // Check if the "Play Again" button is clicked
    if (mouseX >= width / 2 - 50 && mouseX <= width / 2 + 50 &&
        mouseY >= height / 2 + 35 && mouseY <= height / 2 + 65) {
      resetGame(); // Call the method to reset the game
    }
  }
  }
  public void draw() {
    
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
    isCountdownActive = true;
    countDown(10);
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

        printRandomLine("randomBoatFacts.txt");
        Scanner scan = new Scanner(System.in);
        System.out.println("Press Enter to Continue");
        scan.nextLine();
        scan.nextLine();
        updateCountdown();
      fill(0);
    textSize(36);
    textAlign(CENTER);
        
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

  
  private enum GameState {
    MAIN_MENU,
    GAME,
    GAME_OVER
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
    public static void printRandomLine(String filePath) {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        if (!lines.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(lines.size());
            String randomLine = lines.get(randomIndex);
            System.out.println("Random Line: " + randomLine);
        } else {
            System.out.println("The file is empty.");
        }
    }

    public void countDown(int num) {
    if (num >= 0) {
      println(num); // Print the current number
      countDown(num - 1); // Call the function recursively with the next number
    }
  }
private void setGameOver() {
  isMainMenu = false;
  isGame = false;
  isGameOver = true;
  leaderboard.addScore(username, score, attempts += 1);
  SortedLeaderboardPrinter leaderboardPrinter = new SortedLeaderboardPrinter();
  leaderboardPrinter.printLeaderboardSortedByScore();
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
      // Enable username input
      isEnteringUsername = true;
      username = "";
    }
  }
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
private void drawGameOver() {
  background(0); // Draw a black background

  // Display game over text
  fill(255);
  textSize(30);
  textAlign(CENTER);
  text("Game Over", width / 2, height / 2 - 50);

  // Display final score
  textSize(24);
  text("Final Score: " + score, width / 2, height / 2);

  // Display play again button
  textSize(20);
  text("Play Again", width / 2, height / 2 + 50);
}


private void resetGame() {
  // Reset necessary variables and states
  score = 0;
  canoe.setX( width / 2);
  canoe.setY(height -100);
  canoe.setSpeed(5);
  canoe.setAngle(0);
  setMainMenu();
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
    private void updateCountdown() {
    background(85, 107, 207); // white background

    // Calculate the remaining time
    int remainingTime = countdownTimer - (int) ((millis() - countdownStartTime) / 1000);
    if (remainingTime <= 0) {
      isCountdownActive = false; // Countdown finished, resume the game
      return;
    }

    // Display countdown text
    fill(0);
    textSize(36);
    textAlign(CENTER);
    text("Resume in " + remainingTime + "...", width / 2, height / 2);

    // Display the "Resume" button
    fill(255);
    rectMode(CENTER);
    rect(width / 2, height / 2 + 50, 100, 30);
    fill(0);
    textSize(20);
    textAlign(CENTER);
    text("Resume", width / 2, height / 2 + 55);
  }

}




