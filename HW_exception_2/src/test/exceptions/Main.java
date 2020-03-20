package test.exceptions;

import test.exceptions.exceptions.AccountIsLockedException;

import java.io.FilterInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static  FilterInputStream systemIn = new FilterInputStream(System.in){
                                                                                        @Override
                                                                                        public void close() throws IOException {
                                                                                            /*ignore*/
                                                                                        }
                                                                                    };
    public static Terminal terminal = new TerminalImpl();

    public static void main(String[] args) throws IOException {

        System.out.println ("TERMINAL\n"+
                            "   0. look account balance\n"+
                            "   1. get CASH\n"+
                            "   2. put CASH\n"+
                            "   3. exit\n");
        String str = "";
        Scanner scan = new Scanner(systemIn);
        while(str.compareTo("3")!=0) {
            try {
                System.out.print("Enter the operation number want you would like to perform : ");
                str = scan.nextLine();
                if (str.matches("[0-3]")) {
                    switch (str) {
                        case "0":
                            System.out.println("   *** Account balance : "+terminal.getAccountBalance()+" ***");
                            break;
                        case "1":
                            System.out.println( terminal.getCash()?"   ***   Successful operation ! ***":"   *** Unsuccessful operation,try again leter ! ***");
                             break;
                        case "2":
                            System.out.println(terminal.putCash()?"   ***   Successful operation ! ***":"   *** Unsuccessful operation,try again leter ! ***");
                            break;
                        case "3":
                            System.out.println("TERMINAL CLOSED");
                            break;
                    }
                } else {
                    System.out.println("invalid value, try again:");
                }
            }
            catch(Exception e){
                System.out.println("    ***Exception message : "+ e.getMessage()+"***");
            }
        }
        }
}
