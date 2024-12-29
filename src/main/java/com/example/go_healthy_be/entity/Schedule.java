package com.example.go_healthy_be.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedule")
public class Schedule {

        @Id
        private String scheduleId;
        private String scheduleName;
        private String scheduleDescription;
        private LocalDateTime scheduleTime;
        private String scheduleType;
       
        
        @ManyToOne
        @JoinColumn(name = "user_id",referencedColumnName = "userId")
        private User user;




        

    
}
