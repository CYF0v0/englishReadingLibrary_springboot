package com.example.login.controller;

import com.example.login.model.EnrollCourse;
import com.example.login.service.EnrollCourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/enrollcourses")
public class EnrollCourseController {

    @Autowired
    private EnrollCourseService enrollCourseService;

    private static final Logger logger = LoggerFactory.getLogger(EnrollCourseController.class);

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllEnrollCourses() {
        logger.info("Get all enroll courses request received.");

        try {
            List<EnrollCourse> enrollCourses = enrollCourseService.getAllEnrollCourses();
            if (enrollCourses.isEmpty()) {
                logger.warn("No enroll courses found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No enroll courses found"));
            }
            logger.info("Enroll courses retrieved successfully. Total enroll courses: {}", enrollCourses.size());
            return ResponseEntity.ok(ok(enrollCourses));
        } catch (Exception e) {
            logger.error("Failed to retrieve enroll courses.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve enroll courses"));
        }
    }

    @GetMapping("/getByStudentId/{studentId}")
    public ResponseEntity<?> getEnrollCoursesByStudentId(@PathVariable int studentId) {
        logger.info("Get enroll courses by student ID request received. Student ID: {}", studentId);

        try {
            List<EnrollCourse> enrollCourses = enrollCourseService.getEnrollCoursesByStudentId(studentId);
            if (enrollCourses.isEmpty()) {
                logger.warn("No enroll courses found for student ID: {}", studentId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No enroll courses found for student ID"));
            }
            logger.info("Enroll courses retrieved successfully for student ID: {}. Total enroll courses: {}", studentId, enrollCourses.size());
            return ResponseEntity.ok(ok(enrollCourses));
        } catch (Exception e) {
            logger.error("Failed to retrieve enroll courses for student ID: {}", studentId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve enroll courses for student ID"));
        }
    }

    @PutMapping("/updateStatusById/{id}")
    public ResponseEntity<?> updateEnrollCourseStatusById(@PathVariable int id, @RequestParam boolean status) {
        logger.info("Update enroll course status by ID request received. ID: {}, Status: {}", id, status);

        try {
            EnrollCourse updatedEnrollCourse = enrollCourseService.updateEnrollCourseStatusById(id, status);
            logger.info("Enroll course status updated successfully. EnrollCourse: {}", updatedEnrollCourse);
            return ResponseEntity.ok(ok(updatedEnrollCourse));
        } catch (RuntimeException e) {
            logger.error("Enroll course not found. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Enroll course not found"));
        } catch (Exception e) {
            logger.error("Failed to update enroll course status. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update enroll course status"));
        }
    }

    @PutMapping("/updateScoreById/{id}")
    public ResponseEntity<?> updateEnrollCourseScoreById(@PathVariable int id, @RequestParam int score) {
        logger.info("Update enroll course score by ID request received. ID: {}, Score: {}", id, score);

        try {
            EnrollCourse updatedEnrollCourse = enrollCourseService.updateEnrollCourseScoreById(id, score);
            logger.info("Enroll course score updated successfully. EnrollCourse: {}", updatedEnrollCourse);
            return ResponseEntity.ok(ok(updatedEnrollCourse));
        } catch (RuntimeException e) {
            logger.error("Enroll course not found. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Enroll course not found"));
        } catch (Exception e) {
            logger.error("Failed to update enroll course score. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update enroll course score"));
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> createEnrollCourse(@RequestBody EnrollCourse enrollCourse) {
        logger.info("Create enroll course request received. EnrollCourse: {}", enrollCourse);

        try {
            // Check if the combination of courseName and studentId already exists
            List<EnrollCourse> existingEnrollCourses = enrollCourseService.getEnrollCoursesByStudentId(enrollCourse.getStudentId());
            boolean isDuplicate = existingEnrollCourses.stream()
                    .anyMatch(ec -> ec.getCourseName().equals(enrollCourse.getCourseName()));

            if (isDuplicate) {
                logger.warn("Duplicate entry for course and student: 请勿重复报名");
                return ResponseEntity.status(HttpStatus.CONFLICT).body(error("Duplicate entry for course and student: 请勿重复报名"));
            }

            EnrollCourse savedEnrollCourse = enrollCourseService.saveEnrollCourse(enrollCourse);
            logger.info("Enroll course created successfully. EnrollCourse: {}", savedEnrollCourse);
            return ResponseEntity.ok(ok(savedEnrollCourse));
        } catch (Exception e) {
            logger.error("Failed to create enroll course. EnrollCourse: {}", enrollCourse, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to create enroll course"));
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteEnrollCourseById(@PathVariable int id) {
        logger.info("Delete enroll course by ID request received. ID: {}", id);

        try {
            Optional<EnrollCourse> enrollCourse = enrollCourseService.getEnrollCourseById(id);
            if (!enrollCourse.isPresent()) {
                logger.warn("Enroll course not found. ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Enroll course not found"));
            }
            enrollCourseService.deleteEnrollCourseById(id);
            logger.info("Enroll course deleted successfully. EnrollCourse: {}", enrollCourse.get());
            return ResponseEntity.ok(ok(enrollCourse.get()));
        } catch (Exception e) {
            logger.error("Failed to delete enroll course. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to delete enroll course"));
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getEnrollCourseById(@PathVariable int id) {
        logger.info("Get enroll course by ID request received. ID: {}", id);

        try {
            Optional<EnrollCourse> enrollCourse = enrollCourseService.getEnrollCourseById(id);
            if (!enrollCourse.isPresent()) {
                logger.warn("Enroll course not found. ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Enroll course not found"));
            }
            logger.info("Enroll course retrieved successfully. EnrollCourse: {}", enrollCourse.get());
            return ResponseEntity.ok(ok(enrollCourse.get()));
        } catch (Exception e) {
            logger.error("Failed to retrieve enroll course. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve enroll course"));
        }
    }

//    private Map<String, Object> error(String message) {
//        Map<String, Object> response = new HashMap<>();
//        response.put("code", 403);
//        response.put("message", message);
//        return response;
//    }

    private Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", HttpStatus.CONFLICT.value()); // 确保code与状态码一致
        response.put("message", message);
        return response;
    }

    private Map<String, Object> ok(Object data) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 200);
        response.put("data", data);
        return response;
    }
}