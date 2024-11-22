package com.example.login.service;

import com.example.login.model.EnrollCourse;
import com.example.login.repository.EnrollCourseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EnrollCourseService {

    @Autowired
    private EnrollCourseRepository enrollCourseRepository;

    private static final Logger logger = LoggerFactory.getLogger(EnrollCourseService.class);

    public List<EnrollCourse> getAllEnrollCourses() {
        return enrollCourseRepository.findAll();
    }

    public List<EnrollCourse> getEnrollCoursesByStudentId(int studentId) {
        return enrollCourseRepository.findByStudentId(studentId);
    }

    public Optional<EnrollCourse> getEnrollCourseById(int id) {
        return enrollCourseRepository.findById(id);
    }

    public EnrollCourse updateEnrollCourseStatusById(int id, boolean status) {
        return enrollCourseRepository.findById(id)
                .map(enrollCourse -> {
                    enrollCourse.setStatus(status);
                    return enrollCourseRepository.save(enrollCourse);
                })
                .orElseThrow(() -> new RuntimeException("EnrollCourse not found with id: " + id));
    }

    public EnrollCourse updateEnrollCourseScoreById(int id, int score) {
        return enrollCourseRepository.findById(id)
                .map(enrollCourse -> {
                    enrollCourse.setScore(score);
                    return enrollCourseRepository.save(enrollCourse);
                })
                .orElseThrow(() -> new RuntimeException("EnrollCourse not found with id: " + id));
    }

    public EnrollCourse saveEnrollCourse(EnrollCourse enrollCourse) {
        try {
            return enrollCourseRepository.save(enrollCourse);
        } catch (DataIntegrityViolationException e) {
            logger.warn("Duplicate entry for course and student: 请勿重复报名", e);
            return null; // 返回null表示保存失败
        }
    }

    public void deleteEnrollCourseById(int id) {
        enrollCourseRepository.deleteById(id);
    }
}