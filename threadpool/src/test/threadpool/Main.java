package test.threadpool;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

import java.util.Optional;
import java.util.Random;
import java.util.concurrent.*;

import static java.lang.System.out;

import static java.lang.Thread.sleep;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        // write your code here
        ScalableThreadPool stp = new ScalableThreadPool(5, 5);

        for (int i = 0; i < 30; i++) {
            stp.execute(new TestTask());
        }
        out.println(stp);

        stp.start();
        sleep(2000);
         out.println(stp);
        for (int i = 0; i < 20; i++) {
            stp.execute(new TestTask());
            sleep(10);
        }
        sleep(2000);
        stp.shutdown();
        sleep(2000);
        out.println(stp);
        out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++");
    }

    public static class TestTask implements Runnable {
        private static int count = 0;
        private final int id;
        private final int sleeping;
        private static Random random = new Random(13);

        public TestTask() {
            count++;
            id = count;
            sleeping = random.nextInt(1000);
        }

        @Override
        public void run() {
            try {
                out.println(this);
                sleep(random.nextInt(sleeping));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "TestTask - " + id + " sleep: " + sleeping;
        }

    }
}
