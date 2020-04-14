package com.johanrivas.jlearning.Dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.johanrivas.jlearning.models.Entities.Role;

public interface RoleDao extends CrudRepository<Role, Long> {

    public Role findByAuthority(String authority);

    @Query(value = "SELECT ROLE_ID FROM USER_ROLE WHERE USER_ROLE.ROLE_ID = ?1", nativeQuery = true)
    public Long isRoleInUse(Long id);

}
