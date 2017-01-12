package com.CardiacArray.dbTests;

import com.CardiacArray.db.SessionDb;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by kjosavik on 12-Jan-17.
 */
public class SessionDbTest{

    @Test
    public void testLogin() throws Exception {
        String[][] user = {{"1","epost@epsot.no", "123"},{"0", "email@email.com", "321"}};
        String[][] input = {{user[0][1],user[0][2]},{user[1][1],user[1][2]}};
        int[] result = {Integer.parseInt(user[0][0]),Integer.parseInt(user[1][0])};
        SessionDb sessionDb = mock(SessionDb.class);
        when(sessionDb.login(user[0][1],user[0][2])).thenReturn(result[0]);
        when(sessionDb.login(user[1][1],user[1][2])).thenReturn(result[1]);
        when(sessionDb.login(user[0][1],user[1][2])).thenReturn(-1);

        assertEquals(Integer.parseInt(user[0][0]),sessionDb.login(user[0][1],user[0][2]));//correct
        assertEquals(Integer.parseInt(user[1][0]),sessionDb.login(user[1][1],user[1][2]));//correct
        assertEquals(-1,sessionDb.login(user[0][1],user[1][2]));//incorrect password

    }

}