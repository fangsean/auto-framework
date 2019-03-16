package com.auto.algorithm.topk;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2018-12-23
 * @Description: <p>最优\最快topk</p>
 * https://blog.csdn.net/u010452388/article/details/81283998
 */
public class SortTopK {

    /**
     * @param n
     * @return
     */
    private int parent(int n) {
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

    private void swap(int[] data, int a, int b) {
        int tmp;
        tmp = data[a];
        data[a] = data[b];
        data[b] = tmp;
    }

    /**
     * @param data
     * @param n
     */
    private void buildHeap(int[] data, int n) {
        for (int i = 1; i < n; i++) {
            int t = i;
            while (t != 0 && data[parent(t)] > data[t]) {
                swap(data, t, parent(t));
                t = parent(t);
            }
        }
    }

    private void adjustHeap(int[] data, int i, int n) {

        if (data[i] <= data[0]) {
            return;
        }

        //swap
        swap(data, i, 0);

        //adjust
        int t = 0;
        //big root heap
        while (left(t) < n && data[t] > data[left(t)] || (right(t) < n && data[t] > data[right(t)])) {
            if (right(t) < n && data[right(t)] < data[left(t)]) {
                swap(data, t, right(t));
                t = right(t);
            } else {
                swap(data, t, left(t));
                t = left(t);
            }
        }
        //small root heap
    }

    public void queryTopk(int[] data, int n) {

        buildHeap(data, n);

        for (int i = n; i < data.length; i++) {
            adjustHeap(data, i, n);
        }

    }

    public static void display(int[] data) {
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i] + " ");
        }
        System.out.println("\r\n");
    }

    public void adjust(int[] num,int s,int n){  //由于根节点编号是从0开始的，所以其左子树是2*i+1
        for(int i=s;i<n;){
            int max = i;
            if((2*i+1)<n&&num[2*i+1]>num[max]) max = 2*i+1;
            if((2*i+2)<n&&num[2*i+2]>num[max]) max = 2*i+2;
            if(max!=i){
                swap(num,i,max);
                i=max;
            }else break;

        }

    }

    public void heapSort(int[] num) {
        int n = num.length;
        for (int i = n / 2; i >= 0; i--) {   //建堆，从n/2开始向上调整
            adjust(num, i, n);
        }
        for (int j = n - 1; j >= 0; j--) {
            swap(num, 0, j);         //交换开始和最后位置上的数字
            adjust(num, 0, j);    //交换之后再调整堆
        }
    }


    public static void main(String[] args) {

        SortTopK top = new SortTopK();

        int k = 3;
        int[] data = {1, 1, 1, 2, 2, 3, 5, 5, 5, 3};

        display(data);

        if (k > 0 && k <= data.length - 1) {
            top.queryTopk(data, k);
            top.heapSort(data);
            display(data);
        } else {
            System.out.println("Are You Kidding Me?");
        }

    }

}
