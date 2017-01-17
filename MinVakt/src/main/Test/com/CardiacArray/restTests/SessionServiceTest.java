package com.CardiacArray.restTests;

import com.CardiacArray.data.User;
import com.CardiacArray.db.UserDb;
import org.junit.Test;

import static org.mockito.Mockito.*;

/**
 * Created by Vegard on 17/01/2017.
 */
public class SessionServiceTest {
    User validUser = new User("Ola", "Nordmann", 12345678, "test@test.no",
            "Passord", 0, "Testveien 2", 1, true);

    @Test
    public void login() throws Exception {
        UserDb userDb = mock(UserDb.class);
        when(userDb.getUserByEmail("test@test.no")).thenReturn(validUser);
    }

}
