package com.johanrivas.jlearning.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.johanrivas.jlearning.Entities.CourseContent;
import com.johanrivas.jlearning.Entities.DocumentContent;
import com.johanrivas.jlearning.Services.ICourseContentService;
import com.johanrivas.jlearning.Services.IDocumentContentService;
import com.johanrivas.jlearning.Services.IUploadFileService;

@RestController
@RequestMapping("/api/v1")
public class DocumentsContentControlller {

	@Autowired
	private IDocumentContentService documentContentService;
	@Autowired
	private IUploadFileService uploadFileService;
	@Autowired
	private ICourseContentService courseContentService;

	@PostMapping("/documentContents")
	public ResponseEntity<?> save(@RequestParam(name = "file", required = true) MultipartFile document,
			@RequestParam(name = "title", required = true) String title,
			@RequestParam(name = "courseContentId", required = true) String courseContentId) {

		CourseContent courseContent = courseContentService.findById(Long.parseLong(courseContentId));
		if (document == null || title == null || document.isEmpty() || courseContentId == null) {
			return new ResponseEntity<>("the title and the file are required", HttpStatus.BAD_REQUEST);
		}
		if (courseContent == null) {
			return new ResponseEntity<>("the couseContentId is not valid", HttpStatus.BAD_REQUEST);
		}

		String uniqueFilename = uploadFileService.copy(document);

		DocumentContent content = documentContentService
				.save(new DocumentContent(title, uniqueFilename, courseContent));
		return new ResponseEntity<>(content, HttpStatus.CREATED);
	}

	@DeleteMapping("/documentContents/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		DocumentContent document = documentContentService.findById(id);
		uploadFileService.delete(document.getUrlResource());
		documentContentService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
