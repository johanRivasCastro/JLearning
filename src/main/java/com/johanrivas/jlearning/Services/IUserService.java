package com.johanrivas.jlearning.Services;

import java.util.List;

import com.johanrivas.jlearning.Entities.User;

public interface IUserService {

	List<User> findAll(Integer pageNo, Integer pageSize, String sortBy);

	User findById(Long id);

	User save(User user);

	void delete(Long id);
}
