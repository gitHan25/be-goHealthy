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
import com.example.go_healthy_be.security.BCrypt;

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

    @Test

    void getUserUnauthorized()throws Exception{
        mockMvc.perform(
            get("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
            .header("X-API-TOKEN", "notfound")
        ).andExpectAll(
            status().isUnauthorized()
        ).andDo(result ->{
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(response.getErrors());
        });
    }

    @Test

    void getUserUnauthorizedTokenNotSend()throws Exception{
        mockMvc.perform(
            get("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
         
        ).andExpectAll(
            status().isUnauthorized()
        ).andDo(result ->{
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(response.getErrors());
        });
    }
    @Test

    void getUserSucces()throws Exception{
        User user  = new User();
        user.setUsername("test");
      
        user.setEmail("editya@gmail.com");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setUsername("test");
        user.setName("Test");
        user.setToken("Test");
        user.setTokenExpiredAt(System.currentTimeMillis()+100000000L);
        userRepository.save(user);
        mockMvc.perform(
            get("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
            .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result ->{
        WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNull(response.getErrors());
        
        assertEquals("editya@gmail.com",response.getData().getEmail());
        assertEquals("test",response.getData().getUsername());
        
        });
    }
    @Test
    void getUserTokenExp()throws Exception{
        User user  = new User();
        user.setUsername("test");
      
        user.setEmail("editya@gmail.com");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setUsername("test");
        user.setName("Test");
        user.setToken("Test");
        user.setTokenExpiredAt(System.currentTimeMillis()-100000000L);
        userRepository.save(user);
        mockMvc.perform(
            get("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
            .header("X-API-TOKEN", "test")
        ).andExpectAll(
            status().isUnauthorized()
        ).andDo(result ->{
        WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

        assertNotNull(response.getErrors());
        

        });
    }

    @Test
    void updateUserUnauthorized()throws Exception{
        UpdateUserRequest request = new UpdateUserRequest();
        mockMvc.perform(
            patch("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
         
        ).andExpectAll(
            status().isUnauthorized()
        ).andDo(result ->{
        WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNotNull(response.getErrors());
        });
    }
    @Test
    void updateUserSucces()throws Exception{
        User user = new User();
        user.setEmail("han@gmail.com");
        user.setUsername("test");
        user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
        user.setUsername("test");
        user.setName("Test");
        user.setToken("Test");
        user.setTokenExpiredAt(System.currentTimeMillis()+100000000L);
        userRepository.save(user);
        UpdateUserRequest request = new UpdateUserRequest();
        request.setName("Farhan");
        request.setPassword("farhan25");
        request.setUsername("han25");
        mockMvc.perform(
            patch("/api/users/current")
            .accept(MediaType.APPLICATION_JSON)
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-API-TOKEN", "Test")
        ).andExpectAll(
            status().isOk()
        ).andDo(result ->{
        WebResponse<UserResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
        assertNull(response.getErrors());
        assertEquals("Farhan",response.getData().getName());
        assertEquals("han25",response.getData().getUsername());
       
        User userDB = userRepository.findByEmail("han@gmail.com").orElse(null);
            assertNotNull(userDB);
            assertTrue(BCrypt.checkpw("farhan25", userDB.getPassword()));
        });
}
}

