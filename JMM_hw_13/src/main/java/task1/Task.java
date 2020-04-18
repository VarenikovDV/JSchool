package task1;

import java.util.concurrent.Callable;

public class Task<T> {

    private final Callable<? extends T> callable;
    private T result;
    /*  stateTask:
     *  -1 - EXCEPTION
     *   0 - DEFAULT
     *   1 - call() is computes successfully
     */
    private volatile int stateTask = 0;
    private CallableException exp;


    public Task(Callable<? extends T> callable) {
        this.callable = callable;
    }

    public T get() {
        //T temp;
        if (stateTask == 1) {
            return result;
        } else if (stateTask == -1) {
            throw exp;
        }
        synchronized (this) {
            if (stateTask == 1) {
                return result;
            } else if (stateTask == -1) {
                throw exp;
            }
            try {
                result = callable.call();
                stateTask = 1;
                return result;
            } catch (Exception e) {
                exp = new CallableException("", e);
                stateTask = -1;
                throw exp;
            }

        }
    }

    public static final class CallableException extends RuntimeException {
        public CallableException(String str, Exception e) {
            super(str, e);
        }
    }
}
