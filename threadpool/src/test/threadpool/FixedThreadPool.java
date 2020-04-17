package test.threadpool;

public class FixedThreadPool extends ScalableThreadPool {
    public FixedThreadPool(int i){
        super(i,i);
    }
}
