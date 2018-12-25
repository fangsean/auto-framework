package com.auto.algorithm.topk;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-23
 * @Description: <p></p>
 */
public class PartitionTopK {


    /**
     * 快排
     *
     * @param a
     * @param low
     * @param high
     * @return
     */
    public static int partition(int a[], int low, int high) {
        a[0] = a[low];
        int pivokey = a[low];
        while (low < high) {
            while (low < high && a[high] >= pivokey) --high;
            a[low] = a[high];
            while (low < high && a[low] <= pivokey) ++low;
            a[high] = a[low];
        }
        a[low] = a[0];
        return low;
    }


    public static int selectK(int a[], int start, int end, int k) {
        int index = 0;
        if (start < end) {
            index = partition(a, start, end);
            if (index == k)//正好找到第k大的数
            {
                index = k;
            } else if (index < k)//还要从index的右边找k-index个数
            {
                index = selectK(a, index + 1, end, k - index);
            } else if (index > k)//k个数都在Index的左边
            {
                index = selectK(a, start, index - 1, k);
            }
        }
        return index;
    }

    public static void display(int a[], int k) {
        for (int i = 1; i <= k; i++) {
            System.out.print(a[i] + " ");
        }
    }

    public static void main(String args[]) {
        int k = 3;
        int a[] = {49, 38, 29, 65, 97, 76, 13, 27, 49, 22, 19};
        if (k > 0 && k <= a.length - 1) {
            selectK(a, 0, a.length - 1, k);
            display(a, k);
        } else {
            System.out.println("Are You Kidding Me?");
        }
    }

}
