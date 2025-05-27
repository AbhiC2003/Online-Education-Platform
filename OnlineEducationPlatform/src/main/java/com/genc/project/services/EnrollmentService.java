package com.genc.project.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.genc.project.entities.Course;
import com.genc.project.entities.Enrollment;
import com.genc.project.entities.User;
import com.genc.project.repositories.EnrollmentRepository;

@Service
public class EnrollmentService {
	@Autowired
    private EnrollmentRepository enrollmentRepository;

    public void enroll(User user, Course course) {
    	Enrollment e = enrollmentRepository.findByUserIdAndCourseId(user.getId(), course.getId());
    	if(e==null) {
    		Enrollment enrollment = new Enrollment(user, course);
    		enrollmentRepository.save(enrollment);
    	}
        
    }

	public List<Course> getEnrolledCoursesByStudent(int id) {
		return enrollmentRepository.findByUserId(id);
	}

	public boolean isStudentEnrolled(int id, int courseId) {
		Enrollment e = enrollmentRepository.findByUserIdAndCourseId(id, courseId);
		return e!=null;
		
	}
}
