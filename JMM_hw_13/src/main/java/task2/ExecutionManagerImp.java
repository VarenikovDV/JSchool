package task2;

import threadpool.ScalableThreadPool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.System.out;

public class ExecutionManagerImp implements ExecutionManager {
    private final ScalableThreadPool pool;
    private int countThread = 1;
    private Semaphore semaphore;
    private final ExecutionManagerContext context = new ExecutionManagerContext();


    public ExecutionManagerImp() {
        pool = new ScalableThreadPool(countThread, countThread);
    }

    public ExecutionManagerImp(int count) {
        countThread = count;
        pool = new ScalableThreadPool(countThread, countThread);
    }

    public Context execute(Runnable callback, Runnable... tasks) {
        semaphore = new Semaphore(1-tasks.length);
        Runnable[] wrapTasks = new Runnable[tasks.length];
        Runnable callbackWrap = new Runnable() {
            @Override
            public void run() {
                try {
                    semaphore.acquire();
                    callback.run();
                } catch (InterruptedException e) {
                } finally {
                    out.println("finally CALLBACK");
                    context.setFinished(true);
                    pool.shutdown();
                }
            }

            public String toString() {
                return "callbackWrap";
            }
        };
        pool.execute(callbackWrap);
        for (int i = 0; i < wrapTasks.length; i++) {
            //wrapTasks[i] = (Runnable) Proxy.newProxyInstance(this.getClass().getClassLoader(), new Class<?>[]{Runnable.class}, new ThreadWrap(tasks[i]));
            //pool.execute(wrapTasks[i]);
            pool.execute(new ThreadWrap(tasks[i]));
        }
        out.println(pool);
        out.println("**********************************************");
        pool.start();
        return context;

    }
    public void shutdown(){
        pool.shutdown();
    }
    public String poolInfo(){
        return pool.toString();
    };

    /**********************************************************************************************************************/

    public class ThreadWrap implements Runnable {
        Runnable runnable;

        public ThreadWrap(Runnable runnable) {
            this.runnable = runnable;
        }

        @Override
        public void run() {
            try {
                if (context.isInterrupted == false) {
                    runnable.run();
                    out.println(Thread.currentThread() + "  -->  " + this);
                    context.incrementCompletedTaskCount();
                } else {
                    context.incrementInterruptedTaskCount();
                    out.println("   IGNOR: "+Thread.currentThread() + "  -->  " + this);
                }
            } catch (Throwable e) {
                context.incrementFailedTaskCount();
            } finally {
                semaphore.release();
            }
        }

        @Override
        public String toString() {
            return "wrap(" + runnable + ")";
        }
    }


    /**********************************************************************************************************************/
/*
   public class ThreadWrap implements InvocationHandler {
        private final Object obj;
        private <T extends Runnable> ThreadWrap(T t) {
            obj = t;
        }
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Object o = null;
            try {
                if (context.isInterrupted == false) {
                    o = method.invoke(obj, args);
                    context.incrementCompletedTaskCount();
                    out.println(Thread.currentThread() + "  -->  " + this);
                } else {
                    context.incrementInterruptedTaskCount();
                    out.println("   IGNOR: "+Thread.currentThread() + "  -->  " + this);
                }
            } catch (Throwable e) {
                context.incrementFailedTaskCount();
            } finally {
                semaphore.release();
            }
            return o;
        }
    }
*/
    /**********************************************************************************************************************/
    public class ExecutionManagerContext implements Context {
        private AtomicInteger completedTaskCount;
        private AtomicInteger failedTaskCount;
        private AtomicInteger interruptedTaskCount;
        private volatile boolean isInterrupted = false;
        private volatile boolean isFinished;

        public ExecutionManagerContext() {
            completedTaskCount = new AtomicInteger(0);
            failedTaskCount = new AtomicInteger(0);
            interruptedTaskCount = new AtomicInteger(0);
            isFinished = false;
        }

        /*
         * Метод getCompletedTaskCount() возвращает количество тасков, которые на текущий момент успешно выполнились.
         * Метод getc() возвращает количество тасков, при выполнении которых произошел Exception.
         * Метод interrupt() отменяет выполнения тасков, которые еще не начали выполняться.
         * Метод getInterruptedTaskCount() возвращает количество тасков, которые не были выполены из-за отмены (вызовом предыдущего метода).
         * Метод isFinished() вернет true, если все таски были выполнены или отменены, false в противном случае.
         */

        public int getCompletedTaskCount() {
            return completedTaskCount.get();
        }

        public int getFailedTaskCount() {
            return failedTaskCount.get();
        }

        public int getInterruptedTaskCount() {
            return 0;
        }

        public void interrupt() {
            isInterrupted = true;
        }

        public boolean isFinished() {
            return isFinished;
        }

        public void incrementCompletedTaskCount() {
            this.completedTaskCount.incrementAndGet();
        }

        public void incrementFailedTaskCount() {
            this.failedTaskCount.incrementAndGet();
        }

        public void incrementInterruptedTaskCount() {
            this.interruptedTaskCount.incrementAndGet();
        }

        public void setFinished(boolean finished) {
            isFinished = finished;
        }

        public String toString() {
            return "* ExecutionManagerContext : \n"
                    + "*   completedTaskCount: " + completedTaskCount.get() + "\n"
                    + "*   failedTaskCount: " + failedTaskCount.get() + "\n"
                    + "*   interruptedTaskCount: " + interruptedTaskCount.get() + "\n"
                    + "*   isFinished: " + isFinished + "\n";
        }
    }
/**********************************************************************************************************************/
}
