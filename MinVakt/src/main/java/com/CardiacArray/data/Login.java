package com.CardiacArray.data;


public class Login {

    private int id;
    private String token;
    private boolean loggedIn;

    public Login(int id, String token, boolean loggedIn) {
        this.id = id;
        this.token = token;
        this.loggedIn = loggedIn;
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

    public boolean isLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean loggedIn) {
        this.loggedIn = loggedIn;
    }
}
