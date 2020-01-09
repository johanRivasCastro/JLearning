package com.johanrivas.jlearning.Services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.johanrivas.jlearning.Dao.IUserDao;
import com.johanrivas.jlearning.Entities.User;
import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;

@Service
public class UserServiceImpl implements IUserService {

	@Autowired
	private IUserDao userDao;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	@Override
	public List<User> findAll(Integer pageNo, Integer pageSize, String sortBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<User> pagedResult = userDao.findAll(paging);

		if (pagedResult.hasContent()) {
			return pagedResult.getContent();
		} else {
			return new ArrayList<User>();
		}
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	@Override
	public User save(User user) {
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		user.setPassword(encodedPassword);
		return userDao.save(user);
	}

	@Override
	public void delete(Long id) {
		userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		userDao.deleteById(id);
	}

}
