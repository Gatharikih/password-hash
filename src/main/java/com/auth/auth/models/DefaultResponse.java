package com.auth.auth.models;

/*
 * @The Script 09/2022
 * */
public class DefaultResponse {
    private final String message;

    public DefaultResponse(String message){
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
