import java.util.Arrays;

/*
 * 329. Longest Increasing Path in a Matrix
 */
class Leetcode329 {
  public int longestIncreasingPath(int[][] matrix) {
    
      // It is used for memorization of values 
      int dp[][] = new int[matrix.length][matrix[0].length];
      
      //It is used to check if some index is already visited
      boolean[][] visited = new boolean[matrix.length][matrix[0].length];
      for (int i = 0 ; i < dp.length;  i++) {
          Arrays.fill(dp[i], -1);
      }
      
      // Recursively checking all location to find longest increasing path
      int ret = 0;
      for (int i = 0 ; i < matrix.length; i++) {
          for (int j = 0 ; j < matrix[0].length; j++) {
              if (dp[i][j] == -1) {
                  int val = rec(matrix, dp, i, j, -1, visited);
                  if (val > ret) {
                      ret = val;
                  }
              }                
          }
      }
      return ret;
  }
  
  // Recursion on 4 sides of a location in matrix
  public int rec (int[][] matrix, int[][] dp, int i, int j, int prev, boolean [][] visited) {
      visited[i][j] = true;
      if (matrix[i][j] <= prev) {
          visited[i][j] = false;
          return 0;
      } else if (dp[i][j] != -1) {
           visited[i][j] = false;
           return dp[i][j];
      } 
      else {
          dp[i][j] = 1;
          int r = matrix.length;
          int c = matrix[0].length;
          
          //check up
          int ret = 0;
          if (i-1 > -1 && !visited[i-1][j]) {
              ret = rec(matrix, dp, i - 1, j, matrix[i][j], visited);
              if (ret + 1 > dp[i][j]) {
                  dp[i][j] = ret + 1;
              }
          }

          //check down
          if (i+1 < r && !visited[i+1][j]) {
              ret = rec(matrix, dp, i+1, j, matrix[i][j], visited);
              if (ret + 1 > dp[i][j]) {
                  dp[i][j] = ret + 1;
              }
          }

          //check left
          if (j-1 > -1 && !visited[i][j-1]) {
              ret = rec(matrix, dp, i, j-1, matrix[i][j], visited);
              if (ret + 1 > dp[i][j]) {
                  dp[i][j] = ret + 1;
              }
          }

          //check right 
          if (j+1 < c && !visited[i][j+1]) {
              ret = rec(matrix, dp, i ,j +1, matrix[i][j], visited);
              if (ret + 1 > dp[i][j]) {
                  dp[i][j] = ret + 1;
              }
          }
      }
      visited[i][j] = false;
      return dp[i][j];
  }
}