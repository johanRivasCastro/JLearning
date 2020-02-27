package com.johanrivas.jlearning.Services.interfaces;

import java.util.List;

import com.johanrivas.jlearning.Entities.CourseContent;

public interface CourseContentService {

	CourseContent findById(Long id);

	List<CourseContent> findAll(Long courseId);

	CourseContent save(CourseContent content);

	void delete(Long id);
}
