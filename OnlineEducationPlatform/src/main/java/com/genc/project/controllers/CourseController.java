package com.genc.project.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.genc.project.entities.Course;
import com.genc.project.entities.Lesson;
import com.genc.project.entities.User;
import com.genc.project.services.CourseService;
import com.genc.project.services.EnrollmentService;
import com.genc.project.services.LessonService;

import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	@Autowired
	private UserService userService;
	
	@Autowired
	private EnrollmentService enrollmentService;
	
	@Autowired
	private LessonService lessonService;
	
	@GetMapping("/course/{id}")
	public String viewCourse(@PathVariable("id") int courseId, Model model, Authentication auth) {
		String email = ((UserDetailsImpl) auth.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
	    Course course = courseService.getCourse(courseId);
	    model.addAttribute("course", course);
	    boolean isEnrolled = enrollmentService.isStudentEnrolled(user.getId(), courseId);
	    if (isEnrolled) {
	        return "coursePage"; 
	    }
		return "courseDetail";
	}
	
	@PostMapping("/enroll")
	public String enrollStudent(@RequestParam int id, Authentication authentication, Model model) {
	    String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
	    enrollmentService.enroll(user, courseService.getCourse(id));
	    model.addAttribute("course", courseService.getCourse(id));
	    return "coursePage"; 
	}
	
	@GetMapping("/enrolledCourses")
    public String getEnrolledCourses(Authentication authentication, Model model) {
		System.out.println("haiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiiii");
		String email = ((UserDetailsImpl) authentication.getPrincipal()).getEmail(); 
	    User user = userService.getUserByEmail(email);
        List<Course> enrolledCourses = enrollmentService.getEnrolledCoursesByStudent(user.getId());
        model.addAttribute("courses", enrolledCourses);
        int id = ((UserDetailsImpl) authentication.getPrincipal()).getId();
    	model.addAttribute("user", userService.findById(id));
        return "myLearning";
    }

	
    
}
