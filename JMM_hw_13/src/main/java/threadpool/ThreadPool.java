package threadpool;

public interface ThreadPool {
    void start();
    void execute(Runnable runnable);
    void shutdown();
    boolean	isShutdown();
}
