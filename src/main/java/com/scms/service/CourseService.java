package com.scms.service;

import com.scms.model.Course;
import com.scms.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CourseService {

    private final CourseRepository courseRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    public Course findById(Long id) {
        return courseRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Course not found with id: " + id));
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public void deleteById(Long id) {
        courseRepository.deleteById(id);
    }

    public boolean existsByCode(String code) {
        return courseRepository.existsByCode(code);
    }

    public List<Course> search(String keyword) {
        if (keyword == null || keyword.isBlank()) {
            return courseRepository.findAll();
        }
        return courseRepository.findByTitleContainingIgnoreCase(keyword);
    }
}
