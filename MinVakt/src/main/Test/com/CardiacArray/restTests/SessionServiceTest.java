package com.CardiacArray.restTests;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import com.CardiacArray.rest.PasswordUtil;
import com.CardiacArray.rest.SessionService;
import org.junit.Test;

import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Created by Vegard on 17/01/2017.
 */
public class SessionServiceTest {
    User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
            "fd2fa59c22d8353ea4e7b3a51329d27eac86ebeaea0a5e653515035c8e51309d", 0, "Testveien 2", 1, true);

    @Test
    public void loginSuccess() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        SessionService sessionService = new SessionService(userDb);
        Response response = sessionService.login("test@test.no", "Passord");
        assertEquals(200, response.getStatus());

    }

    @Test
    public void loginWrongPass() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        SessionService sessionService = new SessionService(userDb);
        Response response = sessionService.login("test@test.no", "Pasord");
        assertEquals(401, response.getStatus());

    }

    @Test
    public void loginWrongUser() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
        SessionService sessionService = new SessionService(userDb);
        Response response = sessionService.login("te@test.no", "Passord");
        assertEquals(401, response.getStatus());

    }

}
