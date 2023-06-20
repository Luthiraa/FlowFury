
//import nessecary libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import processing.core.*;

/**
 * The Sketch class represents a game sketch using the Processing library.
 * It extends the PApplet class.
 */
public class Sketch extends PApplet {

  // initilaize certain variables, and wcreate new instances of instance variables
  static boolean barriers = false;
  // boolean variable to check if the usere is entering their username
  boolean isEnteringUsername = false;
  // initialize the island objetc (instanitied from the gate class)
  PImage island;
  // instantiate a leaderboard object form the leaderboard class
  leaderboard leaderboard;
  // initialize the mainMenu object from PImage
  PImage mainMenu;
  // instantiate a canoe object form the canoe class
  Canoe canoe;
  // set of boolean statements to control the different screens and flow of the
  // game
  boolean isMainMenu = true;
  boolean isGame = false;
  boolean isGameOver = false;
  boolean rndFact = false;
  // instantiate a gate object form the gate class
  Gate gate;
  // initialize integer attempt as 0
  int attempts = 0;
  // initialzie a string for the username
  String username = "";
  // flag for left arrow key
  boolean isLeftKeyPressed;
  // flag for right arrow key
  boolean isRightKeyPressed;
  private int countdownTimer = 5;
  // countdown
  private boolean isCountdownActive = false;
  // private long to hold a value for the countdown
  private long countdownStartTime;
  // player's score
  static int score;

  /**
   * Sets the size of the application window.
   */
  public void settings() {
    size(800, 400);
    // adjust the window size as needed
  }

  /**
   * Initializes the game objects and variables.
   */
  public void setup() {
    // Create a new instance of the Canoe object
    canoe = new Canoe(this, loadImage("images/canoe.png"), width / 2, height - 100, 5, 0);

    // Create a new instance of the Gate object
    gate = new Gate(this, loadImage("images/island.png"), width / 2, height - 200, 100, 60);

    // Load the main menu image
    mainMenu = loadImage("images/MainMenu.png");

    // Initialize the score
    score = 0;
  }

  /**
   * Generates a random integer between the specified minimum and maximum values.
   *
   * @param min The minimum value (inclusive).
   * @param max The maximum value (inclusive).
   * @return The randomly generated integer.
   */
  public int getRandomNumber(int min, int max) {
    return (int) random(min, max + 1);
  }

  /**
   * Handles the mouse click event.
   */
  public void mouseClicked() {
    if (getCurrentState() == GameState.GAME_OVER) {
      // Check if the "Play Again" button is clicked
      if (mouseX >= width / 2 - 50 && mouseX <= width / 2 + 50 &&
          mouseY >= height / 2 + 35 && mouseY <= height / 2 + 65) {
        resetGame(); // Call the method to reset the game
      }
    }
  }

  /**
   * The main drawing loop of the game.
   */
  public void draw() {
    // Uncomment the following lines to print mouse coordinates
    // if (mousePressed) {
    // System.out.println("X: "+ pmouseX);
    // System.out.println("Y: "+ pmouseY);
    // }

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
    // countDown(10);
  }

