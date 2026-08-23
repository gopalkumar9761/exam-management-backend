package org.example.onlineexam.repository;

import org.example.onlineexam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ExamRepository extends JpaRepository<Exam, Long> {
    // Unique link ya code ke zariye exam find karne ke liye
    Optional<Exam> findByUniqueLink(String uniqueLink);

    // Teacher ID ke hisab se saare exams find karne ke liye
    List<Exam> findByTeacherId(Long teacherId);
}