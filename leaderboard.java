import java.io.*;

public class leaderboard {

  private static final String leaderboardFile = "leaderboard.csv";

  public static void addScore(String username, int score) {
    if (isHighscore(score) && isUsernameUnique(username)) {
      try {
        FileWriter writer = new FileWriter(leaderboardFile, true);
        writer.append(username).append(",").append(Integer.toString(score)).append("\n");
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

  private static boolean isHighscore(int score) {
    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length > 1) {
          int currentScore = Integer.parseInt(parts[1]);
          if (score <= currentScore) {
            return false; // Not a highscore
          }
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return true; // It's a highscore
  }
}
