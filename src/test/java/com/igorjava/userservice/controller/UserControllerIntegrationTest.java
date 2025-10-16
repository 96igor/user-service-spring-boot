package com.igorjava.userservice.controller;

import com.igorjava.userservice.UserServiceApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = UserServiceApplication.class)
@AutoConfigureMockMvc
class UserControllerIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void createAndGet() throws Exception {
        String body = """
      {
        "firstName":"Ivan",
        "lastName":"Ivanov",
        "birthDate":"1990-05-10",
        "email":"ivan@example.com"
      }
    """;

        // create
        var createResult = mvc.perform(post("/api/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andExpect(jsonPath("$.ageInDays").exists())
                .andReturn();

        String resp = createResult.getResponse().getContentAsString();
        // можно парсить id, но для краткости — получим список и проверим
        mvc.perform(get("/api/users")).andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName").value("Ivan"));
    }
}
