package com.johanrivas.jlearning.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.CourseContentDao;
import com.johanrivas.jlearning.models.Entities.CourseContent;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;
import com.johanrivas.jlearning.Services.interfaces.CourseContentService;

@Service
public class CourseContentServiceImpl implements CourseContentService {

	@Autowired
	private CourseContentDao courseContentDao;

	@Override
	public CourseContent save(CourseContent content) {
		return courseContentDao.save(content);
	}

	@Override
	public void delete(Long id) {
		courseContentDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		courseContentDao.deleteById(id);
	}

	@Override
	public List<CourseContent> findAll(Long courseId) {
		return courseContentDao.courseContentsByCourseId(courseId);
	}

	@Override
	public CourseContent findById(Long id) {
		return courseContentDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

}
