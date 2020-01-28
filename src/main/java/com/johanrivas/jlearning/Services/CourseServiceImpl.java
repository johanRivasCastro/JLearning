package com.johanrivas.jlearning.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.johanrivas.jlearning.Dao.ICourseDao;
import com.johanrivas.jlearning.Entities.Course;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class CourseServiceImpl implements ICourseService {

	@Autowired
	private ICourseDao courseDao;
	@Autowired
	private IUploadFileService uploadFileService;

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

	@Transactional(readOnly = true)
	@Override
	public List<Course> findAll(Integer pageNo, Integer pageSize, String sortBy) {
		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<Course> pagedResult = courseDao.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<Course>();
		}
	}

}
