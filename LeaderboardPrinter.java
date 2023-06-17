import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeaderboardPrinter extends leaderboard {

  public void printLeaderboard() {
    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 3) {
          String username = parts[0];
          int score = Integer.parseInt(parts[1]);
          int attempts = Integer.parseInt(parts[2]);
          System.out.println("Username: " + username + ", Score: " + score + ", Attempts: " + attempts);
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
