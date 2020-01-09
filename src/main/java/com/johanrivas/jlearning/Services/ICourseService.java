package com.johanrivas.jlearning.Services;

import java.util.List;

import com.johanrivas.jlearning.Entities.Course;

public interface ICourseService {

	List<Course> findAll();

	Course findById(Long id);

	Course save(Course course);

	void delete(Long id);

}
