package jpa.example.demo.algo;

import org.springframework.util.StopWatch;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;

/**
 * You are climbing a stair case. It takes n steps to reach to the top.
 *
 * Each time you can either climb 1 or 2 steps. In how many distinct ways can you climb to the top?
 *
 * Note: Given n will be a positive integer.
 *
 * Example 1:
 *
 * Input: 2
 * Output: 2
 * Explanation: There are two ways to climb to the top.
 * 1. 1 step + 1 step
 * 2. 2 steps
 * Example 2:
 *
 * Input: 3
 * Output: 3
 * Explanation: There are three ways to climb to the top.
 * 1. 1 step + 1 step + 1 step
 * 2. 1 step + 2 steps
 * 3. 2 steps + 1 step
 */
public class WRONGClimbingStairsNNNNNTimeExceed {

    public static void main(String[] args) {
        Solution solution = new Solution();
        SolutionB solutionB = new SolutionB();
        SolutionC solutionC = new SolutionC();
//        System.out.println(solution.climbStairs(2) + " / " + 2);
//        System.out.println(solution.climbStairs(3) + " / " + 3);

        // check elapsed time
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("A");
        System.out.println(solution.climbStairs(38) + " / " + 63245986);
        stopWatch.stop();
        stopWatch.start("B");
        System.out.println(solutionB.climbStairs(38) + " / " + 63245986);
        stopWatch.stop();
        stopWatch.start("C");
        System.out.println(solutionC.climbStairs(38) + " / " + 63245986);
        stopWatch.stop();

        System.out.println(stopWatch.prettyPrint());
    }
    static class Solution {
        // too slow
        int sol = 0;
        public int climbStairs(int n) {
            if (n < 2) return 1;
            int[] nums = {1, 2};
            ArrayList<Integer> result = new ArrayList<>();
            backtrack(nums, 0, result, n);
            return sol;
        }
        private void backtrack(int[] nums, int index, List<Integer> result, int target) {
            if (index >= nums.length) return;
            int sum = sum(result);
            if (sum == target) {
                sol++;
                return;
            } else if (sum > target) {
                return ;
            }

            for (int i = 0; i < nums.length; i++) {
                result.add(nums[i]);
                backtrack(nums, i, result, target);
                result.remove(result.size() - 1);
            }

        }

        private int sum(List<Integer> list) {
            int sum = 0;
            for (Integer i : list) {
                sum += i;
            }
            return sum;
        }
    }

    static class SolutionB {
        public int climbStairs(int n) {
            return climb_Stairs(0, n);
        }
        public int climb_Stairs(int i, int n) {
            if (i > n) {
                return 0;
            }
            if (i == n) {
                return 1;
            }
            return climb_Stairs(i + 1, n) + climb_Stairs(i + 2, n);
        }
    }

    static class SolutionC {
        public int climbStairs(int n) {
            int[] memo = new int[n];
            return climb_Stairs(0, n, memo);
        }
        public int climb_Stairs(int i, int n, int[] memo) {
            if (i > n) {
                return 0;
            }
            if (i == n) {
                return 1;
            }
            if (memo[i] > 0) {
                return memo[i];
            }
            memo[i] = climb_Stairs(i + 1, n, memo) + climb_Stairs(i + 2, n, memo);
            return memo[i];
        }
    }

}

