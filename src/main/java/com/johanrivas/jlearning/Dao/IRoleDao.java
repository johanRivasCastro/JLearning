package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.Entities.Role;

public interface IRoleDao extends CrudRepository<Role, Long> {

}
