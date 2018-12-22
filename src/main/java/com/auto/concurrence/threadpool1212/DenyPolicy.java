package com.auto.concurrence.threadpool1212;

@FunctionalInterface
public interface DenyPolicy {

    void reject(Runnable runnable, ThreadPool threadPool);

    class DiscardDenyPolicy implements DenyPolicy {
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            // ...
        }
    }

    class AbortDenyPolicy implements DenyPolicy {
        /**
         * 抛出异常
         *
         * @param runnable
         * @param threadPool
         */
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            throw new RunnableDenyException("this thread " + runnable + " will be abort.");
        }
    }

    class RunnerDenyPolicy implements DenyPolicy {

        /**
         * 在提交者线程中执行任务
         *
         * @param runnable
         * @param threadPool
         */
        @Override
        public void reject(Runnable runnable, ThreadPool threadPool) {
            if (!threadPool.isShutDown()) {
                runnable.run();
            }
        }
    }


}
