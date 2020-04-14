package com.johanrivas.jlearning.Controllers;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.models.dtos.PatchUserDTO;
import com.johanrivas.jlearning.models.dtos.PostUserDto;
import com.johanrivas.jlearning.utils.DTOModelMapper;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.interfaces.UploadFileService;
import com.johanrivas.jlearning.Services.interfaces.UserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private DTOModelMapper dtoModelMapper;

	@GetMapping("/users")
	public ResponseEntity<?> users(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy,
			@RequestParam(required = false, name = "filterBy", defaultValue = "") String filterBy) {
		return userService.findAll(pageNo, pageSize, sortBy, filterBy);
		// return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	@GetMapping("/users/{id}")
	public ResponseEntity<User> user(@PathVariable("id") Long id) {
		return new ResponseEntity<User>(userService.findById(id), HttpStatus.OK);
	}

	@PostMapping("/users")
	public ResponseEntity<User> create(@Valid @RequestBody User user, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<User>(userService.save(user), HttpStatus.CREATED);
	}

	// RedirectView
	@PostMapping("/users/photo/{userId}")
	public ResponseEntity<User> uploadPhoto(@RequestParam(name = "photo", required = false) MultipartFile photo,
			@PathVariable("userId") Long userId) {
		User user = userService.findById(userId);
		uploadFileService.delete(user.getPhoto());
		String uniqueFilename = uploadFileService.copy(photo);
		user.setPhoto(uniqueFilename);

		return new ResponseEntity<User>(userService.save(user), HttpStatus.OK);

	}

	@PatchMapping("/users/{id}")
	public ResponseEntity<?> patch(@PathVariable("id") Long id, @Valid @RequestBody PatchUserDTO patchUserDTO,
			BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<User>(userService.patch(patchUserDTO, id), HttpStatus.OK);
	}

}
