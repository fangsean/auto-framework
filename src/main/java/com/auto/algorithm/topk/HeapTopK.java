package com.auto.algorithm.topk;

import com.auto.algorithm.stack.LinkedStack;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-13
 * @Description: <p></p>
 */
public class HeapTopK {

    public static int[] topK(int[] nums, int k) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(k);//队列默认自然顺序排列,小顶堆,不必重写compare

        for (int num : nums) {
            if (pq.size() < k) {
                pq.offer(num);
            } else if (pq.peek() < num) {//如果堆顶元素 < 新数,则删除堆顶,加入新数入堆
                pq.poll();
                pq.offer(num);
            }
        }
        int[] result = new int[k];
        for (int i = 0; i < k&&!pq.isEmpty(); i++) {
            result[i] = pq.poll();
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = {4,5,1,6,2,7,3,8};
        System.out.println(Arrays.toString(topK(nums, 3)));

    }


}
