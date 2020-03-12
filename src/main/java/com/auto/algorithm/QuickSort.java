package com.auto.algorithm;

/**
 * @Description: <p>配套算法</p>
 */
public class QuickSort {

    /**
     * 快速排序
     *
     * @param arr   查询数组
     * @param left  开始坐标
     * @param right 末端坐标
     */
    public void qKSort(int[] arr, int left, int right) {
        if (arr.length == 0 || left >= right) {
            return;
        }

        int i = left, j = right, side = i + (j-i) >> 1;
        int middle = arr[side];

        while (i < j) {

            while (arr[i] < middle) {
                i++;
            }

            while (arr[j] > middle) {
                j--;
            }

            if (i == j) {
                ++i;
            } else if (i < j) {
                int tmp = arr[i];
                arr[i] = arr[j];
                arr[j] = tmp;
                i++;
                j--;
            }

        }

        qKSort(arr, i, right);
        qKSort(arr, left, j);
    }


    /**
     * 二分查询
     *
     * @param arr    有序数组
     * @param search 查询数值
     * @param left   开始坐标
     * @param right  末端坐标
     * @return 坐标
     */
    public int binaryChop(int[] arr, int search, int left, int right) {

        if (arr.length == 0 || left > right) {
            return -1;
        }

        int i = left, j = right, side = (i + j) >> 1;

        if (arr[side] > search) {
            return binaryChop(arr, search, i, side - 1);
        } else if (arr[side] == search) {
            return side;
        } else {
            return binaryChop(arr, search, side + 1, j);
        }

    }

}
