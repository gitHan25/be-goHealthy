package com.example.go_healthy_be.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.*;
import com.example.go_healthy_be.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc

class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        // Bersihkan data user sebelum setiap tes
        userRepository.deleteAll();
    }

    @Test
    void testRegisterBadReq() throws Exception {
        User user = new User();
        user.setUsername("test");
      
        user.setEmail("editya@gmail.com");
        user.setPassword("rahasia");
        user.setName("Test");
        userRepository.save(user);

        // Membuat request untuk registrasi user baru
        RegisterUserRequest request = new RegisterUserRequest();
        request.setUsername("test");
        request.setEmail("editya@gmail.com");
        request.setPassword("rahasia");
        request.setName("Test");

       
        mockMvc.perform(
                post("/api/users")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpectAll(
            status().isBadRequest()
           
        ).andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNotNull(response.getErrors());
        });
    }
}