  /**
   * Draws the main menu screen.
   */
  private void drawMainMenu() {
    // Display the main menu image
    image(mainMenu, 0, 0);

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

  /**
   * Updates the game state.
   */
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
        sleep(5000);
        fill(0);
        textSize(36);
        textAlign(CENTER);

      } else {
        // cast canoe angle and from a double to a float
        double canoeAngleD = canoe.getAngle() + 0.1;
        float canoeAngleF = (float) canoeAngleD;
        // cast canoe speed from double to a float
        double canoeSpeedD = canoe.getSpeed() + 0.1;
        float canoeSpeedF = (float) canoeSpeedD;
        // set angle/trajectory
        canoe.setAngle(canoeAngleF);
        // set canoe speed
        canoe.setSpeed(canoeSpeedF);
        // set the random location fro the gate
        gate.setX(random(0, width - gate.getWidth()));
        gate.setY(random(0, height / 2 - gate.getHeight()));
      }
    }
    // show the date
    gate.display();
    // show the canoe
    canoe.display();

    // Display the score
    fill(0);
    textSize(24);
    text("Score: " + score, 50, 30);
  }

  /**
   * Represents the possible game states.
   */
  private enum GameState {
    MAIN_MENU,
    GAME,
    GAME_OVER
  }

  /**
   * Sets the game state to the main menu.
   */
  private void setMainMenu() {
    isMainMenu = true;
    isGame = false;
    isGameOver = false;
  }

  /**
   * Sets the game state to the active game.
   */
  private void setGame() {
    isMainMenu = false;
    isGame = true;
    isGameOver = false;
  }

  /**
   * Prints a random line from a file.
   *
   * @param filePath The path to the file containing random lines.
   */
  public static void printRandomLine(String filePath) {
    // Read lines from the file into a list
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
      // Generate a random index and retrieve a random line from the list
      Random random = new Random();
      int randomIndex = random.nextInt(lines.size());
      String randomLine = lines.get(randomIndex);
      System.out.println("Random Line: " + randomLine);
    } else {
      System.out.println("The file is empty.");
    }
  }

  /**
   * Starts a countdown from the specified number.
   *
   * @param num The number to start the countdown from.
   */
  public void countDown(int num) {
    if (num >= 0) {
      println(num); // Print the current number
      countDown(num - 1); // Call the function recursively with the next number
    }
  }

  /**
   * Sets the game state to game over.
   */
  private void setGameOver() {
    isMainMenu = false;
    isGame = false;
    isGameOver = true;
    leaderboard.addScore(username, score, attempts += 1);
    SortedLeaderboardPrinter leaderboardPrinter = new SortedLeaderboardPrinter();
    leaderboardPrinter.printLeaderboardSortedByScore();
  }

  /**
   * Handles the key press event.
   */
  public void keyPressed() {
    barriers = true;
    if (keyCode == LEFT) {
      isLeftKeyPressed = true;
    } else if (keyCode == RIGHT) {
      isRightKeyPressed = true;
    }
  }

  /**
   * Handles the key release event.
   */
  public void keyReleased() {
    barriers = true;
    if (keyCode == LEFT) {
      isLeftKeyPressed = false;
    } else if (keyCode == RIGHT) {
      isRightKeyPressed = false;
    }
  }

  /**
   * Handles the mouse press event.
   */
  public void mousePressed() {
    if (mouseX >= 98 && mouseX <= 369 && mouseY >= 194 && mouseY <= 307) {
      if (!isGame && isMainMenu) {
        // Enable username input
        isEnteringUsername = true;
        username = "";
      }
    }
  }

  /**
   * Handles the key type event.
   */
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

  /**
   * Draws the game over screen.
   */
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

  /**
   * Resets the game to its initial state.
   */
  private void resetGame() {
    // Reset necessary variables and states
    score = 0;
    canoe.setX(width / 2);
    canoe.setY(height - 100);
    canoe.setSpeed(5);
    canoe.setAngle(0);
    setMainMenu();
  }

  /**
   * Returns the current game state.
   *
   * @return The current game state.
   * @throws IllegalStateException if the game state is unknown.
   */
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

  /**
   * Updates the countdown timer and displays the countdown on the screen.
   */
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
    rect(width / 2, height / 2 + 50, 200, 60);
    fill(0);
    textSize(24);
    textAlign(CENTER);
    text("Resume", width / 2, height / 2 + 55);
  }

/**

Sleep Function to delay program 
@param milliseconds the number of milliseconds to sleep
@throws IllegalArgumentException if the specified duration is negative
*/
public static void sleep(int milliseconds) {
try {
Thread.sleep(milliseconds);
} catch (InterruptedException e) {
e.printStackTrace();
}
}
}
