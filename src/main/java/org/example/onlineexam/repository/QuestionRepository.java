package org.example.onlineexam.repository;

import org.example.onlineexam.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // Kisi specific exam ke saare questions fetch karne ke liye
    List<Question> findByExamId(Long examId);
}