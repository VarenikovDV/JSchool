package test.exceptions;

import test.exceptions.exceptions.AccountIsLockedException;
import test.exceptions.exceptions.TestException;

import java.io.IOException;

public interface Terminal {
    Double getAccountBalance() throws AccountIsLockedException, TestException, IOException;
    boolean putCash() throws AccountIsLockedException, TestException, IOException ;
    boolean getCash() throws AccountIsLockedException, TestException, IOException;
}
