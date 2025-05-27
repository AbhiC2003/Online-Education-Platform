package com.genc.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.genc.project.entities.Course;
import com.genc.project.services.CourseService;
import com.genc.project.services.EnrollmentService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;



@Controller
public class DashboardController {
	@Autowired
	private UserService userService;
	
	@Autowired
	private EnrollmentService enrollmentService;

    @GetMapping("/dashboard")
    public String showDashboard(Model modal, Authentication auth) {
    	int id =  ((UserDetailsImpl)auth.getPrincipal()).getId();
    	modal.addAttribute("user", userService.findById(id));
    	List<Course> courses = enrollmentService.getEnrolledCoursesByStudent(id);
    	modal.addAttribute("courses", courses);
       return "dashboard"; 
    }
}
