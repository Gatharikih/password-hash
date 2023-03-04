package com.auth.auth.controllers;

import com.auth.auth.models.DefaultResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {
    private int responseStatus;
    private String responseBody;
    Logger logger = LogManager.getLogger(MainController.class);

    /**
     * endpoint to hash a password
     *
     * @param passwdBody - String
     *
     * @return return HTTP Response - DefaultResponse object - with hashed password
     */
    @PostMapping("/passwd/hash")
    ResponseEntity<DefaultResponse> hashPassword(@RequestBody Object passwdBody) {
        try {
            logger.error(passwdBody);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode passwordBodyJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(passwdBody));

            String passwd = passwordBodyJsonNode.get("passwd").asText();
            String hashedPasswd = _hashPassword(passwd);

            responseStatus = 200;
            responseBody = hashedPasswd;
        }catch (Exception e){
            logger.error(e.getMessage());
            logger.traceEntry(e.getMessage());

            return handleAllExceptions(e);
        }

        return ResponseEntity.status(responseStatus).body(new DefaultResponse(responseBody));
    }

    /**
     * endpoint to compare a plain text password and a password hash
     *
     * @param passwdBody - JSON Object
     *
     * @return return HTTP Response - DefaultResponse object - with hashed password
     */
    @PostMapping("/passwd/confirm")
    ResponseEntity<DefaultResponse> comparePassword(@RequestBody Object passwdBody) {
        try {
            logger.error(passwdBody);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode passwordBodyJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(passwdBody));

            String passwd = passwordBodyJsonNode.get("passwd").asText();
            String passwdHash = passwordBodyJsonNode.get("hash").asText();

            boolean match = _comparePassword(passwd, passwdHash);

            if (match){
                responseStatus = 200;
                responseBody = "true";
            }else{
                responseStatus = 401;
                responseBody = "false";
            }
        }catch (Exception e){
            logger.error(e);

            return handleAllExceptions(e);
        }

        return ResponseEntity.status(responseStatus).body(new DefaultResponse(responseBody));
    }

    /**
     * endpoint to update password
     *
     * @param passwdBody - JSON Object
     *
     * @return return HTTP Response - DefaultResponse object - with hashed password
     */
    @PostMapping("/passwd/update")
    ResponseEntity<DefaultResponse> updatePassword(@RequestBody Object passwdBody) {
        try {
            logger.error(passwdBody);

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode passwordBodyJsonNode = objectMapper.readTree(objectMapper.writeValueAsString(passwdBody));

            String passwd = passwordBodyJsonNode.get("passwd").asText();
            String passwdHash = passwordBodyJsonNode.get("hash").asText();
            String newPasswd = passwordBodyJsonNode.get("new_passwd").asText();

            boolean match = _comparePassword(passwd, passwdHash);

            if (match){
                String newHashedPasswd = _hashPassword(newPasswd);

                responseStatus = 200;
                responseBody = newHashedPasswd;
            }else{
                responseStatus = 401;
                responseBody = "Password is incorrect";
            }
        }catch (Exception e){
            logger.error(e);

            return handleAllExceptions(e);
        }

        return ResponseEntity.status(responseStatus).body(new DefaultResponse(responseBody));
    }

    /**
     *  Catch any instance of Exception within the endpoint functions
     * */
    @ExceptionHandler(Exception.class)
    public final ResponseEntity<DefaultResponse> handleAllExceptions(Exception exception) {
        return ResponseEntity.status(500).body(new DefaultResponse(exception.getMessage()));
    }

    private String _hashPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt(10));
    }

    private boolean _comparePassword(String password, String passwordHash){
        return BCrypt.checkpw(password, passwordHash);
    }
}