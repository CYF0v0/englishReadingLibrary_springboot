package com.example.login.service;

import com.example.login.model.Course;
import com.example.login.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    @Autowired
    private CourseRepository courseRepository;

    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    public Course getCourseById(int id) {
        return courseRepository.findById(id).orElse(null);
    }

    public Course getCourseByName(String name) {
        return courseRepository.findByName(name);
    }

    public void deleteCourseById(int id) {
        courseRepository.deleteById(id);
    }

    public void deleteCourseByName(String name) {
        Course course = courseRepository.findByName(name);
        if (course != null) {
            courseRepository.delete(course);
        }
    }

    public Course updateCourseById(int id, Course updatedCourse) {
        return courseRepository.findById(id)
                .map(course -> {
                    course.setName(updatedCourse.getName());
                    course.setTeacher(updatedCourse.getTeacher());
                    course.setStatus(updatedCourse.getStatus());
                    course.setCharge(updatedCourse.getCharge());
                    return courseRepository.save(course);
                })
                .orElseThrow(() -> new RuntimeException("Course not found with id: " + id));
    }

    public Course updateCourseByName(String courseName, Course updatedCourse) {
        Course course = courseRepository.findByName(courseName);
        if (course == null) {
            throw new RuntimeException("Course not found with name: " + courseName);
        }
        course.setName(updatedCourse.getName());
        course.setTeacher(updatedCourse.getTeacher());
        course.setStatus(updatedCourse.getStatus());
        course.setCharge(updatedCourse.getCharge());
        return courseRepository.save(course);
    }

}