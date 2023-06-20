import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * The LeaderboardPrinter class is responsible for printing the leaderboard.
 */
public class LeaderboardPrinter extends leaderboard {

  /**
   * Prints the leaderboard.
   * Displays the top three scores from the leaderboard file.
   */
  public void printLeaderboard() {
    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      // Initialize counter variable
      int counter = 0; 
      while ((line = reader.readLine()) != null && counter < 3) {
        String[] parts = line.split(",");
        // Checks if the line has at least 3 parts (username, score, and attempts).
        if (parts.length >= 3) {
          // Retrieves the username from the parts array.
          String username = parts[0]; 
          // Parses the score from the parts array as an integer.
          int score = Integer.parseInt(parts[1]); 
          // Parses the attempts from the parts array as an integer.
          int attempts = Integer.parseInt(parts[2]); 
          // Prints the username, score, and attempts.
          System.out.println("Username: " + username + ", Score: " + score + ", Attempts: " + attempts); 
        }
        // Increment the counter after printing a line
        counter++; 
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    
  }
}
