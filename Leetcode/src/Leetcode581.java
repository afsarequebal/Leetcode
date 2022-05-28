import java.util.Arrays;

class Leetcode581 {
  public int findUnsortedSubarray(int[] nums) {
    //Array to contain unordered pairs
    int[] vals = new int[nums.length];
    Arrays.fill(vals, -1);
    
    //Compare each elements with previous elements to check reverse-order.
    int max = nums[0];
    for (int i = 1 ; i < nums.length ; i++) {
      int k = 1;
      int j = i-1;
      if(max < nums[i]) {
        max = nums[i];
      }
      while (j > -1 && nums[i] < nums[j] ) {
        vals[j] = k; 
        k++;j--;
      }
      //Check if all current elements are equals and are in reverse-order
      if (j > 0 && vals[j-1] != -1 && nums[i] == nums[j]) {
        vals[j] = 1;
      }
      
      //Check if some element is lesser than maximum element
      if (i-1>-1&& nums[i] < max && vals[i-1] ==-1) {
        vals[i-1] = 1;
      }
    }
    
    int i = 0; int j = nums.length-1;

    //Finding first and last element where order is reversed
    while (i < j ) {
      if (vals[j] == -1) {
        j--;
      } 
      if (vals[i] == -1) {
        i++;
      } 
      if (vals[j] != -1 && vals[i] != -1) {
        break;
      }
    }
    if (i <= j && vals[j] != -1 && vals[i] != -1 ) {
      return j - i + 2;
    }
    return 0;
  }
}
