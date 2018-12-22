package com.auto.test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

/**
 * @author auto.yin[auto.yin@gmail.com]
 * 2018-10-18
 * @Description: <p></p>
 * Makes pizza with CountDownLatch. Pizza is made by
 * assembling rolled risen dough, tomato sauce, and grated
 * cheese on a tray and baking it.
 */
public class LatchPizzaBuilder {

    private final Executor exec;

    private final CountDownLatch doughCombined = new CountDownLatch(1);
    private final CountDownLatch doughRisen = new CountDownLatch(1);
    private final CountDownLatch layerReady = new CountDownLatch(3);

    /* @GuardedBy("doughCombined") */ private String rawDough;
    /* @GuardedBy("doughRisen")    */ private String risenDough;
    /* @GuardedBy("layerReady")    */ private String crust;
    /* @GuardedBy("layerReady")    */ private String sauce;
    /* @GuardedBy("layerReady")    */ private String cheese;

    public LatchPizzaBuilder(Executor exec) {
        this.exec = exec;
    }

    RuntimeException unexpectedInterruption() {
        return new RuntimeException("Punting on unexpected interruption");
    }

    /**
     * Fakes work and traces progress.
     */
    void work(long millis, String action, CountDownLatch waitFor, Supplier<String> input,
              Consumer<String> task, CountDownLatch ready) {
        exec.execute(() -> {
            try {
                if (waitFor != null)
                    waitFor.await();
                String in = input.get();
                System.out.println("Started " + action + " " + in);
                MILLISECONDS.sleep(millis);
                task.accept(in);
                System.out.println("Finished " + action + " " + in);
                ready.countDown();
            } catch (InterruptedException ex) {
                throw unexpectedInterruption();
            }
        });
    }

    void combine(Consumer<String> task, CountDownLatch ready, String... ingredients) {
        work(120 * ingredients.length, "combining", null,
                () -> String.join(", ", ingredients), task, ready);
    }

    void letRise() {
        work(100, "letting rise", doughCombined, () -> rawDough,
                t -> {
                    risenDough = "risen " + t;
                }, doughRisen);
    }

    void rollOut() {
        work(50, "rolling out", doughRisen, () -> risenDough,
                t -> {
                    crust = "rolled-out " + t;
                }, layerReady);
    }

    void grateCheese() {
        work(400, "grating", null, () -> "cheese",
                t -> {
                    cheese = "grated " + t;
                }, layerReady);
    }

    String buildPizza() {
        combine(t -> {
            rawDough = "{" + t + "}";
        }, doughCombined, "flour", "water", "yeast");
        letRise();
        combine(t -> {
            sauce = "{" + t + "}";
        }, layerReady, "tomato", "oil", "garlic", "oregano");
        grateCheese();
        rollOut();
        try {
            layerReady.await(); // Wait for all three layers to be ready.
            return cheese + " on " + sauce + " on " + crust;
        } catch (InterruptedException ex) {
            throw unexpectedInterruption();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        int CONCURRENCY = 4;
        ExecutorService exec = Executors.newFixedThreadPool(CONCURRENCY);
        try {
            LatchPizzaBuilder builder = new LatchPizzaBuilder(exec);
            String pizza = builder.buildPizza();
            System.out.println("Ready to bake " + pizza);
        } finally {
            exec.shutdown();
        }
    }
}

