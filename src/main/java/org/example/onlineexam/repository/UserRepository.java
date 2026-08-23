package org.example.onlineexam.repository;

import org.example.onlineexam.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    // Email ke zariye user ko find karne ke liye (Login ke waqt kaam aayega)
    Optional<User> findByEmail(String email);
}