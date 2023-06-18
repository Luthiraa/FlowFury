import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LeaderboardPrinter extends leaderboard {
  public void printLeaderboard() {
    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      int counter = 0; // Initialize counter variable
      while ((line = reader.readLine()) != null && counter < 3) {
        String[] parts = line.split(",");
        if (parts.length >= 3) {
          String username = parts[0];
          int score = Integer.parseInt(parts[1]);
          int attempts = Integer.parseInt(parts[2]);
          System.out.println("Username: " + username + ", Score: " + score + ", Attempts: " + attempts);
        }
        counter++; // Increment the counter after printing a line
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
