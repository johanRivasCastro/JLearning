package com.johanrivas.jlearning.Services;

import java.util.List;

import com.johanrivas.jlearning.Entities.Role;

public interface IRoleService {

	List<Role> findAll();

	Role save(Role role);

	void delete(Long id);
}
