package com.johanrivas.jlearning.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.johanrivas.jlearning.Dao.ICourseDao;
import com.johanrivas.jlearning.Entities.Course;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private ICourseDao courseDao;

	@Transactional(readOnly = true)
	@Override
	public List<Course> findAll() {

		return (List<Course>) courseDao.findAll();
	}

	@Transactional(readOnly = true)
	@Override
	public Course findById(Long id) {
		return courseDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public Course save(Course course) {
		return courseDao.save(course);
	}

	@Override
	public void delete(Long id) {
		courseDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		courseDao.deleteById(id);
	}

}
