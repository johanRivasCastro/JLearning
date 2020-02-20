package com.johanrivas.jlearning.Services;

import java.util.List;

import com.johanrivas.jlearning.Entities.Course;

import org.springframework.http.ResponseEntity;

public interface ICourseService {

	ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy);

	Course findById(Long id);

	Course save(Course course);

	void delete(Long id);

}
