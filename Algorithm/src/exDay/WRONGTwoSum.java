package jpa.example.demo.algo;

import java.util.ArrayList;
import java.util.List;

/**
 * Given an array of integers, return indices of the two numbers such that they add up to a specific target.
 *
 * You may assume that each input would have exactly one solution, and you may not use the same element twice.
 *
 * Example:
 *
 * Given nums = [2, 7, 11, 15], target = 9,
 *
 * Because nums[0] + nums[1] = 2 + 7 = 9,
 * return [0, 1].
 */
public class WRONGTwoSum {
    public static int[] twoSum(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left < right) {
            while (nums[right] > target) right--;
            if (target == nums[left] + nums[right]) {
                return new int[]{left, right};
            }
            left++;
        }

        return new int[]{};
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 4, 4};
        int target = 8;
//        int[] nums = new int[]{2, 7, 11, 15};
//        int target = 9;
        int[] result = twoSum(nums, target);
        printResult(result);
    }

    private static void printResult(int[] result) {
        List<Integer> els = new ArrayList<>();
        for (int i = 0; i < result.length; i++) {
            els.add(result[i]);
        }
        System.out.println(els);
    }
}
