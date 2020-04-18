import task1.Task;
import task2.Context;
import task2.ExecutionManager;
import task2.ExecutionManagerImp;

import java.util.Random;

import static java.lang.System.out;
import static java.lang.Thread.sleep;

/*
 *
 *
 *
 *
 *
 *
 *
 * */
public class Main {
    public static void main(String[] arg) throws InterruptedException {
        out.println("***************************************");
        int countTask = 20;
        TestTask[] arrayTask = new TestTask[countTask];
        for (int i = 0; i < countTask; i++) {
            arrayTask[i] = new TestTask();
        }

        ExecutionManager em = new ExecutionManagerImp(5);
        Context context = em.execute(()->out.println("executing callback"), arrayTask);
        out.println("**************************************");
        out.println(context);
        sleep(300);
        out.println(context);
        context.interrupt();
        sleep(500);
        out.println(context);
        sleep(1000);
        out.println(em.poolInfo());




    }

    /**********************************************************************************************************************/
    public static class TestTask implements Runnable {
        private static int count = 0;
        private final int id;
        private final int sleeping;
        private static Random random = new Random();

        public TestTask() {
            count++;
            id = count;
            sleeping = random.nextInt(1000);
        }

        @Override
        public void run() {
            try {
                sleep(random.nextInt(sleeping));
            } catch (InterruptedException e) {
                //e.printStackTrace();
            }
        }

        @Override
        public String toString() {
            return "TestTask - " + id + " sleep: " + sleeping;
        }

    }
}
