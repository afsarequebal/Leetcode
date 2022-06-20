import java.util.Arrays;

/**
 *
 * Given an input string, return length of longest valid parantheses string
 * @author afsarequebal
 *
 */
class Leetcode32 {

  public static int longestValidParentheses(String s) {
    int ret = 0; 

    // To use dynamic programming, we find solution of smaller problems. Then make a cut at an index
    // and derive a solution using smaller subproblems. 

    int dp[] = new int[s.length()];
    
    //Initailize all values to zero
    Arrays.fill(dp, 0);

    for (int i = 1 ; i < s.length(); i++) {
      if (s.charAt(i) ==')') {
        if (s.charAt(i-1) == '(') {
          if (i >=2) {
            dp[i] = dp[i-2] + 2;
          } else {
            dp[i] = 2;
          }
        } else {
          //find index of matching closing parentheses
          int matchingIdx = i - dp[i-1] - 1;
          if (matchingIdx >= 0 && s.charAt(matchingIdx) =='(') {
            if (matchingIdx >= 1) {
              dp[i] = 2 + dp[i-1] + dp[matchingIdx - 1];
            } else {
              dp[i] = 2 + dp[i-1];
            }
          }
        }
      } 
      if(ret < dp[i]) ret = dp[i];
    }
    return ret;
  }

  public static void main(String[] args) {
    System.out.println(longestValidParentheses("()())()()()"));
  }
}
