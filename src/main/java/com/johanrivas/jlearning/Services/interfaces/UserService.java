package com.johanrivas.jlearning.Services.interfaces;

import org.springframework.http.ResponseEntity;

import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.models.dtos.PatchUserDTO;
import com.johanrivas.jlearning.models.dtos.PostUserDto;

public interface UserService {

	ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy);

	User findById(Long id);

	User save(User user);

	void delete(Long id);

	User findByEmail(String email);

	User patch(PatchUserDTO userDto, Long id);
}
