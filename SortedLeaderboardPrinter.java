//import all java nessecary java libraries
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

/**
 * This class extends the LeaderboardPrinter class and provides functionality to print the leaderboard sorted by score.
 */
public class SortedLeaderboardPrinter extends LeaderboardPrinter {

  /**
   * Prints the leaderboard sorted by score.
   */
  public void printLeaderboardSortedByScore() {
    // Read the leaderboard data from the file
    String[][] leaderboardData = readLeaderboardData();

    // Sort the leaderboard data by score
    sortLeaderboardDataByScore(leaderboardData);

    // Print the sorted leaderboard data
    printLeaderboardData(leaderboardData);
  }

  /**
   * Reads the leaderboard data from a file and returns it as a 2D array.
   *
   * @return The leaderboard data as a 2D array.
   */
  private String[][] readLeaderboardData() {
    // Initialize the leaderboardData array
    String[][] leaderboardData = null;

    try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
      String line;
      int rowCount = 0;

      // Read each line from the file
      while ((line = reader.readLine()) != null) {
        String[] parts = line.split(",");

        // Check if the line has at least 3 parts (username, score, attempts)
        if (parts.length >= 3) {
          // If the leaderboardData array is null, initialize it with one row and three columns
          if (leaderboardData == null) {
            leaderboardData = new String[1][3];
          } else {
            // If the leaderboardData array already has data, increase its size by one row
            leaderboardData = Arrays.copyOf(leaderboardData, leaderboardData.length + 1);
          }

          // Store the parts in the current row of the leaderboardData array
          leaderboardData[rowCount] = parts;
          rowCount++;
        }
      }
    } catch (IOException e) {
      // Print the stack trace if an exception occurs while reading the file
      e.printStackTrace();
    }

    // Return the leaderboard data
    return leaderboardData;
  }

  /**
   * Sorts the leaderboard data by score in descending order.
   *
   * @param leaderboardData The leaderboard data to be sorted.
   */
  private void sortLeaderboardDataByScore(String[][] leaderboardData) {
    boolean swapped;
    int n = leaderboardData.length;

    // Bubble sort algorithm to sort the leaderboard data
    for (int i = 0; i < n - 1; i++) {
      swapped = false;
      for (int j = 0; j < n - i - 1; j++) {
        int score1 = Integer.parseInt(leaderboardData[j][1].trim());
        int score2 = Integer.parseInt(leaderboardData[j + 1][1].trim());

        // Swap entries if score1 is less than score2
        if (score1 < score2) {
          swapEntries(leaderboardData, j, j + 1);
          swapped = true;
        }
      }
      
      // If no elements were swapped in the inner loop, the array is already sorted, so break the loop
      if (!swapped) {
        break;
      }
    }
  }

  /**
   * Swaps two entries in the leaderboard data.
   *
   * @param leaderboardData The leaderboard data.
   * @param i               The index of the first entry.
   * @param j               The index of the second entry.
   */
  private void swapEntries(String[][] leaderboardData, int i, int j) {
    // Swap the entries by swapping their references
    String[] temp = leaderboardData[i];
    leaderboardData[i] = leaderboardData[j];
    leaderboardData[j] = temp;
  }

  /**
   * Prints the leaderboard data to the console.
   *
   * @param leaderboardData The leaderboard data to be printed.
   */
  private void printLeaderboardData(String[][] leaderboardData) {
    // Print each entry in the leaderboard data
    for (String[] entry : leaderboardData) {
      // fetch each item form the list
      // get the username from teh first index (index[0])
      String username = entry[0];
      // get the score from teh 2nd index of the csv file (index[1])
      int score = Integer.parseInt(entry[1]);
      //get the attempts from the 3rd index (index[2])
      int attempts = Integer.parseInt(entry[2]);
      
      // Print the entry details
      System.out.println("Username: " + username + ", Score: " + score + ", Attempts: " + attempts);
    }
  }

  /**
   * Prints the leaderboard by sorting it first.
   */
  @Override
  public void printLeaderboard() {
    // Read the leaderboard data from the file
    String[][] leaderboardData = readLeaderboardData();

    // Sort the leaderboard data by score
    sortLeaderboardDataByScore(leaderboardData);

    // Print the sorted leaderboard data
    printLeaderboardData(leaderboardData);
  }
}
