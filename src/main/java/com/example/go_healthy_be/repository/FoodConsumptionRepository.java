package com.example.go_healthy_be.repository;
import com.example.go_healthy_be.entity.FoodConsumption;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
@Repository
public interface FoodConsumptionRepository extends  JpaRepository<FoodConsumption,String> {

}
