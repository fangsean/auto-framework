package com.auto.algorithm.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * Description:
 *
 * @author: fang.sheng
 * Version: 1.0
 * Date: 2019-09-07
 * @since JDK 1.8
 */
public class TwoSum {

    public static int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> sum = new HashMap();
        for (int i = 0; i < nums.length; i++) {
            if (sum.containsKey(target - nums[i])) {
                int[] integers = {sum.get(target - nums[i]), i};
                return integers;
            }
            sum.put(nums[i], i);
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 9;
        System.out.println(twoSum(nums, target));

    }
}
    
    