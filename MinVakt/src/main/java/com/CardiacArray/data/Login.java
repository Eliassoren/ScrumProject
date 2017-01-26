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

    /**
     *
     * @return id of the login
     */
    public int getId() {
        return id;
    }

    /**
     *
     * @param id new id to be set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     *
     * @return token of the login
     */
    public String getToken() {
        return token;
    }

    /**
     *
     * @param token new token to be set
     */
    public void setToken(String token) {
        this.token = token;
    }
}
