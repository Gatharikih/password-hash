package com.auth.auth;

import com.auth.auth.controllers.MainController;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
//@ExtendWith({SpringExtension.class})
//@WebMvcTest(MainController.class)
@AutoConfigureMockMvc
class AuthApplicationTests {
    @Autowired
    private MainController controller;
    Gson gson = new Gson();

    @Autowired
    private MockMvc mvc;

    @Test
    void contextLoads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void givenPlainTxtPassword_whenHashPwd_thenStatus200() throws Exception{
//        mvc.perform(request).andExpect(status().is4xxClientError());
        JsonObject body = new JsonObject();
        body.addProperty("passwd", "test_password");

        RequestBuilder request = MockMvcRequestBuilders.post("/passwd/hash")
                .content(gson.toJson(body))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("application/json", result.getResponse().getContentType());
    }

    @Test
    void givenPlainTxtPasswordAndPwdHash_whenCompared_thenStatus200() throws Exception{
        JsonObject body = new JsonObject();
        body.addProperty("passwd", "test_password");
        body.addProperty("hash", "$2a$10$lpA7h4WThFaGWnlRJam1NebYTp5chpcTeQ5XJ0RiPwWQVGJLk3sYW");

        RequestBuilder request = MockMvcRequestBuilders.post("/passwd/confirm")
                .content(gson.toJson(body))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("application/json", result.getResponse().getContentType());
    }

    @Test
    void givenPlainTxtPwdAndPwdHashAndNewPwd_whenCompared_thenStatus200AndReturnNewPwdHash() throws Exception{
        JsonObject body = new JsonObject();
        body.addProperty("passwd", "test_password");
        body.addProperty("hash", "$2a$10$lpA7h4WThFaGWnlRJam1NebYTp5chpcTeQ5XJ0RiPwWQVGJLk3sYW");
        body.addProperty("new_passwd", "new_test_password");

        RequestBuilder request = MockMvcRequestBuilders.post("/passwd/update")
                .content(gson.toJson(body))
                .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mvc.perform(request).andReturn();

        Assertions.assertEquals(200, result.getResponse().getStatus());
        Assertions.assertEquals("application/json", result.getResponse().getContentType());
    }

}
