package soc.network;

import common.interfaces.SearchInt;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;


import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class UserTest {

    User user = new User();

    @Mock
    private SearchInt searchIntMock;

    @Test
    public void userExist(){
        when(searchIntMock.CheckUser(user)).thenReturn(true);
        assertTrue(user.userExist(searchIntMock));
    }

    @Test
    public void getUserName() {
        user.setUserName("user10");
        assertEquals(user.getUserName(), "user10");
    }

    @Test
    public void getUserAge() {
        user.setUserAge(100);
        assertEquals(user.getUserAge(), 100);
    }

}