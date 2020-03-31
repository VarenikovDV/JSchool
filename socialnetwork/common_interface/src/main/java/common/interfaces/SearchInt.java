package common.interfaces;

public interface SearchInt {
    //crutch for cyclical dependencies
    public <T> boolean CheckUser(T user);
    public <T> boolean CheckPhoto(T photo);
}