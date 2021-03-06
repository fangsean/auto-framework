package com.auto.test;

import com.auto.concurrence.atomic.AtomicObject;

public class DataCount {

    private static /*volatile*/ int count;

    public static void main(String[] args) {

        count = 0;
        AtomicObject atomicObject = new AtomicObject();


        for (int i = 0; i < 10; i++) {
            int finalI = i;
            Thread thread = new Thread(() -> {
                count++;
//                if (finalI == 2){
                Thread.yield();
//                }
                System.out.println(finalI);
                atomicObject.getAndIncrement();
            });
//            thread.setPriority(10);
            thread.start();
            /*try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }*/
        }

        Thread thread = new Thread(() -> {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(count);
            System.out.println(atomicObject.get());
        });
        thread.start();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            System.out.println("============");
        }).start();

        int[][][][][] ints = new int[2][2][2][3][3];

    }


}
