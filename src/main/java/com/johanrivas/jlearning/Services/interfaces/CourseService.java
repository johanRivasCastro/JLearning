package com.johanrivas.jlearning.Services.interfaces;

import java.util.List;

import com.johanrivas.jlearning.models.Entities.Course;

import org.springframework.http.ResponseEntity;

public interface CourseService {

	ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy);

	Course findById(Long id);

	Course save(Course course);

	void delete(Long id);

}
