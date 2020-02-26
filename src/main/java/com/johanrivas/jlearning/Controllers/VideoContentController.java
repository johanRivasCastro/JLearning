package com.johanrivas.jlearning.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.johanrivas.jlearning.Entities.VideoContent;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.interfaces.VideoContentService;

@RestController
@RequestMapping("/api/v1")
public class VideoContentController {

	@Autowired
	private VideoContentService videoService;

	@PostMapping("/videoContents")
	public ResponseEntity<?> save(@Valid @RequestBody VideoContent videoContent, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<>(videoService.save(videoContent), HttpStatus.CREATED);
	}

	@DeleteMapping("/videoContents/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		videoService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
