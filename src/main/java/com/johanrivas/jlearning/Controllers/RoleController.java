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

import com.johanrivas.jlearning.Entities.Role;
import com.johanrivas.jlearning.Execptions.BindingResultException;
import com.johanrivas.jlearning.Services.IRoleService;

@RestController
@RequestMapping("/api/v1")
public class RoleController {

	@Autowired
	private IRoleService roleService;

	@GetMapping("/roles")
	public List<Role> roles() {
		return roleService.findAll();
	}

	@PostMapping("/roles")
	public ResponseEntity<?> save(@Valid @RequestBody Role role, BindingResult result) {
		if (result.hasErrors()) {
			throw new BindingResultException(result);
		}
		return new ResponseEntity<>(roleService.save(role), HttpStatus.CREATED);
	}

	@DeleteMapping("/roles/{id}")
	public ResponseEntity<?> delete(@PathVariable("id") Long id) {
		roleService.delete(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
