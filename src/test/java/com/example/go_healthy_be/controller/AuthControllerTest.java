package com.example.go_healthy_be.controller;

import static org.junit.jupiter.api.Assertions.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.LoginUserRequest;
import com.example.go_healthy_be.model.TokenResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
@SpringBootTest
@AutoConfigureMockMvc

public class AuthControllerTest {

   
  
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp(){
        userRepository.deleteAll();
    }

@Test
void loginFailedUserNotFound() throws Exception{

    LoginUserRequest request = new LoginUserRequest();
    request.setEmail("han@gmail.com");
    request.setPassword("han");
    mockMvc.perform(
        post("/api/auth/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isUnauthorized()

    ).andDo(result -> {
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(response.getErrors());
    });
}
@Test
void loginFailedWrongPassword() throws Exception{
User user =  new User();

user.setEmail("farhan@gmail.com");

user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
user.setName("farhan");
user.setUsername("gacor akng");
userRepository.save(user);
    LoginUserRequest request = new LoginUserRequest();
    request.setEmail("han@gmail.com");
    request.setPassword("han");
    mockMvc.perform(
        post("/api/auth/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isUnauthorized()

    ).andDo(result -> {
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(response.getErrors());
    });
}
@Test
void loginSucces() throws Exception{
User user =  new User();

user.setEmail("farhan@gmail.com");

user.setPassword(BCrypt.hashpw("test", BCrypt.gensalt()));
user.setName("farhan");
user.setUsername("gacor akng");
userRepository.save(user);
    LoginUserRequest request = new LoginUserRequest();
    request.setEmail("farhan@gmail.com");
    request.setPassword("test");
    mockMvc.perform(
        post("/api/auth/login")
        .accept(MediaType.APPLICATION_JSON)
        .contentType(MediaType.APPLICATION_JSON)
        .content(objectMapper.writeValueAsString(request))
    ).andExpectAll(
        status().isOk()

    ).andDo(result -> {
        WebResponse<TokenResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNull(response.getErrors());
        assertNotNull(response.getData().getToken());
        assertNotNull(response.getData().getExpiredAt());
        Optional<User> userDb= userRepository.findByEmail("farhan@gmail.com");
        assertNotNull(userDb);
        assertEquals(userDb.get().getToken(), response.getData().getToken());
        assertEquals(userDb.get().getTokenExpiredAt(), response.getData().getExpiredAt());

    });
}

}





