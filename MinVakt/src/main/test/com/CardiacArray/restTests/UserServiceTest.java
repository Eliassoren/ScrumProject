package com.CardiacArray.restTests;

import com.CardiacArray.restService.data.User;
import com.CardiacArray.restService.db.UserDb;
import com.CardiacArray.restService.rest.UserService;
import org.junit.Assert;
import org.junit.Test;

import javax.validation.constraints.AssertTrue;
import javax.ws.rs.BadRequestException;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by Odd Erik on 16.01.2017.
 */
public class UserServiceTest {

    @Test
    public void getUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        //String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt, boolean active
        User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        when(mockUserDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        Assert.assertEquals(service.getUser("test@test.no").getFirstName(), "Ola");
    }

    @Test(expected = javax.ws.rs.NotFoundException.class)
    public void getInvalidUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User invalidUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        when(mockUserDb.getUserByEmail("test@test.no")).thenReturn(invalidUser);
        service.getUser("test@test.no");
    }

    @Test
    public void updateUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User updatedUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        when(mockUserDb.updateUser(updatedUser)).thenReturn(true);
        Assert.assertEquals(true, service.updateUser(updatedUser));
    }

    @Test(expected = BadRequestException.class)
    public void updateUserFailed() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User updatedUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        when(mockUserDb.updateUser(updatedUser)).thenReturn(false);
        service.updateUser(updatedUser);
    }

    @Test
    public void createUser() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        User returnUser = new User();
        returnUser.setEmail("test@test.no");
        when(mockUserDb.getUserByEmail("test@test.no")).thenReturn(null);
        System.out.println(service.createUser(validUser).getStatus());
        Assert.assertTrue((service.createUser(validUser)).getStatus() == 200);
    }

    @Test
    public void createUserFailed() throws Exception {
        UserDb mockUserDb = mock(UserDb.class);
        UserService service = new UserService((mockUserDb));
        User invalidUser = new User("Ola", null, 12345678, "test@test.no",
                "Passord", false, "Testveien 2", 1, true, 100);
        when(mockUserDb.getUserByEmail("test@test.no")).thenReturn(invalidUser);
        Assert.assertTrue((service.createUser(invalidUser)).getStatus() == 400);
    }

}
