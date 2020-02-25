package com.johanrivas.jlearning.Services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.RandomStringUtils;
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
import com.johanrivas.jlearning.Entities.Role;
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
	// @Autowired
	// private EmailSender emailSender;

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
		return userDao.findUniqueUser(id);

	}

	@Override
	public User save(User user) {

		if (user.getId() == null) {
			String generatedPassword = generateCommonLangPassword();
			String encodedPassword = passwordEncoder.encode(generatedPassword);
			user.setPassword(encodedPassword);
			// emailSender.sendEmail();
			User newUser = userDao.save(user);

			return findById(newUser.getId());
		}

		User toUpdate = userDao.findById(user.getId()).orElseThrow(() -> new ResourceNotFoundException(user.getId()));

		toUpdate.setName(user.getName());
		toUpdate.setLastname(user.getLastname());
		toUpdate.setDirection(user.getDirection());
		toUpdate.setEnable(user.getEnable());
		toUpdate.setEmail(user.getEmail());
		toUpdate.setIdentification(user.getIdentification());
		toUpdate.setRoles(user.getRoles());

		return userDao.save(toUpdate);
	}

	@Override
	public void delete(Long id) {
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		uploadFileService.delete(user.getPhoto());
		userDao.deleteById(id);
	}

	@Override
	public User findByEmail(String email) {
		User user = userDao.findByEmail(email);
		if (user == null) {
			throw new ResourceNotFoundException(email);
		}
		return user;
	}

	public String generateCommonLangPassword() {
		String upperCaseLetters = RandomStringUtils.random(2, 65, 90, true, true);
		String lowerCaseLetters = RandomStringUtils.random(2, 97, 122, true, true);
		String numbers = RandomStringUtils.randomNumeric(2);
		String specialChar = RandomStringUtils.random(2, 33, 47, false, false);
		String totalChars = RandomStringUtils.randomAlphanumeric(2);
		String combinedChars = upperCaseLetters.concat(lowerCaseLetters).concat(numbers).concat(specialChar)
				.concat(totalChars);
		List<Character> pwdChars = combinedChars.chars().mapToObj(c -> (char) c).collect(Collectors.toList());
		Collections.shuffle(pwdChars);
		String password = pwdChars.stream().collect(StringBuilder::new, StringBuilder::append, StringBuilder::append)
				.toString();
		return password;
	}

}
