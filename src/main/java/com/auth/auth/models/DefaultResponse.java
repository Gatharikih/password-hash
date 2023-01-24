package com.auth.auth.models;
/*
 * @The Script 09/2022
 * */
public class DefaultResponse {
    private String message;

    public DefaultResponse(String message){
        this.message = message;
    }

//    getter
    public String getMessage() {
        return message;
    }
}
