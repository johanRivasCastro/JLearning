package com.johanrivas.jlearning.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.scheduling.annotation.Async;

import com.johanrivas.jlearning.models.Entities.User;
import com.johanrivas.jlearning.utils.CustomRepository;

public interface UserDao extends JpaRepository<User, Long> {

	@Query("select u from User u where u.name like %?1% or u.lastname like %?1% or u.identification like %?1% or u.email like %?1%")
	Page<User> findByTerm(String term, PageRequest pageRequest);

	User findByEmail(String email);

	User findByIdentification(String identification);

	// @Query("select u from User u join fetch u.courses where u.id = 1")
	// User findUniqueUser(Long id);
	// @Modifying(clearAutomatically = true)
	// <S extends User> S save(S user);
}
