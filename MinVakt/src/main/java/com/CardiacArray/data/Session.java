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
public class Session {
    
    /*Variabler*/
    Date loginDate;
    
    /*Konstruktør*/
    public Session(Date loginDate) {
        this.loginDate = loginDate;
    }
    
    /*Metoder*/
    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }  
}
