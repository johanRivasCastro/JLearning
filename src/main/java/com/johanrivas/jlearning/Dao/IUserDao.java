package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.johanrivas.jlearning.Entities.User;

public interface IUserDao extends PagingAndSortingRepository<User, Long> {

}
