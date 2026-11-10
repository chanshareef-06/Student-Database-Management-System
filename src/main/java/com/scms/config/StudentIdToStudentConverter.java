package com.scms.config;

import com.scms.model.Student;
import com.scms.repository.StudentRepository;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class StudentIdToStudentConverter implements Converter<String, Student> {

    private final StudentRepository studentRepository;

    public StudentIdToStudentConverter(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Student convert(String source) {
        if (source == null || source.isBlank()) {
            return null;
        }
        return studentRepository.findById(Long.valueOf(source)).orElse(null);
    }
}
