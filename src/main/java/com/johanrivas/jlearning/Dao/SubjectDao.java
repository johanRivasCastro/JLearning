package com.johanrivas.jlearning.Dao;

import com.johanrivas.jlearning.models.Entities.Subject;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SubjectDao extends CrudRepository<Subject, Long> {

    public Subject findByName(String name);

    // @Query("select u from User u where u.name like %?1% or u.lastname like %?1%
    // or u.identification like %?1% or u.email like %?1%")
    // Page<User> findByTerm(String term, PageRequest pageRequest);

}