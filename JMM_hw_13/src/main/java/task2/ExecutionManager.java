package task2;

public interface ExecutionManager {
    Context execute(Runnable callback, Runnable... tasks);
    public void shutdown();
    public String poolInfo();
}
