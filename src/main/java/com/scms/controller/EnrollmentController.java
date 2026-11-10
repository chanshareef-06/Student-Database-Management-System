package com.scms.controller;

import com.scms.model.Enrollment;
import com.scms.service.CourseService;
import com.scms.service.EnrollmentService;
import com.scms.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;
    private final StudentService studentService;
    private final CourseService courseService;

    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, StudentService studentService, CourseService courseService) {
        this.enrollmentService = enrollmentService;
        this.studentService = studentService;
        this.courseService = courseService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("enrollments", enrollmentService.findAll());
        return "enrollments/list";
    }

    @GetMapping("/new")
    public String createForm(Model model) {
        Enrollment enrollment = new Enrollment();
        enrollment.setEnrollmentDate(LocalDate.now());
        model.addAttribute("enrollment", enrollment);
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "enrollments/form";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute("enrollment") Enrollment enrollment,
                        BindingResult result,
                        Model model,
                        RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            return "enrollments/form";
        }

        if (enrollment.getId() == null &&
                enrollmentService.alreadyEnrolled(enrollment.getStudent().getId(), enrollment.getCourse().getId())) {
            model.addAttribute("students", studentService.findAll());
            model.addAttribute("courses", courseService.findAll());
            model.addAttribute("errorMessage", "This student is already enrolled in the selected course.");
            return "enrollments/form";
        }

        enrollmentService.save(enrollment);
        redirectAttributes.addFlashAttribute("successMessage", "Enrollment saved successfully.");
        return "redirect:/enrollments";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("enrollment", enrollmentService.findById(id));
        model.addAttribute("students", studentService.findAll());
        model.addAttribute("courses", courseService.findAll());
        return "enrollments/form";
    }

    @GetMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        enrollmentService.deleteById(id);
        redirectAttributes.addFlashAttribute("successMessage", "Enrollment removed successfully.");
        return "redirect:/enrollments";
    }
}
