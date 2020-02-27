package com.johanrivas.jlearning.Services.interfaces;

import java.util.List;

import com.johanrivas.jlearning.Entities.Role;

public interface RoleService {

	List<Role> findAll();

	Role save(Role role);

	void delete(Long id);
}
