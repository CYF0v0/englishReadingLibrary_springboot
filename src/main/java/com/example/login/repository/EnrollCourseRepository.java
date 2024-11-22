package com.example.login.repository;

import com.example.login.model.EnrollCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EnrollCourseRepository extends JpaRepository<EnrollCourse, Integer> {
    List<EnrollCourse> findByStudentId(int studentId);
}