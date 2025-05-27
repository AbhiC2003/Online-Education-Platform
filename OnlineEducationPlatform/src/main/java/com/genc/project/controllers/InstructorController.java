package com.genc.project.controllers;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.genc.project.entities.Course;
import com.genc.project.entities.Lesson;
import com.genc.project.entities.User;
import com.genc.project.services.CourseService;
import com.genc.project.services.LessonService;
import com.genc.project.services.UserDetailsImpl;
import com.genc.project.services.UserService;

@Controller
public class InstructorController {
	@Autowired
	private CourseService courseService;
	
	@Autowired
	private UserService userService;

	@Autowired
	private LessonService lessonService;

	
	@GetMapping("/addCourse")
	public String showAddCourseForm(Model model) {
	    return "createCourse"; 
	}
	
	@PostMapping("/addCourse")
	public String addCourse(@ModelAttribute Course course, Authentication auth) {
		int id = ((UserDetailsImpl) auth.getPrincipal()).getId();
	    User instructor = userService.findById(id); 
	    course.setInstructor(instructor);
	    courseService.saveCourse(course);
	    return "redirect:/home"; 
	}
	
	@GetMapping("/updateCourse/{id}")
    public String showEditForm(@PathVariable int id, Model model) {
        Course course = courseService.getCourse(id);
        model.addAttribute("course", course);
        return "updateCourse"; 
    }
	
	@PostMapping("/updateCourse/{id}")
	public String updateCourse(@PathVariable int id, @ModelAttribute Course course, 
	                           @RequestParam("lessons[title][]") List<String> titles,
	                           @RequestParam("lessons[content][]") List<String> contents) {
	    List<Lesson> lessons = new ArrayList<>();
	    for (int i = 0; i < titles.size(); i++) {
	        Lesson lesson = new Lesson();
	        lesson.setTitle(titles.get(i));
	        lesson.setContent(contents.get(i));
	        lesson.setCourse(course);
	        lessons.add(lesson);
	    }
	    lessonService.saveAll(lessons);  
	    course.setLessons(lessons);
	    courseService.updateCourse(course.getId(), course);
	    return "redirect:/home";
	}

    
    @GetMapping("/deleteCourse/{id}")
    public String deleteCourse(@PathVariable int id) {
        courseService.deleteCourse(id);
        return "redirect:/home"; 
    }
    
}

