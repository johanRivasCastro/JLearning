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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.johanrivas.jlearning.Entities.User;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.IUserService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private IUserService userService;

	@GetMapping("/users")
	public List<User> users(@RequestParam(defaultValue = "0") Integer pageNo,
			@RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "id") String sortBy) {
		return userService.findAll(pageNo, pageSize, sortBy);
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

	@PutMapping("/users/{id}")
	public ResponseEntity<?> update(@PathVariable("id") Long id, @Valid @RequestBody User user, BindingResult result) {
		userService.findById(id);
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		User updated = user;
		updated.setId(id);
		return new ResponseEntity<>(userService.save(updated), HttpStatus.OK);
	}

	@DeleteMapping("/users/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		userService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

}
