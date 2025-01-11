package com.example.go_healthy_be.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.go_healthy_be.entity.FoodConsumption;
import com.example.go_healthy_be.entity.User;
import com.example.go_healthy_be.model.CreateFoodConsumptionRequest;
import com.example.go_healthy_be.model.FoodConsumptionResponse;
import com.example.go_healthy_be.model.UpdateFoodRequest;
import com.example.go_healthy_be.model.WebResponse;
import com.example.go_healthy_be.repository.FoodConsumptionRepository;
import com.example.go_healthy_be.repository.ScheduleRepository;
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
    private ScheduleRepository scheduleRepository;
    @Autowired
  private  ObjectMapper objectMapper;


   @BeforeEach
    void setUp() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC")); 
        
        foodConsumptionRepository.deleteAll();
        scheduleRepository.deleteAll();
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
void CreateFoodConsumptionSuccess() throws Exception {
    CreateFoodConsumptionRequest request = new CreateFoodConsumptionRequest();
    request.setFoodName("Nasi goreng");

    // Parsing string tanggal langsung ke LocalDateTime
    String dateString = "31-12-2023"; // Format DD-MM-YYYY
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate date = LocalDate.parse(dateString, formatter);
    request.setConsumptionDate(date.atStartOfDay()); // Atur waktu ke 00:00

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
            DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            assertEquals("31-12-2023", response.getData().getConsumptionDate().format(responseFormatter));
            assertEquals(100.0, response.getData().getCalories());
            assertTrue(foodConsumptionRepository.existsById(response.getData().getFoodId()));
        });
}
@Test
void getFoodConsumptionNotFound() throws Exception {
    

    mockMvc.perform(
            get("/api/food-consumption/123123123")
                .contentType(MediaType.APPLICATION_JSON)
              
                .header("X-API-TOKEN", "test")
        )
        .andExpectAll(
            status().isNotFound()
        )
        .andDo(result -> {
           
            WebResponse<FoodConsumptionResponse> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});

           
            assertNotNull(response.getErrors());
         
        });
    }
    @Test
    void getFoodConsumptionSucces() throws Exception {
    
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow();
    
        // Buat objek FoodConsumption untuk testing
        FoodConsumption foodConsumption = new FoodConsumption();
        foodConsumption.setFoodId(UUID.randomUUID().toString());
        foodConsumption.setUser(user);
        foodConsumption.setFoodName("Nasi goreng");
    
      
        LocalDateTime consumptionDate = LocalDateTime.now();
        foodConsumption.setConsumptionDate(consumptionDate);
        foodConsumption.setQuantity(2);
        foodConsumption.setCalories(100.0);
        foodConsumptionRepository.save(foodConsumption);
    
        // Lakukan pengujian API
        mockMvc.perform(
                get("/api/food-consumption/" + foodConsumption.getFoodId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        )
        .andExpectAll(
                status().isOk()
        )
        .andDo(result -> {
            
            WebResponse<FoodConsumptionResponse> response = objectMapper.readValue(
                    result.getResponse().getContentAsString(),
                    new TypeReference<>() {}
            );
    
         
            System.out.println("Expected Date: " + consumptionDate);
            System.out.println("Response Date: " + response.getData().getConsumptionDate());
    
            assertNull(response.getErrors());
            assertEquals(foodConsumption.getFoodId(), response.getData().getFoodId());
            assertEquals(foodConsumption.getFoodName(), response.getData().getFoodName());
    
            
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String expectedDate = consumptionDate.format(formatter);
            String responseDate = response.getData().getConsumptionDate().format(formatter);
            assertEquals(expectedDate, responseDate);
    
            assertEquals(100.0, response.getData().getCalories());
            assertTrue(foodConsumptionRepository.existsById(response.getData().getFoodId()));
        });
    }
    @Test
    void getAllFoodConsumptionByUserIdSuccess() throws Exception {
        // Ambil user untuk testing
        User user = userRepository.findByEmail("test@gmail.com").orElseThrow();

        // Buat objek FoodConsumption untuk testing
        FoodConsumption foodConsumption1 = new FoodConsumption();
        foodConsumption1.setFoodId(UUID.randomUUID().toString());
        foodConsumption1.setUser(user);
        foodConsumption1.setFoodName("Nasi goreng");
        foodConsumption1.setConsumptionDate(LocalDateTime.now());
        foodConsumption1.setQuantity(2);
        foodConsumption1.setCalories(100.0);
        foodConsumptionRepository.save(foodConsumption1);

        FoodConsumption foodConsumption2 = new FoodConsumption();
        foodConsumption2.setFoodId(UUID.randomUUID().toString());
        foodConsumption2.setUser(user);
        foodConsumption2.setFoodName("Ayam Bakar");
        foodConsumption2.setConsumptionDate(LocalDateTime.now());
        foodConsumption2.setQuantity(1);
        foodConsumption2.setCalories(300.0);
        foodConsumptionRepository.save(foodConsumption2);

        mockMvc.perform(
                get("/api/users/food-consumption")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .header("X-API-TOKEN", "test")
        ).andExpect(status().isOk())
        .andDo(result -> {
            WebResponse<List<FoodConsumptionResponse>> response = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<>() {});
            assertNull(response.getErrors());
            assertEquals(2, response.getData().size());

            FoodConsumptionResponse foodResponse1 = response.getData().get(1);
            assertEquals("Nasi goreng", foodResponse1.getFoodName());
            assertEquals(100.0, foodResponse1.getCalories());
            assertEquals(2, foodResponse1.getQuantity());

            FoodConsumptionResponse foodResponse2 = response.getData().get(0);
            assertEquals("Ayam Bakar", foodResponse2.getFoodName());
            assertEquals(300.0, foodResponse2.getCalories());
            assertEquals(1, foodResponse2.getQuantity());
        });


    }
    @Test
    void UpdateFoodConsumptionBadRequest() throws Exception {
        UpdateFoodRequest request = new UpdateFoodRequest();
        request.setFoodName("");
        request.setConsumptionDate(null);
        request.setCalories(-1.0);
        request.setQuantity(-2);

        mockMvc.perform(
            put("/api/food-consumption/1234")
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
        void UpdateFoodConsumptionSucces() throws Exception {
            User user = userRepository.findByEmail("test@gmail.com").orElseThrow();
            FoodConsumption foodConsumption = new FoodConsumption();
        foodConsumption.setFoodId(UUID.randomUUID().toString());
        foodConsumption.setUser(user);
        foodConsumption.setFoodName("Nasi goreng");
    
        // Tetapkan tanggal konsumsi
        LocalDateTime consumptionDate = LocalDateTime.now();
        foodConsumption.setConsumptionDate(consumptionDate);
        foodConsumption.setQuantity(2);
        foodConsumption.setCalories(100.0);
        foodConsumptionRepository.save(foodConsumption);
            UpdateFoodRequest request = new UpdateFoodRequest();
            request.setFoodName("Nasi Lemak");
        
            // Parsing string tanggal langsung ke LocalDateTime
            String dateString = "31-12-2024"; // Format DD-MM-YYYY
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate date = LocalDate.parse(dateString, formatter);
            request.setConsumptionDate(date.atStartOfDay()); // Atur waktu ke 00:00
        
            request.setQuantity(1);
            request.setCalories(50.0);
        
            mockMvc.perform(
                    put("/api/food-consumption/"+ foodConsumption.getFoodId())
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
                    assertEquals(request.getFoodName(), response.getData().getFoodName());
                    assertEquals(request.getQuantity(), response.getData().getQuantity());
                    DateTimeFormatter responseFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                    assertEquals(request.getConsumptionDate().format(responseFormatter), response.getData().getConsumptionDate().format(responseFormatter));
                    assertEquals(request.getCalories(), response.getData().getCalories());
                    assertTrue(foodConsumptionRepository.existsById(response.getData().getFoodId()));
                });
        }
@Test
void deleteFoodByIdSuccess() throws Exception {
    User user = userRepository.findByEmail("test@gmail.com").orElseThrow();
    FoodConsumption foodConsumption = new FoodConsumption();
    foodConsumption.setFoodId(UUID.randomUUID().toString());
    foodConsumption.setUser(user);
    foodConsumption.setFoodName("Nasi goreng");
    LocalDateTime consumptionDate = LocalDateTime.now();
    foodConsumption.setConsumptionDate(consumptionDate);
    foodConsumption.setQuantity(2);
    foodConsumption.setCalories(100.0);
    foodConsumptionRepository.save(foodConsumption);

    mockMvc.perform(
             delete("/api/food-consumption/" + foodConsumption.getFoodId())
            .header("X-API-TOKEN", "test")
            .accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andDo(result -> {
            WebResponse<String> response = objectMapper.readValue(
                result.getResponse().getContentAsString(), 
                new TypeReference<>() {}
            );
            assertNull(response.getErrors());
            assertEquals("OK", response.getData());

            assertFalse(foodConsumptionRepository.existsById(foodConsumption.getFoodId()));

        });
}
}
        
        
        
    
        

