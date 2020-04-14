package com.johanrivas.jlearning.Services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javassist.tools.web.BadHttpRequest;

import com.johanrivas.jlearning.Dao.RoleDao;
import com.johanrivas.jlearning.models.Entities.Role;
import com.johanrivas.jlearning.Execptions.ResourceInUseException;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;
import com.johanrivas.jlearning.Execptions.UniqueConstraintViolationException;
import com.johanrivas.jlearning.Services.interfaces.RoleService;

@Service
public class RoleServiceImpl implements RoleService {

	@Autowired
	private RoleDao roleDao;

	@Override
	public Role save(Role role) {
		Role roleByAuthority = roleDao.findByAuthority(role.getAuthority());
		if (roleByAuthority != null) {
			throw new UniqueConstraintViolationException("there is allready a role with this name");
		}
		return roleDao.save(role);
	}

	@Override
	public void delete(Long id) {
		Long isRoleInUse = null;
		roleDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		isRoleInUse = roleDao.isRoleInUse(id);
		if (isRoleInUse != null) {
			throw new ResourceInUseException("This Role can't be removed becouse it is in use");
		}
		roleDao.deleteById(id);
	}

	@Override
	public List<Role> findAll() {
		return (List<Role>) roleDao.findAll();
	}

}
