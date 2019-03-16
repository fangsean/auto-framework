package com.auto.test;

public class DataCount {

    private static /*volatile*/ int count;

    public static void main(String[] args) {

        count = 0;

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                count++;
            });
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(()->{
            System.out.println("============");
        }).start();

        int[][][][][] ints = new int[2][2][2][3][3];


    }


}
