package com.example.go_healthy_be.repository;
import com.example.go_healthy_be.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email); 
    Optional<User> findFirstByToken(String token);

}
