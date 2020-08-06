package com.tatu.essay.model;

public class TokenInfo {

    private String token;

    private String username;


    public TokenInfo() {
    }

    public TokenInfo(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }


}