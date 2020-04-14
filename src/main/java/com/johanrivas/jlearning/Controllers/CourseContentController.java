package com.johanrivas.jlearning.Controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johanrivas.jlearning.models.Entities.CourseContent;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.interfaces.CourseContentService;

@RestController
@RequestMapping("/api/v1")
public class CourseContentController {

	@Autowired
	private CourseContentService courseCountentService;

	@GetMapping("/courseContents/{courseId}")
	public ResponseEntity<?> courseContents(@PathVariable("courseId") Long courseId) {
		List<CourseContent> courseContents = courseCountentService.findAll(courseId);
		return new ResponseEntity<>(courseContents, HttpStatus.OK);
	}

	@PostMapping("/courseContents")
	public ResponseEntity<?> create(@Valid @RequestBody CourseContent content, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<CourseContent>(courseCountentService.save(content), HttpStatus.CREATED);
	}

	@DeleteMapping("/courseContents/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		courseCountentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
