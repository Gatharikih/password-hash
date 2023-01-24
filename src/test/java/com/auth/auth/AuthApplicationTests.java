package com.auth.auth;

import com.auth.auth.controllers.MainController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class AuthApplicationTests {
    @Autowired
    private MainController controller;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

}
