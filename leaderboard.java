import java.io.*;
import processing.core.*;

public class leaderboard {

  public static final String leaderboardFile = "leaderboard.csv";

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

  public void checkHS() {
    // ...existing code...
  }
}