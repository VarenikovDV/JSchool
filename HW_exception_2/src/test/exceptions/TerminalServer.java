package test.exceptions;

import test.exceptions.exceptions.TestException;

import java.io.IOException;
import java.util.Random;

public class TerminalServer {
    public TerminalServer(){}

    public static boolean  checkPin(String strPin){
        return  Math.floorMod(Integer.valueOf(strPin),2)== 0 ? true:false;
    }

    public static Integer getAccountBalance() throws TestException  {
        Random r = new Random();
        return  r.nextInt(1_000_000);
    }

    static public boolean putCash(Integer i) throws TestException {
        Random r = new Random();
        if(Math.floorMod(r.nextInt(1_000),3)!=0){
            return true;
        }
        else {
            throw new TestException(" Bad internet connection, try again later. ");
        }
    }

    public static boolean  getCash(Integer i) throws TestException {
        Random r = new Random();
        if(Math.floorMod(r.nextInt(1_000),2)!=0){
            return true;
        }
        else {
            throw new TestException(" Bad internet connection, try again later. ");
        }
    }
}
