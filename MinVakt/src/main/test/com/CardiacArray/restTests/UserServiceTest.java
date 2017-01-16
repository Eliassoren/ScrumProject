package com.CardiacArray.restTests;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.rest.UserService;
import javassist.NotFoundException;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.BadRequestException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by OddErik on 16.01.2017.
 */
public class UserServiceTest {

    @Test
    public void getUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        //String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt, boolean active
        User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.getUser("test@test.no")).thenReturn(validUser);
        Assert.assertEquals(service.getUser("test@test.no").getFirstName(), "Ola");
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void getInvalidUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User invalidUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.getUser("test@test.no")).thenReturn(invalidUser);
        service.getUser("test@test.no");
    }

    @Test
    public void updateUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User updatedUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.updateUser(updatedUser)).thenReturn(true);
        Assert.assertEquals(true, service.updateUser(updatedUser));
    }

    @Test(expected = BadRequestException.class)
    public void updateUserFailed() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User updatedUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.updateUser(updatedUser)).thenReturn(false);
        service.updateUser(updatedUser);
    }

    @Test
    public void createUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.getUser("test@test.no")).thenReturn(new User());
        Assert.assertTrue(service.createUser(validUser));
    }

    @Test(expected = BadRequestException.class)
    public void createUserFailed() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User invalidUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", 0, "Testveien 2", 1, true);
        when(mockUserDb.getUser("test@test.no")).thenReturn(invalidUser);
        service.createUser(invalidUser);
    }

}