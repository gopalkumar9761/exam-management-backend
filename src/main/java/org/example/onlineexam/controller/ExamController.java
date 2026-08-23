package org.example.onlineexam.controller;

import org.example.onlineexam.entity.Exam;
import org.example.onlineexam.entity.Question;
import org.example.onlineexam.repository.ExamRepository;
import org.example.onlineexam.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/exams")
@CrossOrigin(origins = "http://localhost:3000")
public class ExamController {

    @Autowired
    private ExamRepository examRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @PostMapping("/create")
    public ResponseEntity<?> createExam(@RequestBody Map<String, Object> requestData) {
        try {
            String title = (String) requestData.get("title");
            Long teacherId = Long.valueOf(requestData.get("teacherId").toString());
            String course = (String) requestData.get("course");
            String branch = (String) requestData.get("branch");
            String year = (String) requestData.get("year");
            String semester = (String) requestData.get("semester");
            String subject = (String) requestData.get("subject");

            String examDate = (String) requestData.get("examDate");
            String startTime = (String) requestData.get("startTime");
            int durationMinutes = Integer.parseInt(requestData.get("durationMinutes").toString());

            String uniqueLink = UUID.randomUUID().toString().substring(0, 8).toUpperCase();

            Exam exam = new Exam(title, uniqueLink, teacherId, course, branch, year, semester, subject, examDate, startTime, durationMinutes);
            Exam savedExam = examRepository.save(exam);

            List<Map<String, String>> questionsList = (List<Map<String, String>>) requestData.get("questions");
            if (questionsList != null) {
                for (Map<String, String> qData : questionsList) {
                    Question question = new Question(
                            savedExam.getId(),
                            qData.get("questionText"),
                            qData.get("optionA"),
                            qData.get("optionB"),
                            qData.get("optionC"),
                            qData.get("optionD"),
                            qData.get("correctOption")
                    );
                    questionRepository.save(question);
                }
            }

            Map<String, Object> response = new HashMap<>();
            response.put("message", "Exam created successfully with schedule!");
            response.put("exam", savedExam);
            response.put("uniqueLink", uniqueLink);

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/join/{uniqueLink}")
    public ResponseEntity<?> getExamByLink(@PathVariable String uniqueLink) {
        Optional<Exam> examOpt = examRepository.findByUniqueLink(uniqueLink);
        if (examOpt.isPresent()) {
            Exam exam = examOpt.get();
            List<Question> questions = questionRepository.findByExamId(exam.getId());

            for (Question q : questions) {
                q.setCorrectOption(null); // Security: Hide correct options from student
            }

            Map<String, Object> response = new HashMap<>();
            response.put("exam", exam);
            response.put("questions", questions);

            return ResponseEntity.ok(response);
        }
        return ResponseEntity.badRequest().body("Error: Invalid Exam Link or Code!");
    }

    @GetMapping("/teacher/{teacherId}")
    public ResponseEntity<List<Exam>> getExamsByTeacher(@PathVariable Long teacherId) {
        List<Exam> exams = examRepository.findByTeacherId(teacherId);
        return ResponseEntity.ok(exams);
    }

    @GetMapping("/{examId}/questions")
    public ResponseEntity<List<Question>> getQuestionsByExamId(@PathVariable Long examId) {
        List<Question> questions = questionRepository.findByExamId(examId);
        return ResponseEntity.ok(questions);
    }

    // Delete Exam API (Updated path to match frontend: /{examId})
    @DeleteMapping("/{examId}")
    public ResponseEntity<?> deleteExam(@PathVariable Long examId) {
        try {
            Optional<Exam> examOpt = examRepository.findById(examId);
            if (examOpt.isPresent()) {
                // Pehle is exam se linked saare questions delete karein taaki foreign key constraint error na aaye
                List<Question> questions = questionRepository.findByExamId(examId);
                if (questions != null && !questions.isEmpty()) {
                    questionRepository.deleteAll(questions);
                }

                // Ab exam delete karein
                examRepository.deleteById(examId);
                return ResponseEntity.ok(Map.of("message", "Exam deleted successfully from database!"));
            }
            return ResponseEntity.badRequest().body("Error: Exam not found!");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}