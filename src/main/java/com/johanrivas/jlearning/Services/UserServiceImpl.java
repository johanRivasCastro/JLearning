package com.johanrivas.jlearning.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@Autowired
	private IUploadFileService uploadFileService;

	@Override
	public ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<User> pagedResult = null;
		if (filterBy.length() < 2) {
			pagedResult = userDao.findAll(paging);
		} else {
			pagedResult = userDao.findByTerm(filterBy, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
		}

		if (pagedResult.hasContent()) {
			return new ResponseEntity<>(pagedResult, HttpStatus.OK);
		} else {
			return new ResponseEntity<User>(HttpStatus.OK);
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
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		uploadFileService.delete(user.getPhoto());
		userDao.deleteById(id);
	}

}
