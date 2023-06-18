import java.io.*;
import processing.core.*;

/**
 * The leaderboard class represents a leaderboard for storing and retrieving scores.
 */
public class leaderboard {

  // The filename of the leaderboard file.
  public static final String leaderboardFile = "leaderboard.csv";

  /**
   * Adds a score to the leaderboard.
   *
   * @param username the username of the player
   * @param score    the score achieved by the player
   * @param attempts the number of attempts made by the player
   */
  public static void addScore(String username, int score, int attempts) {
    if (isUsernameUnique(username)) {
      try {
        FileWriter writer = new FileWriter(leaderboardFile, true);
        writer.append(username).append(",").append(Integer.toString(score)).append(",").append(Integer.toString(attempts)).append("\n");
        writer.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  /**
   * Checks if a username is unique in the leaderboard.
   *
   * @param username the username to check for uniqueness
   * @return true if the username is unique, false otherwise
   */
  private static boolean isUsernameUnique(String username) {
    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length > 0 && parts[0].equals(username)) {
          return false; // Username already exists
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true; // Username is unique
  }
}
