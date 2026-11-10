package com.scms.controller;

import com.scms.service.CourseService;
import com.scms.service.EnrollmentService;
import com.scms.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final StudentService studentService;
    private final CourseService courseService;
    private final EnrollmentService enrollmentService;

    @Autowired
    public HomeController(StudentService studentService, CourseService courseService, EnrollmentService enrollmentService) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.enrollmentService = enrollmentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("studentCount", studentService.findAll().size());
        model.addAttribute("courseCount", courseService.findAll().size());
        model.addAttribute("enrollmentCount", enrollmentService.findAll().size());
        return "index";
    }
}
