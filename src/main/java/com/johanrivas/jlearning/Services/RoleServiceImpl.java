package com.johanrivas.jlearning.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.IRoleDao;
import com.johanrivas.jlearning.Entities.Role;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class RoleServiceImpl implements IRoleService {

	@Autowired
	private IRoleDao roleDao;

	@Override
	public Role save(Role role) {
		return roleDao.save(role);
	}

	@Override
	public void delete(Long id) {
		roleDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		roleDao.deleteById(id);
	}

	@Override
	public List<Role> findAll() {

		return (List<Role>) roleDao.findAll();
	}

}
