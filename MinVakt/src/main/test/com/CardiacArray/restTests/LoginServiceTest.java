package com.CardiacArray.restTests;

import com.CardiacArray.data.Login;
import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.rest.LoginService;
import org.junit.Test;

import javax.ws.rs.NotAuthorizedException;
import javax.ws.rs.core.Response;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Vegard on 17/01/2017.
 */
public class LoginServiceTest {
    User validUser = new User(1, "Ola", "Nordmann", 12345678, "test@test.no",
            "wrong", false, "Testveien 2", 1, true);

    @Test(expected = NotAuthorizedException.class)
    public void loginFails() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        LoginService loginService = new LoginService(userDb);
        Login login = loginService.login("test@test.no", "password");
    }
}