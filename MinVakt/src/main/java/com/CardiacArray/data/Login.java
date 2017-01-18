package com.CardiacArray.data;


public class Login {

    private int id;
    private String token;

    public Login(int id, String token) {
        this.id = id;
        this.token = token;
    }

    public Login() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
