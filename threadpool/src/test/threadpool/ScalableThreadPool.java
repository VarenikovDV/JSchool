package test.threadpool;

import java.util.*;
import java.util.concurrent.ExecutionException;

import static java.lang.System.out;


public class ScalableThreadPool implements ThreadPool {

    private final int minimumPoolSize;
    private final int maximumPoolSize;

    private final HashSet<Worker> workers;
    private final Queue<Runnable> taskQueue = new ArrayDeque<>();
    private final Object mainLock = new Object();
    /*
     *     RUNNING    = -1
     *     SHUTDOWN   =  0
     *     STOP       =  1
     */
    private volatile int statePool;
    private volatile int waitingThreads = 0;
    private volatile long countThreads;


    public ScalableThreadPool(int minimumPoolSize, int maximumPoolSize) {
        if (minimumPoolSize < 1 || minimumPoolSize > maximumPoolSize) throw new IllegalArgumentException();
        this.minimumPoolSize = minimumPoolSize;
        this.maximumPoolSize = maximumPoolSize;
        statePool = 1;
        workers = new HashSet<>(minimumPoolSize);
        for (int i = 0; i < minimumPoolSize; i++) {
            workers.add(new Worker());
        }
    }

    @Override
    public void execute(Runnable runnable) {
        Objects.requireNonNull(runnable);
        Worker w = null;
        if (statePool != 0) {
            synchronized (mainLock) {
                taskQueue.add(runnable);
                if ((waitingThreads == 0 || (statePool == 1 && taskQueue.size() > countThreads)) && countThreads < maximumPoolSize) {
                    w = new Worker(/*taskQueue.poll()*/);
                    out.println("ADD NEW WORKER.");
                    workers.add(w);
                } else mainLock.notify();
            }
            if (statePool == -1 && w != null) w.start();
        } else throw new IllegalStateException("invalid object state for calling the procedure,pool is " + "SHUTDOWN");
    }


    @Override
    public void start() {
        if (statePool == 1) {
            out.println("**************************************");
            statePool = -1;
            workers.forEach(T -> {
                out.println("START: " + T);
                T.start();
            });
        } else
            throw new IllegalStateException("invalid object state for calling the procedure,pool is " + (statePool == 0 ? "SHUTDOWN" : "RUNNING"));
    }


    @Override
    public void shutdown() {
        if (statePool == -1) {
            synchronized (mainLock) {
                workers.forEach(T -> {
                    out.println("INTERRUPT: " + T);
                    T.interrupt();
                });
                statePool = 0;
            }
        } else
            throw new IllegalStateException("invalid object state for calling the procedure, pool is " + (statePool == 0 ? "SHUTDOWN" : "STOP"));
    }

    @Override
    public boolean isShutdown() {
        return statePool == 0 ? true : false;
    }


    private class Worker extends Thread {
        private Runnable task;

        public Worker() {
            synchronized (mainLock) {
                countThreads++;
                waitingThreads++;
            }
        }

        public Worker(Runnable runnable) {
            task = runnable;
            synchronized (mainLock) {
                countThreads++;
                waitingThreads++;
            }
        }

        @Override
        public void run() {
            boolean b = true;
            while (!isInterrupted()) {
                try {
                    synchronized (mainLock) {
                        if (b) waitingThreads--;
                        if (taskQueue.isEmpty()) {
                            if (countThreads > minimumPoolSize) {
                                workers.remove(this);
                                countThreads--;
                                out.println("INTERRUPT from RUN: " + this);
                                interrupt();
                            } else {
                                try {
                                    waitingThreads++;
                                    out.println("WAIT:" + this);
                                    mainLock.wait();
                                    out.println("WAKE UP:" + this);
                                } catch (InterruptedException e) {
                                    interrupt();
                                    out.println("WAKE UP TO INTERRUPT:" + this);
                                } finally {
                                    waitingThreads--;

                                }
                            }
                        } else {
                            task = taskQueue.poll();

                        }
                    }
                    if (task != null) {
                        out.print(waitingThreads + "  " + this + " EXECUTE :");
                        task.run();
                        task = null;
                    }
                } catch (Throwable e) {
                    e.printStackTrace();
                } finally {
                    b = false;
                }
            }
        }
    }

    @Override
    public String toString() {
        synchronized (mainLock) {
            String str = "";
            switch (statePool) {
                case -1:
                    str = "RUNNING";
                    break;
                case 0:
                    str = "SHUTDOWN";
                    break;
                case 1:
                    str = "STOP";
                    break;
            }
            return "* ScalableThreadPool : \n"
                    + "*   minimumPoolSize: " + minimumPoolSize + "\n"
                    + "*   maximumPoolSize: " + maximumPoolSize + "\n"
                    + "*   statePool: " + str + "\n"
                    + "*   waitingThreads: " + waitingThreads + "\n"
                    + "*   countThreads: " + countThreads + "\n"
                    + "*   workers: " + workers.size() + "   " + workers + "\n"
                    + "*   taskQueue: " + taskQueue.size() + "   " + taskQueue;

        }
    }


}
