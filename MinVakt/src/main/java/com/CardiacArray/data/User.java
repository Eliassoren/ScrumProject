/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.data;

import java.sql.Timestamp;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Odd Erik
 */
public class User {

    private int Id;
    private String firstName;
    private String lastName;
    private int mobile;
    private String email;
    private String password;
    private int admin;
    private String address;
    private int userCategoryInt;
    private String userCategoryString;
    private String token;
    private Timestamp expired;
    private boolean active; //false if user does not work at the facility anymore.


    public User(int Id, String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt, String userCategoryString, String token, Timestamp expired, boolean active) {
        this.Id = Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.address = address;
        this.userCategoryInt = userCategoryInt;
        this.userCategoryString = userCategoryString;
        this.token = token;
        this.expired = expired;
        this.active = active;
    }

    /*
     *Constructor used to create new users that needs to be added to the database.
     */
    public User(String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt, boolean active) {
        //if (firstName == null || lastName == null || email == null || password == null) throw new IllegalArgumentException("Name, password or email cannot be null");

        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.address = address;
        this.userCategoryInt = userCategoryInt;
        this.active = active;
    }

    
    public User() {
        
    }

    /**
     *
     * @return the user id
     */

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public int getId() {
        return Id;
    }

    /**
     * Sets the id of the user
     * @param Id The id of the user
     */
    public void setId(int Id) {
        this.Id = Id;
    }

    /**
     *
     * @return String representation of the user's first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets the first name of the user
     * @param firstName first name of the user
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     *
     * @return String representation of the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the last name of the user
     * @param lastName last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     *
     * @return int representation of the user's phone number
     */
    public int getMobile() {
        return mobile;
    }

    /**
     * Sets the phone number of the user
     * @param phoneNumber phone number of the user
     */
    public void setMobile(int phoneNumber) {
        this.mobile = mobile;
    }

    /**
     *
     * @return String representation of the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets the email of the user
     * @param email email of the user
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     *
     * @return String representation of the user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets the password of the user
     * @param password password of the user
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     * @return 1 if the user is admin and 0 if the user is not
     */
    public int getAdmin() {
        return admin;
    }

    /**
     * Sets admin privileges
     * @param newStatus 1 is the user is admin and 0 if the user is not
     */
    public void setAdmin(int newStatus) {
        this.admin = newStatus;
    }

    /**
     *
     * @return String representation of the user's address
     */
    public String getAddress() {
        return address;
    }

    /**
     * Sets the address of the user
     * @param address adress of the user
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     *
     * @return id of the user's category
     */
    public int getUserCategoryInt() {
        return userCategoryInt;
    }

    /**
     * Sets the int representation of user's category
     * @param userCategoryInt Id of the category
     */
    public void setUserCategoryInt(int userCategoryInt) {
        this.userCategoryInt = userCategoryInt;
    }

    /**
     *
     * @return String representation of the user's category
     */
    public String getUserCategoryString() {
        return userCategoryString;
    }

    /**
     * Sets the user category as String
     * @param userCategoryString String representation of the user's category
     */
    public void setUserCategoryString(String userCategoryString) {
        this.userCategoryString = userCategoryString;
    }

    /**
     * Sets the token of the user
     * @param token token of the user
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     *
     * @return String representation of the user's token
     */
    public String getToken() {
        return token;
    }

    /**
     * Sets at which time token will expire
     * @param expired expire time
     */
    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    /**
     *
     * @return time when token expires
     */
    public Timestamp getExpired() {
        return expired;
    }

    /**
     *
     * @param email email of the user
     * @return true if email is valid and false if not
     */
    public boolean isValidEmail(String email) {
        if(email != null) {
            String regex = "^(.+)[@](.+)[.](.+)$";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(email);

            return m.find();
        }

        return false;
    }
}
