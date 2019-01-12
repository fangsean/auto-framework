package com.auto.wiki.model;

import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-09
 * @Description: <p></p>
 */
public class IntegerAccumulator {

    private int init;

    public IntegerAccumulator(int init) {
        this.init = init;
    }


    public int setInit(int init) {
        this.init += init;
        return init;
    }

    public int getInit() {
        return init;
    }


    public static void main(String[] args) {

        IntegerAccumulator integerAccumulator = new IntegerAccumulator(1);

        IntStream.range(0, 10).forEach(i -> {
            new Thread(() -> {

                int c = 0;
                while (true) {

                    int init = integerAccumulator.getInit();

                    int n = integerAccumulator.setInit(c);

                    System.out.println(init + " + " + c + "=" + n);
                    if (init + c != n) {
                        System.out.println("ERROR:" + init + " + " + c + "=" + n);
                    }
                    c++;

                    try {
                        TimeUnit.SECONDS.sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }).start();
        });


    }


}
