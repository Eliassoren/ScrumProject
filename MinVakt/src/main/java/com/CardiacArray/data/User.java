/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.data;

import java.util.Date;
import java.sql.Timestamp;

/**
 *
 * @author OddErik
 */
public class User {
    
    /*Variabler*/
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
    

    public User(int Id, String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt, String userCategoryString, String token, Timestamp expired) {
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
    }

    /*
    *Construcor used to creat new users that needs to be added to the database.
     */
    public User(String firstName, String lastName, int mobile, String email, String password, int admin, String address, int userCategoryInt) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobile = mobile;
        this.email = email;
        this.password = password;
        this.admin = admin;
        this.address = address;
        this.userCategoryInt = userCategoryInt;
    }
    
    public User() {
        
    }
    
    /*Metoder*/
    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMobile() {
        return mobile;
    }

    public void setMobile(int phoneNumber) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAdmin() {
        return admin;
    }

    public void setAdmin(int newStatus) {
        this.admin = newStatus;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getUserCategoryInt() {
        return userCategoryInt;
    }

    public void setUserCategoryInt(int userCategoryInt) {
        this.userCategoryInt = userCategoryInt;
    }

    public String getUserCategoryString() {
        return userCategoryString;
    }

    public void setUserCategoryString(String userCategoryString) {
        this.userCategoryString = userCategoryString;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setExpired(Timestamp expired) {
        this.expired = expired;
    }

    public Timestamp getExpired() {
        return expired;
    }
}
