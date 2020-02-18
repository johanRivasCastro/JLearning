package com.johanrivas.jlearning.Controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.johanrivas.jlearning.Entities.User;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.IUploadFileService;
import com.johanrivas.jlearning.Services.IUserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private IUserService userService;
	@Autowired
	private IUploadFileService uploadFileService;

	@GetMapping("/users")
	public ResponseEntity<?> users(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false, name = "filterBy", defaultValue = "") String filterBy) {
		return userService.findAll(pageNo, pageSize, sortBy, filterBy);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> user(@PathVariable("id") Long id) {
		return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<?> create(@Valid @RequestBody User user, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
	}

	@PostMapping("/users/photo/{userId}")
	public RedirectView uploadPhoto(@RequestParam(name = "photo", required = false) MultipartFile photo,
			@PathVariable("userId") Long userId) {
		User user = userService.findById(userId);
		uploadFileService.delete(user.getPhoto());
		String uniqueFilename = uploadFileService.copy(photo);
		user.setPhoto(uniqueFilename);
		userService.save(user);
		return new RedirectView("/api/v1/uploads/" + uniqueFilename);
	}

	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody User user, BindingResult result) {
		userService.findById(id);
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<>(userService.save(user), HttpStatus.OK);
	}

}
