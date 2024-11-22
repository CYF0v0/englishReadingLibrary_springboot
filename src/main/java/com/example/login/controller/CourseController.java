package com.example.login.controller;

import com.example.login.model.Course;
import com.example.login.service.CourseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    @Autowired
    private CourseService courseService;

    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @PostMapping("/create")
    public ResponseEntity<?> createCourse(@RequestBody Course course) {
        logger.info("Create course request received. Course: {}", course);

        try {
            Course savedCourse = courseService.saveCourse(course);
            logger.info("Course created successfully. Course: {}", savedCourse);
            return ResponseEntity.ok(ok(savedCourse));
        } catch (Exception e) {
            logger.error("Failed to create course. Course: {}", course, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to create course"));
        }
    }

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllCourses() {
        logger.info("Get all courses request received.");

        try {
            List<Course> courses = courseService.getAllCourses();
            if (courses.isEmpty()) {
                logger.warn("No courses found.");
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("No courses found"));
            }
            logger.info("Courses retrieved successfully. Total courses: {}", courses.size());
            return ResponseEntity.ok(ok(courses));
        } catch (Exception e) {
            logger.error("Failed to retrieve courses.", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve courses"));
        }
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<?> getCourseById(@PathVariable int id) {
        logger.info("Get course by ID request received. ID: {}", id);

        try {
            Course course = courseService.getCourseById(id);
            if (course == null) {
                logger.warn("Course not found. ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
            }
            logger.info("Course retrieved successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (Exception e) {
            logger.error("Failed to retrieve course. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve course"));
        }
    }

    @GetMapping("/getByName/{name}")
    public ResponseEntity<?> getCourseByName(@PathVariable String name) {
        logger.info("Get course by name request received. Name: {}", name);

        try {
            Course course = courseService.getCourseByName(name);
            if (course == null) {
                logger.warn("Course not found. Name: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
            }
            logger.info("Course retrieved successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (Exception e) {
            logger.error("Failed to retrieve course. Name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to retrieve course"));
        }
    }

    @DeleteMapping("/deleteById/{id}")
    public ResponseEntity<?> deleteCourseById(@PathVariable int id) {
        logger.info("Delete course by ID request received. ID: {}", id);

        try {
            Course course = courseService.getCourseById(id);
            if (course == null) {
                logger.warn("Course not found. ID: {}", id);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
            }
            courseService.deleteCourseById(id);
            logger.info("Course deleted successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (Exception e) {
            logger.error("Failed to delete course. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to delete course"));
        }
    }

    @DeleteMapping("/deleteByName/{name}")
    public ResponseEntity<?> deleteCourseByName(@PathVariable String name) {
        logger.info("Delete course by name request received. Name: {}", name);

        try {
            Course course = courseService.getCourseByName(name);
            if (course == null) {
                logger.warn("Course not found. Name: {}", name);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
            }
            courseService.deleteCourseByName(name);
            logger.info("Course deleted successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (Exception e) {
            logger.error("Failed to delete course. Name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to delete course"));
        }
    }

    @PutMapping("/updateById/{id}")
    public ResponseEntity<?> updateCourseById(@PathVariable int id, @RequestBody Course updatedCourse) {
        logger.info("Update course by ID request received. ID: {}, Updated Course: {}", id, updatedCourse);

        try {
            Course course = courseService.updateCourseById(id, updatedCourse);
            logger.info("Course updated successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (RuntimeException e) {
            logger.error("Course not found. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
        } catch (Exception e) {
            logger.error("Failed to update course. ID: {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update course"));
        }
    }

    @PutMapping("/updateByName/{name}")
    public ResponseEntity<?> updateCourseByName(@PathVariable String name, @RequestBody Course updatedCourse) {
        logger.info("Update course by name request received. Name: {}, Updated Course: {}", name, updatedCourse);

        try {
            Course course = courseService.updateCourseByName(name, updatedCourse);
            logger.info("Course updated successfully. Course: {}", course);
            return ResponseEntity.ok(ok(course));
        } catch (RuntimeException e) {
            logger.error("Course not found. Name: {}", name, e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error("Course not found"));
        } catch (Exception e) {
            logger.error("Failed to update course. Name: {}", name, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error("Failed to update course"));
        }
    }

    private Map<String, Object> error(String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("code", 403);
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