package org.example.onlineexam.repository;

import org.example.onlineexam.entity.Result;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {
    // Kisi specific student ke saare results dekhne ke liye
    List<Result> findByStudentId(Long studentId);
}