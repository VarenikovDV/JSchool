package test.exceptions;

//import java.sql.Timestamp;

import test.exceptions.exceptions.AccountIsLockedException;
import test.exceptions.exceptions.TestException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Scanner;

public class TerminalImpl implements Terminal{
    private static boolean block = true;
    long startBlock = 0;

//    private final TerminalServer server;
//    private final PinValidator pinValidator;

    public static void setBlockState(boolean b){
        block = b;
    }

    @Override
    public Double getAccountBalance() throws AccountIsLockedException, TestException, IOException {
        if(PinValidator.requestPin() == true){
            return TerminalServer.getAccountBalance().doubleValue();
        }
        return Double.valueOf(0);
    }

    @Override
    public boolean putCash() throws AccountIsLockedException, TestException, IOException  {
        if(PinValidator.requestPin() == true){
            Integer cash;
            try(Scanner scan = new Scanner(Main.systemIn)) {
                while(true) {
                    System.out.print("   Enter put amount: ");
                    String str = scan.nextLine();
                    if(str.matches("^[1-9]+[0-9]*")){
                        cash=Integer.valueOf(str);
                    }
                    else{
                        System.out.println("   *** Invalid value аmount, try again later. ***");
                        return false;
                    }

                    if (Math.floorMod(cash, 100)==0) {
                        return TerminalServer.putCash(cash);
                    }
                    else {
                        System.out.println("   Amount must be a multiple of 100, enter again: ");
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean getCash() throws AccountIsLockedException, IOException, TestException {
        if(PinValidator.requestPin() == true){
            Integer cash;
            try(Scanner scan = new Scanner(Main.systemIn)) {
                while(true) {
                    System.out.print("   Enter get amount: ");
                    String str = scan.nextLine();
                    if(str.matches("^[1-9]+[0-9]*")){
                        cash=Integer.valueOf(str);
                    }
                    else{
                        System.out.println("   *** Invalid value аmount, try again later. ***");
                        return false;
                    }

                    if (Math.floorMod(cash, 100)==0) {
                        return TerminalServer.getCash(cash);
                    }
                    else {
                        System.out.println("   Amount must be a multiple of 100, enter again: ");
                    }
                }
            }
        }
        return false;
    }

}
