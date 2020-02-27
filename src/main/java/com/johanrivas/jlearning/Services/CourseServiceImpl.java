package com.johanrivas.jlearning.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.johanrivas.jlearning.Dao.CourseDao;
import com.johanrivas.jlearning.Entities.Course;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;
import com.johanrivas.jlearning.Services.interfaces.CourseService;
import com.johanrivas.jlearning.Services.interfaces.UploadFileService;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;
	@Autowired
	private UploadFileService uploadFileService;

	@Transactional(readOnly = true)
	@Override
	public ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Course> pagedResult = null;
		if (filterBy.length() < 2) {
			pagedResult = courseDao.findAll(paging);
		} else {
			pagedResult = courseDao.findByTerm(filterBy, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
		}
		return new ResponseEntity<>(pagedResult, HttpStatus.OK);
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
		Course course = courseDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		uploadFileService.delete(course.getImg());
		courseDao.deleteById(id);
	}

}
