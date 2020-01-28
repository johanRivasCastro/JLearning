package com.johanrivas.jlearning.Dao;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.johanrivas.jlearning.Entities.User;

public interface IUserDao extends PagingAndSortingRepository<User, Long> {

	// @Query("select p from Product p where p.name like %?1% and p.stock > 0 and
	// p.enable = 1")
	@Query("select u from User u where u.name like %?1% or u.lastname like %?1% or u.identification like %?1% or u.email like %?1%")
	Page<User> findByTerm(String term, PageRequest pageRequest);
}
