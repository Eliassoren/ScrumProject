package com.CardiacArray.restTests;

import com.CardiacArray.data.Login;
import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.rest.LoginService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

/**
 * Created by Vegard on 17/01/2017.
 */
public class LoginServiceTest {
    User validUser = new User(1, "Ola", "Nordmann", 12345678, "test@test.no",
            "fd2fa59c22d8353ea4e7b3a51329d27eac86ebeaea0a5e653515035c8e51309d", false, "Testveien 2", 1, true);

    @Test
    public void loginSuccess() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        LoginService loginService = new LoginService(userDb);
        Login login = loginService.login("test@test.no", "Passord");
        assertEquals(1, login.getId());
        assertTrue(login.getToken() instanceof String);

    }
}
