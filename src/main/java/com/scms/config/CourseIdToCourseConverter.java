package com.scms.config;

import com.scms.model.Course;
import com.scms.repository.CourseRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class CourseIdToCourseConverter implements Converter<String, Course> {

    private final CourseRepository courseRepository;

    public CourseIdToCourseConverter(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public Course convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return courseRepository.findById(Long.valueOf(source)).orElse(null);
    }
}
