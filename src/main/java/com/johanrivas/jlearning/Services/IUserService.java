package com.johanrivas.jlearning.Services;

import org.springframework.http.ResponseEntity;

import com.johanrivas.jlearning.Entities.User;

public interface IUserService {

	ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy);

	User findById(Long id);

	User save(User user);

	void delete(Long id);
}
