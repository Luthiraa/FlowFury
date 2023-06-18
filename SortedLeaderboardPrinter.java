import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class SortedLeaderboardPrinter extends LeaderboardPrinter {

  public void printLeaderboardSortedByScore() {
    String[][] leaderboardData = readLeaderboardData();
    sortLeaderboardDataByScore(leaderboardData);
    printLeaderboardData(leaderboardData);
  }

  private String[][] readLeaderboardData() {
    String[][] leaderboardData = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      int rowCount = 0;
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");
        if (parts.length >= 3) {
          if (leaderboardData == null) {
            leaderboardData = new String[1][3];
          } else {
            leaderboardData = Arrays.copyOf(leaderboardData, leaderboardData.length + 1);
          }
          leaderboardData[rowCount] = parts;
          rowCount++;
        }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return leaderboardData;
  }

  private void sortLeaderboardDataByScore(String[][] leaderboardData) {
    boolean swapped;
    int n = leaderboardData.length;

    for (int i = 0; i < n - 1; i++) {
      swapped = false;
      for (int j = 0; j < n - i - 1; j++) {
        int score1 = Integer.parseInt(leaderboardData[j][1].trim());
        int score2 = Integer.parseInt(leaderboardData[j + 1][1].trim());
        if (score1 < score2) {
          swapEntries(leaderboardData, j, j + 1);
          swapped = true;
        }
      }
      if (!swapped) {
        break;
      }
    }
  }

  private void swapEntries(String[][] leaderboardData, int i, int j) {
    String[] temp = leaderboardData[i];
    leaderboardData[i] = leaderboardData[j];
    leaderboardData[j] = temp;
  }

  private void printLeaderboardData(String[][] leaderboardData) {
    for (String[] entry : leaderboardData) {
      String username = entry[0];
      int score = Integer.parseInt(entry[1]);
      int attempts = Integer.parseInt(entry[2]);
      System.out.println("Username: " + username + ", Score: " + score + ", Attempts: " + attempts);
    }
  }
   @Override
    public void printLeaderboard() {
        String[][] leaderboardData = readLeaderboardData();
        sortLeaderboardDataByScore(leaderboardData);
        printLeaderboardData(leaderboardData);
    }
}
