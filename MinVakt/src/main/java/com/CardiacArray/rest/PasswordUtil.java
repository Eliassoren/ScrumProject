package com.CardiacArray.rest;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Utility class for generating and verifying passwords.
 * Created by Audun on 10.03.2016.
 */
public class PasswordUtil {
    /**
     * Creates hashed password
     *
     * @param password user password
     * @param firstName user's first name for salt-string
     * @return hashed password as string
     */
    public String hashPassword(String password, String firstName) {
        String salt = new StringBuilder(firstName).reverse().toString();
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update((password + salt).getBytes());
            byte[] hashedPassword = md.digest();
            StringBuffer sb = new StringBuffer();
            for(byte b : hashedPassword) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
        }
        return null;
    }

    /**
     * Verifies email and password from user
     *
     * @param givenPassword input password
     * @param firstName user's first name
     * @param userPassword hashed password from database
     * @return boolean true if verify succeeded, false else
     */
    public boolean verifyPassword(String givenPassword, String firstName, String userPassword) {
        String inputHashed = hashPassword(givenPassword,firstName);
        return inputHashed.equals(userPassword);
    }
}
