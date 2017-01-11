/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.CardiacArray.data;

import java.util.Date;

/**
 *
 * @author OddErik
 */
public class User {
    
    /*Variabler*/
    private int Id;
    private String firstName;
    private String lastName;
    private int phoneNumber;
    private String email;
    private String password;
    private int admin;
    private Shift shift = new Shift(int time, Date date, int departmentId, String role, int shiftId, boolean tradeable, boolean shiftManager);
    
    /*Konstrukt�r*/
    public User(int Id, String firstName, String lastName, int phoneNumber, String email, String password, int admin) {
        this.Id = Id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.password = password;
        this.admin = admin;
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

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
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

    public Shift getShift() {
        return shift;
    }
        
    public void setShiftTradeable(boolean newStatus) {
        shift.setTradeable(newStatus);
    }
    
    

    public static void main(String[] args) {
        // TODO code application logic here
    }
}
