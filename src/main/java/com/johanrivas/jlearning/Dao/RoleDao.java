package com.johanrivas.jlearning.Dao;

import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.Entities.Role;

public interface RoleDao extends CrudRepository<Role, Long> {

    public Role findByAuthority(String authority);
}
