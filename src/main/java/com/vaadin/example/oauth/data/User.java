package com.vaadin.example.oauth.data;

public class User {

    private String username;
    private String email;
    private String token;
    private Integer tokenExpires;

    public User(String username, String email, String token, Integer tokenExpires) {
        this.username = username;
        this.email = email;
        this.token = token;
        this.tokenExpires = tokenExpires;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTokenExpires() {
        return tokenExpires;
    }

    public void setTokenExpires(Integer tokenExpires) {
        this.tokenExpires = tokenExpires;
    }
}
