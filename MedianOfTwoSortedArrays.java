package jpa.example.demo.algo;

/**
 * There are two sorted arrays nums1 and nums2 of size m and n respectively.
 *
 * Find the median of the two sorted arrays. The overall run time complexity should be O(log (m+n)).
 *
 * You may assume nums1 and nums2 cannot be both empty.
 *
 * Example 1:
 *
 * nums1 = [1, 3]
 * nums2 = [2]
 *
 * 1, 2, 3
 * 4
 * 0 -> [1] -> 1 -> [1, 2] -> 2
 *
 *
 *
 * The median is 2.0
 * Example 2:
 *
 * nums1 = [1, 2]
 * nums2 = [3, 4]
 *
 * The median is (2 + 3)/2 = 2.5
 */
public class MedianOfTwoSortedArrays {
    public static void main(String[] args) {
        Solution solution = new Solution();

//        int[] nums1 = {1, 3};
//        int[] nums2 = {2};
//        int[] nums1 = {1, 2};
//        int[] nums2 = {3, 4};
//        int[] nums1 = {};
//        int[] nums2 = {1};
//        int[] nums1 = {};
//        int[] nums2 = {2, 3};
//        int[] nums1 = {1};
//        int[] nums2 = {2, 3};
//        int[] nums1 = {1};
//        int[] nums2 = {2, 3, 4};
//        int[] nums1 = {3};
//        int[] nums2 = {-2, -1};
        int[] nums1 = {1};
        int[] nums2 = {1};

        System.out.println(solution.findMedianSortedArrays(nums1, nums2));
//        System.out.println(3/2);
    }

    static class Solution {
        public double findMedianSortedArrays(int[] nums1, int[] nums2) {
            int length1 = nums1.length;
            if (length1 == 0) {
                return findMedian(nums2);
            }
            int length2 = nums2.length;
            if (length2 == 0) {
                return findMedian(nums1);
            }

            int totalLength = length1 + length2;
            boolean isEven = totalLength % 2 == 0;
            int medianIndex = totalLength / 2;
            int index = 0, fp = 0, sp = 0;
            int[] sub = new int[totalLength];
            while (index < medianIndex) {
                if (fp < nums1.length && getResultValue(nums1, fp) < getResultValue(nums2, sp)) {
                    sub[index++] = nums1[fp++];
                } else if (sp < nums2.length && getResultValue(nums1, fp) > getResultValue(nums2, sp)) {
                    sub[index++] = nums2[sp++];
                } else {
                    sub[index++] = nums1[fp++];
                    sub[index++] = nums2[sp++];
                }
            }

            if (!isEven) {
                return Math.min(getResultValue(nums1, fp), getResultValue(nums2, sp));
            }
            if (index <= medianIndex) {
                return (sub[medianIndex - 1] + Math.min(getResultValue(nums1, fp), getResultValue(nums2, sp))) / 2.0d;
            }
            return getResultValue(sub, index / 2);
        }

        private int getResultValue(int[] nums, int fp) {
            if (fp >= nums.length) {
                return Integer.MAX_VALUE;
            }
            return nums[fp];
        }

        private double findMedian(int[] nums) {
            boolean isEven = nums.length % 2 == 0;
            int medianIndex = nums.length / 2;
            if (!isEven) {
                return nums[medianIndex];
            }
            return (nums[medianIndex - 1] + nums[medianIndex]) / 2.0d;
        }
    }
}
