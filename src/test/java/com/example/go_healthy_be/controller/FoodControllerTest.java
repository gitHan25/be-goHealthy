package com.example.go_healthy_be.controller;


import static org.junit.jupiter.api.Assertions.*;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.TimeZone;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;


import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateFoodConsumptionRequest;
import com.example.go_healthy_be.model.FoodConsumptionResponse;
import com.example.go_healthy_be.model.TokenResponse;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.repository.FoodConsumptionRepository;
import com.example.go_healthy_be.repository.UserRepository;
import com.example.go_healthy_be.security.BCrypt;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
@SpringBootTest

@AutoConfigureMockMvc
public class FoodControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private FoodConsumptionRepository foodConsumptionRepository;
    
    @Autowired
  private  ObjectMapper objectMapper;


   @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
        foodConsumptionRepository.deleteAll();
        userRepository.deleteAll();
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
    void CreateFoodConsumptionBadRequest() throws Exception {
        CreateFoodConsumptionRequest request = new CreateFoodConsumptionRequest();
        request.setFoodName("");
        request.setConsumptionDate(null);
        request.setCalories(-1.0);
        request.setQuantity(-2);

        mockMvc.perform(
            post("/api/food-consumption")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(request))
            .header("X-API-TOKEN", "test")
            ).andExpectAll(
                status().isBadRequest()
            
            ).andDo(result -> {
                WebResponse<String> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
           
                assertNotNull(response.getErrors());
            });
        }
        @Test
void CreateFoodConsumptionSucces() throws Exception {
    // Atur timezone ke UTC
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

    CreateFoodConsumptionRequest request = new CreateFoodConsumptionRequest();
    request.setFoodName("Nasi goreng");

    // Parsing string tanggal
    String dateString = "31-12-2023"; // Format DD-MM-YYYY
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    dateFormat.setTimeZone(TimeZone.getTimeZone("UTC")); // Pastikan parsing menggunakan UTC
    java.util.Date utilDate = dateFormat.parse(dateString);
    java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
    request.setConsumptionDate(sqlDate);

    request.setQuantity(2);
    request.setCalories(100.0);

    mockMvc.perform(
            post("/api/food-consumption")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request))
                .header("X-API-TOKEN", "test")
        )
        .andExpectAll(
            status().isOk()
        )
        .andDo(result -> {
            // Membaca respons JSON
            WebResponse<FoodConsumptionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

            // Validasi hasil
            assertNull(response.getErrors());
            assertEquals("Nasi goreng", response.getData().getFoodName());
            assertEquals(2, response.getData().getQuantity());
            assertEquals("31-12-2023", new SimpleDateFormat("dd-MM-yyyy").format(response.getData().getConsumptionDate()));
            assertEquals(100.0, response.getData().getCalories());
            assertTrue(foodConsumptionRepository.existsById(response.getData().getFood_id()));
        });
}

        
}
