package test.exceptions.exceptions;

public class AccountIsLockedException extends  Exception{
    public AccountIsLockedException(String message){
        super(message);
    }
    public AccountIsLockedException(){}
}
