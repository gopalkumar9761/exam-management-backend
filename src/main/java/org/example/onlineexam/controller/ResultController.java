package org.example.onlineexam.controller;

import org.example.onlineexam.entity.Question;
import org.example.onlineexam.entity.Result;
import org.example.onlineexam.repository.QuestionRepository;
import org.example.onlineexam.repository.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/results")
@CrossOrigin(origins = "http://localhost:3000")
public class ResultController {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private ResultRepository resultRepository;

    @PostMapping("/submit")
    public ResponseEntity<?> submitExam(@RequestBody Map<String, Object> submissionData) {
        try {
            Long examId = Long.valueOf(submissionData.get("examId").toString());
            Long studentId = Long.valueOf(submissionData.get("studentId").toString());
            String studentName = (String) submissionData.get("studentName");
            String rollNumber = (String) submissionData.get("rollNumber");
            String course = (String) submissionData.get("course");
            String semester = (String) submissionData.get("semester");

            Map<String, String> studentAnswers = (Map<String, String>) submissionData.get("answers");
            List<Question> questions = questionRepository.findByExamId(examId);

            int score = 0;
            int totalQuestions = questions.size();

            for (Question q : questions) {
                String qIdStr = String.valueOf(q.getId());
                if (studentAnswers.containsKey(qIdStr)) {
                    String studentChoice = studentAnswers.get(qIdStr);
                    // Correct option match check (agar entity me method match kare)
                    if (studentChoice.equalsIgnoreCase(q.getCorrectOption())) {
                        score++;
                    }
                }
            }

            Result result = new Result(examId, studentId, studentName, rollNumber, course, semester, score, totalQuestions);
            Result savedResult = resultRepository.save(result);

            return ResponseEntity.ok(savedResult);

        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<Result>> getAllResults() {
        List<Result> results = resultRepository.findAll();
        return ResponseEntity.ok(results);
    }
}