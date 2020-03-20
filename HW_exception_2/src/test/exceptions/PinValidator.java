package test.exceptions;

import test.exceptions.exceptions.AccountIsLockedException;
import test.exceptions.exceptions.TestException;

import java.awt.*;
import java.io.*;
import java.util.Scanner;

public class PinValidator {
    private static final String regexPin = "[0-9]{4}";
    private static final int attemptsCount = 3;
    private static boolean block = false;
    private static final long lock = 10000 ;
    private static long timeLock;


    protected static  FilterInputStream systemIn = new FilterInputStream(System.in){
                                                                        @Override
                                                                        public void close() throws IOException {
                                                                            //don't close System.in!
                                                                        }
                                                                        };

    private static  Runnable runLock= ()->{
                                            try {
                                                Thread.sleep(lock);
                                            }
                                            catch (InterruptedException e) {
                                                e.printStackTrace();
                                            }
                                            TerminalImpl.setBlockState(false);
                                            PinValidator.setBlockState(false);
                                          };

    private static void setBlockState(boolean b){
        block=b;
    }




    public static boolean requestPin() throws AccountIsLockedException, IOException, TestException {
        if (block == true) {
            double l = (lock -System.currentTimeMillis()+timeLock)/1000;
            throw new AccountIsLockedException("Enter the pin has been blocked, it will be unlocked through "+l+" ss");
        }
        int aCount = attemptsCount;
        try (Scanner scan = new Scanner(systemIn)) {
            System.out.print("   Enter PIN: ");
            String strPin;
            while (aCount > 0) {

                strPin = scan.nextLine();

                if (strPin.matches("[0-9]{4}") && TerminalServer.checkPin(strPin)) {
                    System.out.println("   PIN ok");
                    TerminalImpl.setBlockState(false);
                    return true;
                } else if(--aCount>0){
                    System.out.print("   PIN invalid, try again:");
                }
                ;
            }
            TerminalImpl.setBlockState(true);
            PinValidator.setBlockState(true);
            timeLock=System.currentTimeMillis();
            Thread t = new Thread(runLock);
            t.start();
            throw new TestException("You entered the wrong pin code 3 times.");
        }
    }
}
