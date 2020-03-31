package soc.network;


import common.interfaces.SearchInt;

import java.util.*;

public class User {

    private String userName;
    private int userAge;

    public User() {
    }

    public User(String userName, int userAge ) {
        this.userAge=userAge;
        this.userName=userName;
    }

    public String getUserName() {
        return userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }


    public boolean userExist(SearchInt searchInt){
        //soc.network.search.Search.SearchInt searchInt = new Search();
        return searchInt.CheckUser(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return userAge == user.userAge &&
                Objects.equals(userName, user.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, userAge);
    }
}
