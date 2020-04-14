package com.johanrivas.jlearning.Services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.RandomStringUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.johanrivas.jlearning.Dao.RoleDao;
import com.johanrivas.jlearning.Dao.UserDao;
import com.johanrivas.jlearning.models.Entities.Role;
import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.models.dtos.PatchUserDTO;
import com.johanrivas.jlearning.models.dtos.PostUserDto;
import com.johanrivas.jlearning.utils.DTOModelMapper;

import com.johanrivas.jlearning.Execptions.ResourceNotFoundException;
import com.johanrivas.jlearning.Execptions.UniqueConstraintViolationException;
import com.johanrivas.jlearning.Services.interfaces.UploadFileService;
import com.johanrivas.jlearning.Services.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserDao userDao;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
	private UploadFileService uploadFileService;
	@Autowired
	private EmailSender emailSender;
	@Autowired
	private DTOModelMapper dtoModelMapper;

	@Autowired
	private RoleDao roleDao;

	@Override
	public ResponseEntity<?> findAll(Integer pageNo, Integer pageSize, String sortBy, String filterBy) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<User> pagedResult = null;
		if (filterBy.length() < 2) {
			pagedResult = userDao.findAll(paging);
		} else {
			pagedResult = userDao.findByTerm(filterBy, PageRequest.of(pageNo, pageSize, Sort.by(sortBy)));
		}

		pagedResult.map(user -> removeUserCourses(user));
		return new ResponseEntity<>(pagedResult, HttpStatus.OK);
	}

	@Override
	public User findById(Long id) {
		return userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public User removeUserCourses(User user) {
		user.setCourses(null);
		return user;
	}

	@Override
	@Transactional
	public User save(User user) {

		if (user.getId() == null) {
			User userByEmail = userDao.findByEmail(user.getEmail());
			if (userByEmail != null) {
				throw new UniqueConstraintViolationException("there is allready a user with this email");
			}
			User userByIdentification = userDao.findByIdentification(user.getIdentification());
			if (userByIdentification != null) {
				throw new UniqueConstraintViolationException("there is allready a user with this ID");
			}
			String generatedPassword = generateCommonLangPassword();
			String encodedPassword = passwordEncoder.encode(generatedPassword);
			user.setPassword(encodedPassword);
			// emailSender.sendEmail(user.getEmail(), generatedPassword);
		}

		List<Role> roles = new ArrayList<Role>(user.getRoles());
		user.getRoles().clear();

		roles.forEach(role -> {
			Optional<Role> found = roleDao.findById(role.getId());
			if (found.isPresent()) {
				user.getRoles().add(found.get());
			}
		});
		return userDao.save(user);
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

	@Override
	public User patch(PatchUserDTO userDto, Long id) {
		User user = userDao.findById(id).orElseThrow(() -> new ResourceNotFoundException(id));
		dtoModelMapper.mapDtoToEntity(userDto, user);
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userDao.save(user);
	}

}
