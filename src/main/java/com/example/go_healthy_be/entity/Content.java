package com.example.go_healthy_be.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "content")
public class Content {
  @Id
  @Column(name = "id_content")
  private  String contentId;
  private String title;

  private String bodyContent;
  private LocalDateTime created_at;

}
