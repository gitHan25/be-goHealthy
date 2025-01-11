package com.example.go_healthy_be.controller;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateScheduleRequest;
import com.example.go_healthy_be.model.ScheduleResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.repository.ScheduleRepository;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest

@AutoConfigureMockMvc
public class ScheduleControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private ScheduleRepository scheduleRepository;

        @Autowired
        private ObjectMapper objectMapper; 
      
        @BeforeEach
        void setUp() {
         
            scheduleRepository.deleteAll();
            userRepository.deleteAll(); 
            
         
            TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
        
            
            User user = new User();
            user.setUsername("test");
            user.setEmail("test@gmail.com");
            user.setPassword(BCrypt.hashpw("rahasia", BCrypt.gensalt()));
            user.setName("Test");
            user.setToken("test");
            user.setTokenExpiredAt(System.currentTimeMillis() + 1000000000L);
            userRepository.save(user);
        }
        
       

        @Test
        void CreateScheduleBadRequest() throws Exception{
                CreateScheduleRequest request = new CreateScheduleRequest();
                request.setScheduleName("");
                request.setScheduleDescription("");
                request.setScheduleTime(null);
                request.setScheduleType("");
                
                mockMvc.perform(
                    post("/api/schedule")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header("X-API-TOKEN","test")
                ).andExpectAll(
                    status().isBadRequest()
                ).andDo(result ->{
                    WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
           
                    assertNotNull(response.getErrors());
                });
        
            }

            @Test
            void CreateScheduleSuccess() throws Exception{
                CreateScheduleRequest request = new CreateScheduleRequest();
                request.setScheduleName("Test");
                request.setScheduleDescription("Test");
                 String dateString = "31-12-2023"; 
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);
    request.setScheduleTime(date.atStartOfDay());
               
                request.setScheduleType("Test");
                
                mockMvc.perform(
                    post("/api/schedule")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(request))
                    .header("X-API-TOKEN","test")
                ).andExpectAll(
                    status().isOk()
                ).andDo(result ->{
                    WebResponse<ScheduleResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
           
                    assertNotNull(response.getData());
                    assertNull(response.getErrors());
                    assertEquals("Test", response.getData().getScheduleName());
                    assertEquals("Test", response.getData().getScheduleDescription());
                    assertEquals("Test", response.getData().getScheduleType());
                    DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    assertEquals("31-12-2023", response.getData().getScheduleTime().format(responseFormatter));
                    assertTrue(scheduleRepository.existsById(response.getData().getScheduleId()));
                });
        
            }

            @Test
            void getScheduleNotFound() throws Exception{
                
                
            }
    }
