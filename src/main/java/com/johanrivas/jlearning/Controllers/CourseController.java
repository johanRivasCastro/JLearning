package com.johanrivas.jlearning.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johanrivas.jlearning.Entities.Course;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.ICourseService;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
@RequestMapping("/api/v1")
public class CourseController {

	@Autowired
	private ICourseService courseService;

	@GetMapping("/courses")
	public List<Course> courses() {
		return courseService.findAll();
	}

	@GetMapping("/courses/{id}")
	public ResponseEntity<Course> course(@PathVariable("id") Long id) {
		return new ResponseEntity<Course>(courseService.findById(id), HttpStatus.OK);
	}

	@PostMapping("/courses")
	public ResponseEntity<?> create(@Valid @RequestBody Course course, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<>(courseService.save(course), HttpStatus.CREATED);
	}

	@PutMapping("/courses/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody Course course,
			BindingResult result) {
		courseService.findById(id);
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		Course updated = course;
		updated.setId(id);
		return new ResponseEntity<>(courseService.save(updated), HttpStatus.OK);
	}

	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		courseService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
