package com.auto.active.mq;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * @author jsen.yin [jsen.yin@gmail.com]
 * 2019-01-20
 * @Description: <p></p>
 */
public class ActiveFuture<T> extends FutureTask<T> {

    public ActiveFuture(Callable<T> callable) {
        super(callable);
    }

    public ActiveFuture(Runnable runnable, T result) {
        super(runnable, result);
    }

}
