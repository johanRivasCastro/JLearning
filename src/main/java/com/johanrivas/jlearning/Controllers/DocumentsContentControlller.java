package com.johanrivas.jlearning.Controllers;

import java.io.IOException;
import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

	@GetMapping(value = "/uploads/{filename:.+}")
	public ResponseEntity<Resource> document(@PathVariable String filename, HttpServletRequest request) {

		Resource recurso = null;

		try {
			recurso = uploadFileService.load(filename);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		String contentType = null;
		try {
			contentType = request.getServletContext().getMimeType(recurso.getFile().getAbsolutePath());
		} catch (IOException ex) {
//			logger.info("Could not determine file type.");
		}

		// Fallback to the default content type if type could not be determined
		if (contentType == null) {
			contentType = "application/octet-stream";
		}

		return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
				.body(recurso);
	}

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

		String uniqueFilename = null;
		try {
			uniqueFilename = uploadFileService.copy(document);
		} catch (IOException e) {
			e.printStackTrace();
		}

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