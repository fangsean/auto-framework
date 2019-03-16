package com.auto.algorithm.heap;

import com.auto.algorithm.topk.SortTopK;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-23
 * @Description: <p></p>
 */
public class HeapTest {

    //堆排序
    public void heapSort(int[] arr) {
        //构造大根堆
        heapInsert(arr);
        int size = arr.length;
        while (size > 0) {
            //固定最大值
            swap(arr, 0, size - 1);
            size--;
            //构造大根堆
            heapify(arr, 0, size);
        }
    }

    //构造大根堆（通过新插入的数上升）
    public void heapInsert(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            //当前插入的索引
            int currentIndex = i;
            //父结点索引
            int fatherIndex = parent(currentIndex);
            //如果当前插入的值大于其父结点的值,则交换值，并且将索引指向父结点
            //然后继续和上面的父结点值比较，直到不大于父结点，则退出循环
            while (arr[currentIndex] > arr[fatherIndex]) {
                //交换当前结点与父结点的值
                swap(arr, currentIndex, fatherIndex);
                //将当前索引指向父索引
                currentIndex = fatherIndex;
                //重新计算当前索引的父索引
                fatherIndex = parent(currentIndex);
            }
        }
    }

    //将剩余的数构造成大根堆（通过顶端的数下降）
    public void heapify(int[] arr, int index, int size) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        while (left < size) {
            int largestIndex;
            //判断孩子中较大的值的索引（要确保右孩子在size范围之内）
            if (arr[left] < arr[right] && right < size) {
                largestIndex = right;
            } else {
                largestIndex = left;
            }
            //比较父结点的值与孩子中较大的值，并确定最大值的索引
            if (arr[index] > arr[largestIndex]) {
                largestIndex = index;
            }
            //如果父结点索引是最大值的索引，那已经是大根堆了，则退出循环
            if (index == largestIndex) {
                break;
            }
            //父结点不是最大值，与孩子中较大的值交换
            swap(arr, largestIndex, index);
            //将索引指向孩子中较大的值的索引
            index = largestIndex;
            //重新计算交换之后的孩子的索引
            left = 2 * index + 1;
            right = 2 * index + 2;
        }

    }


    /**
     * @param n
     * @return
     */
    private int parent(int n) {
        if(n - 1 <0){
            return 0;
        }
        return (n - 1) >> 1;
    }

    /**
     * @param n
     * @return
     */
    private int left(int n) {
        return n << 1 + 1;
    }

    /**
     * @param n
     * @return
     */
    private int right(int n) {
        return left(n) + 1;
    }

    //交换数组中两个元素的值
    private void swap(int[] data, int a, int b) {
        int tmp;
        tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    public static void main(String[] args) {
        int[] data = {49, 38, 29, 65, 97, 76, 13, 27, 49, 22, 19};
        HeapTest heap = new HeapTest();
        SortTopK.display(data);
        heap.heapSort(data);
        SortTopK.display(data);
    }


}
