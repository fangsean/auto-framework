package com.auto.algorithm.topk;

import java.util.*;

/**
 * @author jsen.yin[jsen.yin@gmail.com]
 * 2019-03-13
 * @Description: <p></p>
 */
public class MapTopK {

    public List<Integer> topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if (!map.containsKey(nums[i])) {
                map.put(nums[i], 1);
            } else {
                map.put(nums[i], map.get(nums[i]) + 1);
            }
        }
        //按照get出来的元素对应的次数排序
        PriorityQueue<Integer> priorityQueue = new PriorityQueue<>(k, (o1,o2)-> {
                return map.get(o1).compareTo(map.get(o2));
        });
        //将元素加入小顶堆
        for (Map.Entry<Integer, Integer> integerIntegerEntry : map.entrySet()) {
            if (priorityQueue.size() < k) {
                priorityQueue.offer(integerIntegerEntry.getKey());
            } else if (integerIntegerEntry.getValue() > map.get(priorityQueue.peek())) {
                //比较元素出现的次数，如果次数比小顶堆的堆顶大，则取而代之，自己做堆顶
                priorityQueue.poll();
                priorityQueue.offer(integerIntegerEntry.getKey());
            }
        }
        List<Integer> list = new ArrayList<>(k);
        list.addAll(priorityQueue);
        return list;
    }


    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3, 5, 5, 5, 3};

        List list = (new MapTopK()).topKFrequent(nums, 2);
        System.out.println(list);
    }

}
